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
import com.page.community.eventdetails.activity.EventDetailActivity;
import com.page.community.eventlist.holder.ViewHolder;
import com.page.community.eventlist.model.EventListResult;
import com.page.political.SignListResult.SignListItem;
import com.qfant.wuye.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2018/5/16.
 */

public class SignListActivity extends BaseActivity implements OnItemClickListener<EventListResult.Data.ActivityList>, SwipRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout srlDownRefresh;
    @BindView(R.id.text_year)
    TextView textYear;
    @BindView(R.id.text_mouth)
    TextView textMouth;
    private MultiAdapter<SignListItem> adapter;
    private int mYear;
    private int mMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_list_layout);
        ButterKnife.bind(this);
        setTitleBar("考勤记录", true);
        setListView();
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        setYear();
    }


    @Override
    protected void onResume() {
        super.onResume();
        startRequest(1);
    }

    private void setListView() {
        adapter = new MultiAdapter<SignListItem>(getContext()).addTypeView(new ITypeView<SignListItem>() {
            @Override
            public boolean isForViewType(SignListItem item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new SignListViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.activity_sign_list_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        srlDownRefresh.setOnRefreshListener(this);
    }

    private void showYear() {
        new YearPickerDialog(this, R.style.list_dialog_style, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                if (mYear == year || mMonth == monthOfYear) {
                    return;
                }
                mYear = year;
                mMonth = monthOfYear;
                setYear();
                startRequest(1);
            }
        }, mYear, mMonth, 1).show();
    }

    private void setYear() {
        textYear.setText(mYear + "年");
        textMouth.setText(mMonth + 1 + "月");
    }

    private void startRequest(int page) {
        SignListParam param = new SignListParam();
        param.pageNo = page;
        param.year = mYear;
        param.month = mMonth + 1;
        if (page == 1) {
            Request.startRequest(param, page, ServiceMap.signList, mHandler, Request.RequestFeature.BLOCK, Request.RequestFeature.CANCELABLE);
        } else {
            Request.startRequest(param, page, ServiceMap.signList, mHandler);
        }
    }


    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.signList) {
            SignListResult result = (SignListResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.signList)) {
                if ((int) param.ext == 1) {
                    adapter.setData(result.data.signList);
                } else {
                    adapter.addData(result.data.signList);
                }
            } else {
                showToast("没有更多了");
            }
            srlDownRefresh.setRefreshing(false);
        }
        return false;
    }

    @Override
    public void onItemClickListener(View view, EventListResult.Data.ActivityList data, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(EventDetailActivity.ID, data.id);
        qStartActivity(EventDetailActivity.class, bundle);
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
            case R.id.text_mouth:
                showYear();
                break;
        }
    }
}
