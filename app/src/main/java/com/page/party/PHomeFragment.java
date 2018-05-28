package com.page.party;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

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
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.framework.activity.BaseFragment;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.ArrayUtils;
import com.framework.utils.imageload.ImageLoad;
import com.framework.view.LineDecoration;
import com.framework.view.circlerefresh.CircleRefreshLayout;
import com.framework.view.sivin.Banner;
import com.framework.view.sivin.BannerAdapter;
import com.page.analysis.AnalysisActivity;
import com.page.home.activity.MainActivity;
import com.page.home.activity.TextViewActivity;
import com.page.home.activity.WebActivity;
import com.page.home.model.HomeModel;
import com.page.home.model.LinksParam;
import com.page.home.model.LinksResult;
import com.page.home.model.LinksResult.Data.Links;
import com.page.home.model.NoticeResult;
import com.page.home.model.NoticeResult.Data.Datas;
import com.page.information.InfoPlatformActivity;
import com.page.integral.IntegralActivity;
import com.page.map.NearbyResult;
import com.page.map.PointResult;
import com.page.party.model.NewsResult;
import com.page.party.model.NewsResult.NewsData.NewsItem;
import com.page.home.view.MRecyclerView;
import com.page.home.view.ModeView;
import com.page.partymanger.MeetingListActivity;
import com.page.political.PoliticalManagerActivity;
import com.page.political.SignParam;
import com.page.political.SignStatusResult;
import com.page.store.home.model.FoodRecResult;
import com.qfant.wuye.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.page.community.serve.activity.ServeActivity.TITLE;

/**
 * Created by chenxi.cui on 2017/8/13.
 * 首页
 */

public class PHomeFragment extends BaseFragment {


    @BindView(R.id.circlerefreshlayout)
    CircleRefreshLayout circlerefreshlayout;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.flipper)
    ViewFlipper flipper;
    @BindView(R.id.gl_mode)
    GridLayout glMode;
    @BindView(R.id.rv_711_list)
    MRecyclerView rv711List;
    @BindView(R.id.bmapView)
    TextureMapView mMapView;
    @BindView(R.id.image_baidu)
    ImageView imageBaido;
    private BannerAdapter bannerAdapter;
    private Unbinder unbinder;
    private MultiAdapter adapter711;
    private BaiduMap mBaiduMap;
    LatLng centerPoint = new LatLng(33.865347, 115.776416);
    private boolean isFirstLocation = true;
    private LocationClient mLocationClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = onCreateViewWithTitleBar(inflater, container, R.layout.a_fragment_home_layout);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitleBar("亳州智慧非公", false);
        setRefresh();
        setBanner();
        setModel();
        setMap();
//        set711();
//        setFlipper(NoticeResult.Data.mock());
    }

    private void setMap() {
        mMapView = (TextureMapView) getView().findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mMapView.showScaleControl(false);
        mMapView.showZoomControls(false);
        imageBaido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setCurrentTab(1);
            }
        });
        initMap();
