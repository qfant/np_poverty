package com.page.map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.view.IFView;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/5/21.
 */

public class NearBySearchActivity extends BaseActivity implements BaiduMap.OnMarkerClickListener {
    @BindView(R.id.bmapView)
    MapView bmapView;
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
    private BaiduMap mBaiduMap;
    private NearbyResult.NearbyItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(this);
        setContentView(R.layout.nearby_search_layou_map);
        ButterKnife.bind(this);

        mBaiduMap = bmapView.getMap();
        mBaiduMap.setOnMarkerClickListener(this);
        item = (NearbyResult.NearbyItem) myBundle.getSerializable("NearbyItem");
        setTitleBar(item.name, true);
        LatLng cenpt = new LatLng(item.latitude, item.longitude);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(15)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);

        LatLng point = new LatLng(item.latitude, item.longitude);
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.map_text_option, null);
        TextView text = (TextView) inflate.findViewById(R.id.text);
        text.setText(item.name);
        text.setVisibility(View.VISIBLE);
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromView(inflate);
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        mBaiduMap.addOverlay(option);
        setInfo();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (item == null) {
            return false;
        }
        setInfo();
        return false;
    }

    private void setInfo() {
        final View layout = findViewById(R.id.ll_bottom);
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
        layout.setVisibility(View.VISIBLE);
        tvLink.setOnClickListener(this);
        tvLink1.setOnClickListener(this);
        tvLink2.setOnClickListener(this);
        tvLink3.setOnClickListener(this);
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

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (ServiceMap.zixun == param.key) {
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
        }
        return super.onMsgSearchComplete(param);
    }
}
