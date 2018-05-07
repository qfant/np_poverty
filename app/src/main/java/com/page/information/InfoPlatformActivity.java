package com.page.information;

import android.os.Bundle;
import android.widget.ListView;

import com.framework.activity.BaseActivity;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/5/7.
 */

public class InfoPlatformActivity extends BaseActivity {
    @BindView(R.id.listview)
    ListView listview;
    private InfoPlatformAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_info_platform_layout);
        ButterKnife.bind(this);
        setTitleBar("信息平台", true);
        initData();
        requestData();
    }

    private void requestData() {
        Request.startRequest(new BaseParam(), ServiceMap.getPlatforms,mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
    }

    private void initData() {
        adapter = new InfoPlatformAdapter(this);
        listview.setAdapter(adapter);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getPlatforms) {
            InfoPlatformResult result = (InfoPlatformResult) param.result;
            adapter.setData(result.data.infoPlatformlist);
        }
        return super.onMsgSearchComplete(param);
    }

}
