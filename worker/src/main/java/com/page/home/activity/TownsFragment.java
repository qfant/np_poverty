package com.page.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.framework.activity.BaseFragment;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;
import com.page.home.GetTownsParam;
import com.page.home.TownsResult;
import com.page.home.adapter.TownsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenxi.cui on 2017/8/13.
 * townBean
 */

public class TownsFragment extends BaseFragment {

    @BindView(R.id.main_lv)
    GridView mainLv;
    @BindView(R.id.main_srl)
    SwipeRefreshLayout mainSrl;
    Unbinder unbinder;
    private TownsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pub_fragment_contact_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        loadData();
    }

    private void initData() {
        adapter = new TownsAdapter(getContext());
        mainLv.setAdapter(adapter);
        mainSrl.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mainSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        mainLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TownsAdapter adapter = (TownsAdapter) adapterView.getAdapter();
                TownsResult.TownBean item = adapter.getItem(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", item);
                item.type = getType();
                qStartActivity(TownsChildActivity.class, bundle);
            }
        });
    }


    private void loadData() {
        mainSrl.setRefreshing(true);
        GetTownsParam param = new GetTownsParam();
        Request.startRequest(param, ServiceMap.getTowns, mHandler, Request.RequestFeature.ADD_ONORDER);
    }
    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (super.onMsgSearchComplete(param)) {
            return true;
        }
        if (param.key == ServiceMap.getTowns) {
            TownsResult result = (TownsResult) param.result;
            if (result.bstatus.code == 0) {
                if (adapter != null) {
                    adapter.setData(result.data.towns);
                }
                if (mainSrl != null) {
                    mainSrl.setRefreshing(false);
                }
            }
        }
        return super.onMsgSearchComplete(param);
    }

    @Override
    public void onNetEnd(NetworkParam param) {
        if (mainSrl != null) {
            mainSrl.setRefreshing(false);
        }
        super.onNetEnd(param);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
