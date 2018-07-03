package com.page.information;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;
import butterknife.OnClick;
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
import com.page.information.InfoPlatformResult.InfoItem;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/5/7.
 */

public class InfoPlatformActivity extends BaseActivity implements OnItemClickListener<InfoItem>, SwipRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout srlDownRefresh;
    @BindView(R.id.input_search)
    EditText inputSearch;
    @BindView(R.id.text_search)
    TextView textSearch;
    private MultiAdapter<InfoItem> adapter;
    private int id;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_manager_list_layout);
        ButterKnife.bind(this);
        id = myBundle.getInt("id");
        type = myBundle.getInt("type");
        setTitleBar("信息平台", true);
        setListView();
        startRequest(1);

    }
    @OnClick(R.id.text_search)
    public void onViewClicked() {
        String s = inputSearch.getText().toString();
        if (TextUtils.isEmpty(s)) {
            return;
        }
        InfoPlatformParam param = new InfoPlatformParam();
        param.managerId = id;
        param.type = type;
        param.name = s;
        Request.startRequest(param, 1, ServiceMap.InfoList, mHandler, Request.RequestFeature.BLOCK, Request.RequestFeature.CANCELABLE);
//        startRequest(1, s);
    }
    private void setListView() {
        adapter = new MultiAdapter<InfoItem>(getContext()).addTypeView(new ITypeView<InfoItem>() {
            @Override
            public boolean isForViewType(InfoItem item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new InfoViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.activity_info_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        srlDownRefresh.setOnRefreshListener(this);
    }


    private void startRequest(int page) {
        CompanyParam param = new CompanyParam();
        param.managerId = id;
        param.type = type;
        param.pageNo = page;
        if (page == 1) {
            Request.startRequest(param, page, ServiceMap.companyList, mHandler, Request.RequestFeature.BLOCK, Request.RequestFeature.CANCELABLE);
        } else {
            Request.startRequest(param, page, ServiceMap.companyList, mHandler);
        }
    }


    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.companyList) {
            InfoPlatformResult result = (InfoPlatformResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.newsList)) {
                if ((int) param.ext == 1) {
                    adapter.setData(result.data.newsList);
                } else {
                    adapter.addData(result.data.newsList);
                }
            } else {
                showToast("没有更多了");
            }
            srlDownRefresh.setRefreshing(false);
        } else  if (param.key == ServiceMap.InfoList) {
            InfoPlatformResult result = (InfoPlatformResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.infoListResult)) {
                if ((int) param.ext == 1) {
                    adapter.setData(result.data.infoListResult);
                } else {
                    adapter.addData(result.data.infoListResult);
                }
            } else {
                showToast("没有更多了");
            }
            srlDownRefresh.setRefreshing(false);
        }
        return false;
    }

    @Override
    public void onItemClickListener(View view, InfoItem data, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", data.id);
        qStartActivity(InfoDetailActivity.class, bundle);
    }

    @Override
    public void onRefresh(int index) {
        startRequest(1);
    }

    @Override
    public void onLoad(int index) {
        startRequest(++index);
    }

}
