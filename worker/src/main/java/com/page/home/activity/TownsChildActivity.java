package com.page.home.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.framework.activity.BaseActivity;
import com.haolb.client.R;
import com.page.home.TownsResult;
import com.page.home.adapter.TownsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenxi.cui on 2017/8/13.
 * townBean
 */

public class TownsChildActivity extends BaseActivity {

    @BindView(R.id.main_lv)
    GridView mainLv;
    @BindView(R.id.main_srl)
    SwipeRefreshLayout mainSrl;
    Unbinder unbinder;
    private TownsAdapter adapter;
    private TownsResult.TownBean townBean;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_fragment_contact_layout);
        unbinder = ButterKnife.bind(this);
        townBean = (TownsResult.TownBean) myBundle.getSerializable("item");
        type =  myBundle.getInt("type");
        if (townBean == null) {
            finish();
            return;
        }
        setTitleBar(townBean.name, true);
        initData();
    }


    private void initData() {
        adapter = new TownsAdapter(getContext());
        mainLv.setAdapter(adapter);
        if (adapter != null) {
            adapter.setData(townBean.village);
        }
        mainLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TownsAdapter adapter = (TownsAdapter) adapterView.getAdapter();
                TownsResult.TownBean item = adapter.getItem(i);
                Bundle bundle = new Bundle();
                item.type = townBean.type;
                bundle.putSerializable("item", item);
                qStartActivity(PersonListActivity.class, bundle);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }



}