//        imageBaido.setVisibility(View.GONE);
    }

    private void setRefresh() {
        circlerefreshlayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                circlerefreshlayout.finishRefreshing();
                            }
                        });
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        mLocationClient.start();
//        getNotices();
//        getLinks();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        mLocationClient.stop();
    }

    private void set711() {
        adapter711 = new MultiAdapter<NewsItem>(getContext()).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.a_activity_new_item_layout, parent, false));
            }
        });
        rv711List.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv711List.setAdapter(adapter711);
        rv711List.addItemDecoration(new LineDecoration(getContext()));
        adapter711.setOnItemClickListener(new OnItemClickListener<NewsItem>() {
            @Override
            public void onItemClickListener(View view, NewsItem data, int position) {
                PNewsInfoActivity.startActivity(getContext(), data.title, data.intro);
            }
        });
        adapter711.setData(NewsResult.NewsData.mock());
    }

    private void initMap() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
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

    private void requestSignStatus(boolean isBlock) {
        if (isBlock) {
            Request.startRequest(new SignParam(centerPoint.latitude, centerPoint.longitude), ServiceMap.nearby, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
        } else {
            Request.startRequest(new SignParam(centerPoint.latitude, centerPoint.longitude), ServiceMap.nearby, mHandler, Request.RequestFeature.CANCELABLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        if (mLocationClient != null) {
            mLocationClient.stop();
            mLocationClient = null;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onHide();
        } else {
            onShow();
        }
    }

    protected void onShow() {
        mMapView.onResume();
//        mMapView.setVisibility(View.VISIBLE);
    }

    protected void onHide() {
        mMapView.onPause();
//        mMapView.setVisibility(View.GONE);
    }

    private void setModel() {
        ArrayList<HomeModel> list = new ArrayList<>();
        list.add(new HomeModel("政策宣传", R.drawable.icon_notice));
        list.add(new HomeModel("党建业务", R.drawable.icon_study_count));
        list.add(new HomeModel("工作动态", R.drawable.icon_dynamic_phase));
        list.add(new HomeModel("积分管理", R.drawable.icon_party_activity));
        list.add(new HomeModel("党员管理", R.drawable.icon_partybranch_introduce));
        list.add(new HomeModel("信息平台", R.drawable.icon_three_affairs));
        list.add(new HomeModel("指导员管理", R.drawable.icon_work_guide));
        list.add(new HomeModel("统计分析", R.drawable.icon_clear_build));

        for (final HomeModel homeModel : list) {
            ModeView itemView = new ModeView(getContext());
            itemView.setData(homeModel);
            itemView.setTag(homeModel.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    switch ((String) v.getTag()) {
                        case "政策宣传":
                            PNewListActivity.startActivity(getContext(), homeModel.title, 1);
                            break;
                        case "党建业务":
                            PNewListActivity.startActivity(getContext(), homeModel.title, 2);
                            break;
                        case "工作动态":
                            PNewListActivity.startActivity(getContext(), homeModel.title, 3);
                            break;
                        case "积分管理":
                            qStartActivity(IntegralActivity.class);
                            break;
                        case "党员管理":
                            showToast("正在研发中");
//                            qStartActivity(MeetingListActivity.class);
                            break;
                        case "信息平台":
                            showToast("正在研发中");
//                            qStartActivity(InfoPlatformActivity.class);
                            break;
                        case "指导员管理":
                            qStartActivity(PoliticalManagerActivity.class);
                            break;
                        case "统计分析":
                            showToast("正在研发中");
//                            qStartActivity(AnalysisActivity.class);
                            break;
                    }
                }
            });
            glMode.addView(itemView);
        }
    }

    private void setBanner() {
        ArrayList<Links> arrayList = new ArrayList<>();
        bannerAdapter = new BannerAdapter<Links>(arrayList) {
            @Override
            protected void bindTips(TextView tv, Links bannerModel) {
//                tv.setText(bannerModel.getTips());
            }

            @Override
            public void bindImage(ImageView imageView, Links bannerModel) {
                ImageLoad.loadPlaceholder(getContext(), bannerModel.imgurl, imageView, R.drawable.banner, R.drawable.banner);
            }

        };
        banner.setBannerAdapter(bannerAdapter);
        banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                try {
                    Links links = (Links) bannerAdapter.getmDataList().get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString(TITLE, links.title);
                    bundle.putString(WebActivity.URL, links.link);
                    qStartActivity(WebActivity.class, bundle);
                } catch (Exception e) {

                }
            }
        });
    }

    public void getNotices() {
        Request.startRequest(new BaseParam(), ServiceMap.getNoticeList, mHandler);
    }


    private void getLinks() {
        LinksParam param = new LinksParam();
        param.type = 1;
        Request.startRequest(param, ServiceMap.getLinks, mHandler);
    }


    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (ServiceMap.nearby == param.key) {
            NearbyResult result = (NearbyResult) param.result;
            if (param.result.bstatus.code == 0) {
                addOvers(result.data.partyBranchList);
            }
        } else if (param.key == ServiceMap.getLinks) {
            LinksResult linksResult = (LinksResult) param.result;
            if (linksResult != null && linksResult.data != null && linksResult.data.links != null) {
                updataBanner(linksResult.data.links);
            }
        } else if (param.key == ServiceMap.getNoticeList) {
            NoticeResult result = (NoticeResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.datas)) {
                setFlipper(result.data.datas);
            }
        } else if (param.key == ServiceMap.getRecommendCategorys) {
//            FoodRecResult result = (FoodRecResult) param.result;
//            updataList(result);
        }
        return false;
    }

    private void addOvers(List<NearbyResult.NearbyItem> nearbyItems) {
        if (nearbyItems == null) {
            return;
        }
        mBaiduMap.clear();
        for (NearbyResult.NearbyItem item : nearbyItems) {
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

    private void setFlipper(List<Datas> datas) {
        flipper.removeAllViews();
        for (final Datas item : datas) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.pub_fragment_home_notice_layout, null);
            TextView tvTips = (TextView) view.findViewById(R.id.tv_tips);
            tvTips.setText(item.title);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("intro", item.content);
                    qStartActivity(TextViewActivity.class, bundle);
                }
            });
            flipper.addView(view);
        }
    }

    private void updataBanner(List<Links> links) {
        bannerAdapter.setImages(links);
        banner.notifyDataHasChanged();
    }

    private void updataList(FoodRecResult result) {
        if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.products)) {
            rv711List.setVisibility(View.VISIBLE);
            adapter711.setData(result.data.products);
        } else {
            rv711List.setVisibility(View.GONE);
        }
    }

}
