package com.page.map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.framework.activity.BaseActivity;
import com.framework.activity.BaseFragment;
import com.framework.activity.SoftKeyBoardListener;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.ArrayUtils;
import com.framework.view.IFView;
import com.framework.view.LineDecoration;
import com.page.map.NearbyResult.NearbyItem;
import com.page.political.SignParam;
import com.qfant.wuye.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chenxi.cui on 2017/9/30.
 */

public class MapFragment extends BaseFragment implements BaiduMap.OnMarkerClickListener, OnItemClickListener<NearbyItem> {
    private static final String TAG = MapFragment.class.getSimpleName();
    @BindView(R.id.input_search)
    EditText inputSearch;
    @BindView(R.id.text_search)
    TextView textSearch;
    Unbinder unbinder;
    @BindView(R.id.bmapView)
    MapView bmapView;
    @BindView(R.id.text_back)
    IFView textBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_close)
    TextView tvClose;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_link)
    TextView tvLink;
    @BindView(R.id.tv_link1)
    TextView tvLink1;
    @BindView(R.id.tv_link2)
    TextView tvLink2;
    @BindView(R.id.tv_link3)
    TextView tvLink3;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.ll_search_view)
    LinearLayout llSearchView;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private MapStatus mMapStatus;
    LatLng centerPoint = new LatLng(33.747383, 115.785038);
    private LocationClient mLocationClient;
    private boolean isFirstLocation = true;
    private NearbyItem item;
    private MultiAdapter<NearbyItem> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = onCreateViewWithTitleBar(inflater, container, R.layout.fragement_map);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mMapView = (MapView) getView().findViewById(R.id.bmapView);
        setTitleBar("党建地图", false);
        initMap();
        initSearch();
    }

    private void initSearch() {
        textBack.setVisibility(View.GONE);
        llSearchView.setVisibility(View.GONE);
        textSearch.setFocusable(true);
        inputSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    textBack.setVisibility(View.VISIBLE);
//                    llSearchView.setVisibility(View.VISIBLE);
//                } else {
//                    textBack.setVisibility(View.GONE);
//                    llSearchView.setVisibility(View.GONE);
//                }
            }
        });
        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                textBack.setVisibility(View.VISIBLE);
                llSearchView.setVisibility(View.VISIBLE);
//                Toast.makeText(getActivity(), "键盘显示 高度" + height, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void keyBoardHide(int height) {

                textBack.setVisibility(View.GONE);
                llSearchView.setVisibility(View.GONE);
