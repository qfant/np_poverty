package com.page.political;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.ArrayUtils;
import com.framework.view.AddView;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/5/17.
 */

public class WorkLogActivity extends BaseActivity {
    @BindView(R.id.input_title)
    EditText inputTitle;
    @BindView(R.id.input_content)
    EditText inputContent;
    @BindView(R.id.addView)
    AddView addView;
    private String addrStr;
    private LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_log_layout);
        ButterKnife.bind(this);
        setTitleBar("写日志", true, "提交", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRequest();
            }
        });
        initLocation();
        addView.setAddNumber(1);
    }

    private void initLocation() {
        //定位客户端的设置
        mLocationClient = new LocationClient(this);
        MyLocationListener mLocationListener = new MyLocationListener();
        //注册监听
        mLocationClient.registerLocationListener(mLocationListener);
        //配置定位
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");//坐标类型
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//打开Gps
        option.setScanSpan(10000 * 1000);//1000毫秒定位一次
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationClient.start();
    }

    @Override
    protected void onPause() {
        mLocationClient.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient = null;
        super.onDestroy();
    }

    //自定义的定位监听
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //将获取的location信息给百度map
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(100)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            addrStr = location.getAddrStr();
        }
    }

    private void submitRequest() {
        String[] imageUrls = addView.getImageUrls();
        String title = inputTitle.getText().toString();
        String content = inputContent.getText().toString();
        if (ArrayUtils.isEmpty(imageUrls)) {
            showToast("请上传图片");
            return;
        }
        if (TextUtils.isEmpty(title)) {
            showErrorTip(inputTitle, "请输入标题");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            showErrorTip(inputContent, "请输入日志内容");
            return;
        }
        WorkLogSubParam param = new WorkLogSubParam();
        param.title = title;
        param.content = content;
        param.address = addrStr;
        param.pic1 = imageUrls[0];
        Request.startRequest(param, ServiceMap.submitWorklog, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.submitWorklog) {
            if (param.result.bstatus.code == 0) {
                showToast(param.result.bstatus.des);
                finish();
            } else {
                showToast(param.result.bstatus.des);
            }
        }else if (param.key == ServiceMap.uploadPic) {
            addView.onMsgSearchComplete(param);
        }
        return super.onMsgSearchComplete(param);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        addView.onActivityResult(requestCode, resultCode, data);
    }
}
