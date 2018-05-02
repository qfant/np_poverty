package com.page.home.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.haolb.client.R;
import com.page.detail.DetailParam;
import com.page.home.CamerasResult;
import com.page.home.GetPersonsParam;
import com.page.home.TownsResult;
import com.page.home.adapter.ContactListAdapter;
import com.page.home.adapter.HomeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2017/11/15.
 */

public class ContactListActivity extends BaseActivity {

    @BindView(R.id.main_lv)
    ListView mainLv;
    @BindView(R.id.main_srl)
    SwipeRefreshLayout mainSrl;
    private ContactListAdapter adapter;
    private TownsResult.TownBean townBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_contact_list);
        ButterKnife.bind(this);
        townBean = (TownsResult.TownBean) myBundle.getSerializable("item");
        if (townBean == null) {
            finish();
            return;
        }
        setTitleBar(townBean.name, true);
        initData();
    }

    private void initData() {
        adapter = new ContactListAdapter(getContext());
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
                HomeAdapter adapter = (HomeAdapter) adapterView.getAdapter();
                CamerasResult.CameraBean item = adapter.getItem(i);
                item.type = adapter.getType();
                DetailParam param = new DetailParam();
                param.id = item.id;
                Request.startRequest(param, ServiceMap.getRepair, mHandler, Request.RequestFeature.BLOCK);
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
        GetPersonsParam param = new GetPersonsParam();
        param.villageId = townBean.id;
        Request.startRequest(param, ServiceMap.getPersons, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (super.onMsgSearchComplete(param)) {
            return true;
        }
        if (param.key == ServiceMap.getPersons) {
            CamerasResult result = (CamerasResult) param.result;
            if (result.bstatus.code == 0) {
                if (adapter != null) {
                    adapter.setData(result.data.cameras);
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


}