//                Toast.makeText(getActivity(), "键盘隐藏 高度" + height, Toast.LENGTH_SHORT).show();
            }
        });
        setListView();
    }

    private void setListView() {
        adapter = new MultiAdapter<NearbyItem>(getContext()).addTypeView(new ITypeView<NearbyItem>() {
            @Override
            public boolean isForViewType(NearbyItem item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new NearByHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.activity_nearby_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(getContext()));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
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
        mMapStatus = new MapStatus.Builder().zoom(16).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        initLocation();
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

                                                   @Override
                                                   public void onMapStatusChangeStart(MapStatus arg0) {
                                                       // 手势操作地图，设置地图状态等操作导致地图状态开始改变。
                                                   }

                                                   @Override
                                                   public void onMapStatusChange(MapStatus mapStatus) {

                                                   }

                                                   @Override
                                                   public void onMapStatusChangeFinish(MapStatus arg0) {
                                                       // 地图状态改变结束
                                                       //target地图操作的中心点。
                                                       LatLng target = mBaiduMap.getMapStatus().target;
                                                       centerPoint = target;
                                                       requestSignStatus(false);
                                                   }
                                               }
        );

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.text_back, R.id.input_search, R.id.text_search, R.id.ll_search_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_back:
                llSearchView.setVisibility(View.GONE);
                textBack.setVisibility(View.GONE);
                break;
            case R.id.ll_search_view:
                llSearchView.setVisibility(View.GONE);
                textBack.setVisibility(View.GONE);
                ((BaseActivity) getActivity()).hideSoftInput();
                break;
            case R.id.input_search:
                llSearchView.setVisibility(View.VISIBLE);
                textBack.setVisibility(View.VISIBLE);
                break;
            case R.id.text_search:
                String inputContent = inputSearch.getText().toString();
                if (TextUtils.isEmpty(inputContent)) {
                    showToast("请输入搜索内容");
                    return;
                }
                NearbySearchParam param = new NearbySearchParam();
                param.name = inputContent;
                Request.startRequest(param, ServiceMap.nearbySearch, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);

                break;
        }
    }


    @Override
    public void onItemClickListener(View view, NearbyItem data, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("NearbyItem", data);
        qStartActivity(NearBySearchActivity.class, bundle);
    }


    //自定义的定位监听
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //将获取的location信息给百度map
//            MyLocationData data = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    .direction(100)
//                    .latitude(location.getLatitude())
//                    .longitude(location.getLongitude())
//                    .build();
//            mBaiduMap.setMyLocationData(data);
            if (isFirstLocation) {
                centerPoint = new LatLng(location.getLatitude(), location.getLongitude());
                requestSignStatus(false);
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(centerPoint);
                mBaiduMap.setMapStatus(status);//直接到中间
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
        item = (NearbyItem) extraInfo.getSerializable("item");
        if (item == null) {
            return false;
        }
//        View layout = LinearLayout.inflate(getContext(), R.layout.map_marker_window_info, null);
        final View layout = getView().findViewById(R.id.ll_bottom);
        TextView tvInfo = (TextView) layout.findViewById(R.id.tv_info);
        TextView tvTitle = (TextView) layout.findViewById(R.id.tv_title);
        TextView tvClose = (TextView) layout.findViewById(R.id.tv_close);
        TextView tvLink = (TextView) layout.findViewById(R.id.tv_link);
        TextView tvLink1 = (TextView) layout.findViewById(R.id.tv_link1);
        TextView tvLink2 = (TextView) layout.findViewById(R.id.tv_link2);
        TextView tvLink3 = (TextView) layout.findViewById(R.id.tv_link3);
        tvLink.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tvLink1.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tvLink2.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tvLink3.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tvInfo.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tvTitle.setText("位置:  " + item.address);
        StringBuffer sb = new StringBuffer();
        sb.append("党组织书记:");
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
        tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText et = new EditText(getContext());
                et.setHint("请输入您想要咨询内容");
                new AlertDialog.Builder(getContext())
                        .setTitle("咨询内容")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et.getText().toString();
                                MapParam mapParam = new MapParam();
                                if (TextUtils.isEmpty(input)) {
                                    showToast("请输入您想要咨询内容");
                                    return;
                                }
                                mapParam.content = input;
                                mapParam.managerId = item.managerid;
                                Request.startRequest(mapParam, ServiceMap.zixun, mHandler, Request.RequestFeature.BLOCK, Request.RequestFeature.CANCELABLE);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
//        InfoWindow mInfoWindow = new InfoWindow(layout, marker.getPosition(), BitmapHelper.dip2px(getContext(), -100));
        layout.setVisibility(View.VISIBLE);
        tvLink.setOnClickListener(this);
        tvLink1.setOnClickListener(this);
        tvLink2.setOnClickListener(this);
        tvLink3.setOnClickListener(this);
        return false;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_link) {
            requestLinks(1);
        } else if (v.getId() == R.id.tv_link1) {
            requestLinks(2);
        } else if (v.getId() == R.id.tv_link2) {
            requestLinks(3);
        } else if (v.getId() == R.id.tv_link3) {
            requestLinks(4);
        }
    }

    private void requestLinks(int i) {
        if (item == null) {
            return;
        }
        PartyBIParam partyBIParam = new PartyBIParam();
        partyBIParam.id = item.id;
        partyBIParam.type = i;
        Request.startRequest(partyBIParam, ServiceMap.partyBranchIntro, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
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
                    .zIndex(10)
                    .icon(bitmap);
            mBaiduMap.addOverlay(option);
        }
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_gcoding);
        OverlayOptions option = new MarkerOptions()
                .position(centerPoint)
                .icon(bitmap);
        mBaiduMap.addOverlay(option);
    }


    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (ServiceMap.nearby == param.key) {
            NearbyResult result = (NearbyResult) param.result;
            if (param.result.bstatus.code == 0) {
                addOvers(result.data.partyBranchList);
            }
        } else if (ServiceMap.zixun == param.key) {
            showToast(param.result.bstatus.des);
        } else if (ServiceMap.partyBranchIntro == param.key) {
            PartyBaranchResult result = (PartyBaranchResult) param.result;
            if (result.bstatus.code == 0) {
                if (result.data.content != null) {
                    showTipText(result.data.content);
                }
            } else {
                showToast(param.result.bstatus.des);
            }
        } else if (ServiceMap.nearbySearch == param.key) {
            NearbyResult result = (NearbyResult) param.result;
            if (param.result.bstatus.code == 0) {
                if (!ArrayUtils.isEmpty(result.data.partyBranchList)) {
                    adapter.setData(result.data.partyBranchList);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            llSearchView.setVisibility(View.VISIBLE);
                        }
                    },1000);
                }
            } else {
                showToast(param.result.bstatus.des);
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
