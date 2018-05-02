package com.page.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;
import com.page.home.GetPersonsParam;
import com.page.home.GetWorkersParam;
import com.page.home.PersonsResult;
import com.page.home.TownsResult;
import com.page.home.adapter.PersonListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenxi.cui on 2017/8/13.
 * 首页
 */

public class PersonListActivity extends BaseActivity {

    @BindView(R.id.main_lv)
    ListView mainLv;
    @BindView(R.id.main_srl)
    SwipeRefreshLayout mainSrl;
    Unbinder unbinder;
    private PersonListAdapter adapter;
    private TownsResult.TownBean townBean;

    @Nullable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_fragment_person_layout);
        unbinder = ButterKnife.bind(this);
        townBean = (TownsResult.TownBean) myBundle.getSerializable("item");
        if (townBean == null) {
            finish();
            return;
        }
        setTitleBar(townBean.name, true);
        initData();
        loadData();
    }


    private void initData() {
        adapter = new PersonListAdapter(getContext());
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

                PersonListAdapter adapter = (PersonListAdapter) adapterView.getAdapter();
                PersonsResult.PersonBean item = adapter.getItem(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", item);
                qStartActivity(PersonDetailActivity.class, bundle);
     }
        });
    }


    private void loadData() {
        mainSrl.setRefreshing(true);
        if (townBean.type == 2) {
            GetPersonsParam param = new GetPersonsParam();
            param.villageId = townBean.id;
            Request.startRequest(param, ServiceMap.getPersons, mHandler, Request.RequestFeature.ADD_ONORDER);
        }else {
            GetWorkersParam param = new GetWorkersParam();
            param.villageId = townBean.id;
            Request.startRequest(param, ServiceMap.getWorkers, mHandler, Request.RequestFeature.ADD_ONORDER);

        }
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (super.onMsgSearchComplete(param)) {
            return true;
        }
        if (param.key == ServiceMap.getPersons||param.key == ServiceMap.getWorkers) {
            PersonsResult result = (PersonsResult) param.result;
            if (result.bstatus.code == 0) {
                if (adapter != null) {
                    adapter.setData(result.data.personsResult);
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
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
