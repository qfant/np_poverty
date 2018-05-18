package com.page.map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.framework.activity.BaseFragment;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.BitmapHelper;
import com.page.map.NearbyResult.NearbyItem;
import com.page.political.SignParam;
import com.qfant.wuye.R;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/30.
 */

public class MapFragment extends BaseFragment implements BaiduMap.OnMarkerClickListener {
    private static final String TAG = MapFragment.class.getSimpleName();
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private MapStatus mMapStatus;
    LatLng centerPoint = new LatLng(33.747383, 115.785038);
    private LocationClient mLocationClient;
    private boolean isFirstLocation = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        return onCreateViewWithTitleBar(inflater, container, R.layout.fragement_map);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapView = (MapView) getView().findViewById(R.id.bmapView);
        setTitleBar("党建地图", false);
        initMap();
    }

    private void requestSignStatus(boolean isBlock) {
        if (isBlock) {
            Request.startRequest(new SignParam(centerPoint.latitude, centerPoint.longitude), ServiceMap.nearby, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
        } else {
            Request.startRequest(new SignParam(centerPoint.latitude, centerPoint.longitude), ServiceMap.nearby, mHandler, Request.RequestFeature.CANCELABLE);
        }
    }

    private void initMap() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMarkerClickListener(this);
        mBaiduMap.setMyLocationEnabled(true);
        initLocation();
    }

    private void initLocation() {
        //定位客户端的设置
        mLocationClient = new LocationClient(getActivity());
        MyLocationListener mLocationListener = new MyLocationListener();
        //注册监听
        mLocationClient.registerLocationListener(mLocationListener);
        //配置定位
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");//坐标类型
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//打开Gps
        option.setScanSpan(60 * 1000);//1000毫秒定位一次
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


    private void recoverStatus(String name, boolean hasBack, LatLng cenpt, int zoom) {
        setTitleBar("党建地图", hasBack);
//        LatLng cenpt = new LatLng(33.850643, 115.785038);
        //定义地图状态
        mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(zoom)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        Bundle extraInfo = marker.getExtraInfo();
        if (extraInfo == null) {
            return false;
        }
        final NearbyItem item = (NearbyItem) extraInfo.getSerializable("item");
        if (item == null) {
            return false;
        }
//        View layout = LinearLayout.inflate(getContext(), R.layout.map_marker_window_info, null);
        final View layout = getView().findViewById(R.id.ll_bottom);
        TextView tvInfo = (TextView) layout.findViewById(R.id.tv_info);
        TextView tvLink = (TextView) layout.findViewById(R.id.tv_link);
        TextView tvTitle = (TextView) layout.findViewById(R.id.tv_title);
        TextView tvClose = (TextView) layout.findViewById(R.id.tv_close);
        tvLink.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tvLink.setVisibility(View.GONE);
        tvTitle.setText("位置:  "+item.address);
        StringBuffer sb = new StringBuffer();
        sb.append("主管单位: ");
        sb.append(item.intro);
        sb.append("\n");
        sb.append("\n");
        sb.append("负责人:");
        sb.append("  ");
        sb.append(item.manager);
        sb.append("  ");
        sb.append(item.phone);
        tvInfo.setText(sb.toString());
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setVisibility(View.GONE);
            }
        });
        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText et = new EditText(getContext());
                new AlertDialog.Builder(getContext(), R.style.list_dialog_style)
                        .setTitle("请输入您想要咨询或预约办理的事项及您的联系方式")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et.getText().toString();
                                MapParam mapParam = new MapParam();
                                if (TextUtils.isEmpty(input)) {
                                    showToast("请输入您想要咨询或预约办理的事项及您的联系方式");
                                    return;
                                }
                                mapParam.content = input;
                                mapParam.villageid = item.id;
//                                Request.startRequest(mapParam, ServiceMap.consult, mHandler, Request.RequestFeature.BLOCK);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
//        InfoWindow mInfoWindow = new InfoWindow(layout, marker.getPosition(), BitmapHelper.dip2px(getContext(), -100));
        layout.setVisibility(View.VISIBLE);

        return false;
    }

    private void addOvers(List<NearbyItem> nearbyItems) {
        if (nearbyItems == null) {
            return;
        }
        mBaiduMap.clear();
        for (NearbyItem item : nearbyItems) {
            //定义Maker坐标点
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", item);
            LatLng point = new LatLng(item.latitude, item.longitude);
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.map_text_option, null);
            TextView text = (TextView) inflate.findViewById(R.id.text);
            text.setText(item.name);
            text.setVisibility(View.VISIBLE);
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromView(inflate);
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .extraInfo(bundle)
                    .icon(bitmap);
            mBaiduMap.addOverlay(option);
        }
    }


    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (ServiceMap.nearby == param.key) {
            NearbyResult result = (NearbyResult) param.result;
            if (param.result.bstatus.code == 0) {
                addOvers(result.data.partyBranchList);
            }
        }
        return super.onMsgSearchComplete(param);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.stop();
            mLocationClient = null;
        }
        mMapView.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.v(TAG, "onHiddenChanged" + hidden);
        if (hidden) {
            onHide();
        } else {
            onShow();
        }
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        mLocationClient.start();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLocationClient.stop();
        mMapView.onPause();
    }

    protected void onShow() {
        mMapView.onResume();
        mLocationClient.start();
    }

    protected void onHide() {

    }


}
