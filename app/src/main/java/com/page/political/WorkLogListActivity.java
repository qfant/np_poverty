package com.page.political;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.ArrayUtils;
import com.framework.view.LineDecoration;
import com.framework.view.pull.SwipRefreshLayout;
import com.page.party.PNewsInfoActivity;
import com.page.political.WorkLogListResult.WorkLogItem;
import com.qfant.wuye.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2018/5/17.
 */

public class WorkLogListActivity extends BaseActivity implements OnItemClickListener<WorkLogItem>, SwipRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout srlDownRefresh;
    @BindView(R.id.text_year)
    TextView textYear;
    @BindView(R.id.text_mouth)
    TextView textMouth;
    private MultiAdapter<WorkLogItem> adapter;
    private int mType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_log_list_layout);
        ButterKnife.bind(this);
        setTitleBar("工作日志", true, "写日志", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qStartActivity(WorkLogActivity.class);
            }
        });
        setListView();
        textYear.setTextColor(getResources().getColor(R.color.pub_color_red));
        textMouth.setTextColor(getResources().getColor(R.color.pub_color_black));
        mType = 1;
        startRequest(1);

    }

    private void setListView() {
        adapter = new MultiAdapter<WorkLogItem>(getContext()).addTypeView(new ITypeView<WorkLogItem>() {
            @Override
            public boolean isForViewType(WorkLogItem item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.activity_work_log_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        srlDownRefresh.setOnRefreshListener(this);
    }


    private void startRequest(int page) {
        WorkLogParam param = new WorkLogParam();
        param.pageNo = page;
        param.type = mType;
        if (page == 1) {
            Request.startRequest(param, page, ServiceMap.worklogList, mHandler, Request.RequestFeature.BLOCK, Request.RequestFeature.CANCELABLE);
        } else {
            Request.startRequest(param, page, ServiceMap.worklogList, mHandler);
        }
    }


    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.worklogList) {
            WorkLogListResult result = (WorkLogListResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.workLogResult)) {
                if ((int) param.ext == 1) {
                    adapter.setData(result.data.workLogResult);
                } else {
                    adapter.addData(result.data.workLogResult);
                }
            } else {
                showToast("没有更多了");
            }
            srlDownRefresh.setRefreshing(false);
        }
        return false;
    }

    @Override
    public void onItemClickListener(View view, WorkLogItem data, int position) {
        PNewsInfoActivity.startActivity(this, data.title, data.content, data.id);
    }

    @Override
    public void onRefresh(int index) {
        startRequest(1);
    }

    @Override
    public void onLoad(int index) {
        startRequest(++index);
    }

    @OnClick({R.id.text_year, R.id.text_mouth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_year:
                textYear.setTextColor(getResources().getColor(R.color.pub_color_red));
                textMouth.setTextColor(getResources().getColor(R.color.pub_color_black));
                mType = 1;
                startRequest(1);
                break;
            case R.id.text_mouth:
                textMouth.setTextColor(getResources().getColor(R.color.pub_color_red));
                textYear.setTextColor(getResources().getColor(R.color.pub_color_black));
                mType = 2;
                startRequest(1);
                break;
        }
    }
}
