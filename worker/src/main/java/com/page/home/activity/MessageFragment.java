package com.page.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.framework.activity.BaseFragment;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;
import com.page.home.GetNoticesParam;
import com.page.home.NoticesResult;
import com.page.home.adapter.MessageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenxi.cui on 2017/8/13.
 * 首页
 */

public class MessageFragment extends BaseFragment {

    @BindView(R.id.main_lv)
    ListView mainLv;
    @BindView(R.id.main_srl)
    SwipeRefreshLayout mainSrl;
    Unbinder unbinder;
    private MessageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pub_fragment_message_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        adapter = new MessageAdapter(getContext());
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
                MessageAdapter adapter = (MessageAdapter) adapterView.getAdapter();
                NoticesResult.NoticeBean item = adapter.getItem(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", item);
                qStartActivity(MessageDetailActivity.class, bundle);
            }
        });
    }

    @Override
    public void onResume() {
        loadData();
        super.onResume();
    }

    private void loadData() {
        mainSrl.setRefreshing(true);
        GetNoticesParam param = new GetNoticesParam();
        Request.startRequest(param, ServiceMap.getNotices, mHandler, Request.RequestFeature.ADD_ONORDER);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (super.onMsgSearchComplete(param)) {
            return true;
        }
        if (param.key == ServiceMap.getNotices) {
            NoticesResult result = (NoticesResult) param.result;
            if (result.bstatus.code == 0) {
                if (adapter != null) {
                    adapter.setData(result.data.noticesResult);
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
