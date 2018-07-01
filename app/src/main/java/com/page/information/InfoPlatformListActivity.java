package com.page.information;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/5/7.
 */

public class InfoPlatformListActivity extends BaseActivity implements OnItemClickListener<String>, SwipRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout srlDownRefresh;
    private MultiAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_manager_list_layout);
        ButterKnife.bind(this);
        setTitleBar("信息平台", true);
        setListView();
//        startRequest(1);
        List<InfoItem> infos = new ArrayList<>();
        InfoItem infoItem = new InfoItem();
//        谯城区，涡阳县，蒙城县 利辛县，谯城经开区，亳芜产业园区，亳州经开区
        String [] aa ={"谯城区","涡阳县","蒙城县","利辛县","谯城经开区","亳芜产业园区","亳州经开区"};
        adapter.setData(Arrays.asList(aa));
    }

    private void setListView() {
        adapter = new MultiAdapter<String>(getContext()).addTypeView(new ITypeView<String>() {
            @Override
            public boolean isForViewType(String item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new InfoViewListHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.activity_info_list_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        srlDownRefresh.setOnRefreshListener(this);
    }


//    private void startRequest(int page) {
//        InfoParam param = new InfoParam();
//        param.pageNo = page;
//        if (page == 1) {
//            Request.startRequest(param, page, ServiceMap.InfoList, mHandler, Request.RequestFeature.BLOCK, Request.RequestFeature.CANCELABLE);
//        } else {
//            Request.startRequest(param, page, ServiceMap.InfoList, mHandler);
//        }
//    }



    @Override
    public void onItemClickListener(View view, String data, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("id", data);
        qStartActivity(InfoChartActivity.class, bundle);
    }

    @Override
    public void onRefresh(int index) {
//        startRequest(1);
    }

    @Override
    public void onLoad(int index) {
//        startRequest(++index);
    }

}
