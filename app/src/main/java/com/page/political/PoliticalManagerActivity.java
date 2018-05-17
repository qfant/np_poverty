package com.page.political;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.page.map.PointResult;
import com.qfant.wuye.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2018/5/7.
 */

public class PoliticalManagerActivity extends BaseActivity {
    @BindView(R.id.bmapView)
    MapView mMapView;
    @BindView(R.id.text_time)
    TextView textTime;
    @BindView(R.id.text_status)
    TextView textStatus;
    @BindView(R.id.text_date)
    TextView textDate;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.ll_2)
    LinearLayout ll2;
    @BindView(R.id.ll_3)
    LinearLayout ll3;
    @BindView(R.id.ll_4)
    LinearLayout ll4;
    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.text_2)
    TextView text2;
    @BindView(R.id.text_3)
    TextView text3;
    @BindView(R.id.text_4)
    TextView text4;
    private BaiduMap mBaiduMap;
    //    LatLng centerPoint = new LatLng(33.747383, 115.785038);
    LatLng centerPoint = new LatLng(33.865347, 115.776416);
    private boolean isFirstLocation = true;
    private SignStatusResult.SignStatus mSignStatus;
    private LocationClient mLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(this);
        setContentView(R.layout.activity_guide_layout);
        ButterKnife.bind(this);
        setTitleBar("指导员管理", true);
        initView();
    }

    public void initView() {
        setTime();
        setDate();
        initMap();
    }

    private void requestSignStatus(boolean isBlock) {
        if (isBlock) {
            Request.startRequest(new SignParam(centerPoint.latitude, centerPoint.longitude), ServiceMap.signstatus, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
        } else {
            Request.startRequest(new SignParam(centerPoint.latitude, centerPoint.longitude), ServiceMap.signstatus, mHandler, Request.RequestFeature.CANCELABLE);
        }
    }

    private void initMap() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        initLocation();
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
        option.setScanSpan(10 * 1000);//1000毫秒定位一次
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);
        mLocationClient.start();
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
            mBaiduMap.setMyLocationData(data);
            centerPoint = new LatLng(location.getLatitude(), location.getLongitude());
            requestSignStatus(false);
            MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(centerPoint);
            mBaiduMap.setMapStatus(status);//直接到中间
            if (isFirstLocation) {
                mBaiduMap.animateMapStatus(status);//动画的方式到中间
                isFirstLocation = false;
            }
        }
    }

    public void setDate() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取年
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取月
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取日
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));//获取星期
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        textDate.setText(mYear + "年" + mMonth + "月" + mDay + "日" + " 星期" + mWay);
    }

    private void setTime() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                final String times = format.format(new Date());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        textTime.setText(times);
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }


    private void addOvers(List<PointResult.PointItem> pointItems) {
        if (pointItems == null) {
            return;
        }
        mBaiduMap.clear();
        for (PointResult.PointItem item : pointItems) {
            //定义Maker坐标点
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", item);
            LatLng point = new LatLng(item.lat, item.lon);
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.map_text_option, null);
            TextView text = (TextView) inflate.findViewById(R.id.text);
            text.setText("我在 " + item.name + " 附近");
            text.setVisibility(View.VISIBLE);
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromView(inflate);
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .extraInfo(bundle)
                    .zIndex(15)
                    .icon(bitmap);
            mBaiduMap.addOverlay(option);
//            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(point);
//            mBaiduMap.setMapStatus(msu);
        }

    }


    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (ServiceMap.signstatus == param.key) {
            SignStatusResult result = (SignStatusResult) param.result;
            if (param.result.bstatus.code == 0) {
            } else {
//                showToast(result.bstatus.des);
                textStatus.setText(result.bstatus.des);
            }
            updateSignStatus(result.data);
        } else if (ServiceMap.signin == param.key || ServiceMap.signout == param.key) {
            if (param.result.bstatus.code == 0) {
                requestSignStatus(true);
            } else {
                showToast(param.result.bstatus.des);
            }
        }
        return super.onMsgSearchComplete(param);
    }

    private void updateSignStatus(SignStatusResult.SignStatus signstatus) {
        this.mSignStatus = signstatus;
        if (signstatus == null) {
            text1.setEnabled(false);
            text2.setEnabled(false);
            textStatus.setVisibility(View.VISIBLE);
            return;
        }
        textStatus.setText(mSignStatus.companyname);
        text1.setEnabled(signstatus.signin == 1 ? true : false);
        text2.setEnabled(signstatus.signout == 1 ? true : false);
        List<PointResult.PointItem> pointItems = new ArrayList<>();
        PointResult.PointItem pointItem = new PointResult.PointItem();
        pointItems.add(pointItem);
        pointItem.lat = signstatus.latitude;
        pointItem.lon = signstatus.longitude;
        pointItem.name = signstatus.companyname;
        addOvers(pointItems);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.stop();
            mLocationClient = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        mLocationClient.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        mLocationClient.stop();
    }

    @OnClick({R.id.ll_1, R.id.ll_2, R.id.ll_3, R.id.ll_4})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.ll_1:
                if (mSignStatus == null) {
                    showToast("没有可签到的企业");
                    return;
                }
                if (mSignStatus.signin == 0) {
                    return;
                }
                SignParam signParam = new SignParam(centerPoint.latitude, centerPoint.longitude);
                signParam.id = mSignStatus.id;
                Request.startRequest(signParam, ServiceMap.signin, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
                break;
            case R.id.ll_2:
                if (mSignStatus == null) {
                    showToast("没有可签到的企业");
                    return;
                }
                if (mSignStatus.signout == 0) {
                    return;
                }
                SignParam signParam1 = new SignParam(centerPoint.latitude, centerPoint.longitude);
                signParam1.id = mSignStatus.id;
                Request.startRequest(signParam1, ServiceMap.signout, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
                break;
            case R.id.ll_3:
                qStartActivity(SignListActivity.class);
                break;
            case R.id.ll_4:
                qStartActivity(WorkLogListActivity.class);
                break;
        }
    }

}
