package com.page.political;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.ArrayUtils;
import com.framework.view.LineDecoration;
import com.framework.view.pull.SwipRefreshLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.page.information.BarChartManager;
import com.page.political.PoliticalListResult.PoliticalItem;
import com.qfant.wuye.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2018/5/16.
 */

public class PoliticalListActivity extends BaseActivity implements OnItemClickListener<PoliticalItem>, SwipRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout srlDownRefresh;
    @BindView(R.id.bar_chart1)
    BarChart barChart1;
    @BindView(R.id.bar_chart2)
    BarChart barChart2;
    @BindView(R.id.text_4)
    TextView text4;
    @BindView(R.id.chart1)
    CombinedChart chart1;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    private MultiAdapter<PoliticalItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_list_layout);
        ButterKnife.bind(this);
        setTitleBar("指导员管理", true);
        setListView();
        startRequest(1);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setListView() {
//        final PoliticalListViewHolder holder = new PoliticalListViewHolder(this, LayoutInflater.from(this).inflate(R.layout.activity_po_list_item_layout, null, false));
        adapter = new MultiAdapter<PoliticalItem>(getContext()).addTypeView(new ITypeView<PoliticalItem>() {
            @Override
            public boolean isForViewType(PoliticalItem item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new PoliticalListViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.activity_po_list_item_layout, parent, false), new PoliticalListViewHolder.PoCallback() {
                    @Override
                    public void callback1(PoliticalItem data) {
                        if (data.signin == 0) {
                            showToast("已经签到");
                            return;
                        }
                        SignParam signParam = new SignParam(data.latitude, data.longitude);
                        signParam.id = data.id;
                        Request.startRequest(signParam, ServiceMap.signin, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
                    }

                    @Override
                    public void callback2(PoliticalItem data) {
                        if (data.signin == 1) {
                            showToast("已经签退");
                            return;
                        }
                        SignParam signParam1 = new SignParam(data.latitude, data.longitude);
                        signParam1.id = data.id;
                        Request.startRequest(signParam1, ServiceMap.signout, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);

                    }
                });
            }
        });
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);
//        adapter.setOnItemClickListener(this);
        srlDownRefresh.setOnRefreshListener(this);
//        holder.setPoCallback(new PoliticalListViewHolder.PoCallback() {
//            @Override
//            public void callback1(PoliticalItem data) {
//                if (data.signin == 0) {
//                    showToast("已经签到");
//                    return;
//                }
//                SignParam signParam = new SignParam(data.latitude, data.longitude);
//                signParam.id = data.id;
//                Request.startRequest(signParam, ServiceMap.signin, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
//            }
//
//            @Override
//            public void callback2(PoliticalItem data) {
//                if (data.signin == 1) {
//                    showToast("已经签退");
//                    return;
//                }
//                SignParam signParam1 = new SignParam(data.latitude, data.longitude);
//                signParam1.id = data.id;
//                Request.startRequest(signParam1, ServiceMap.signout, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
//
//            }
//        });
    }


    private void startRequest(int page) {
        PoliticalListParam param = new PoliticalListParam();
        if (page == 1) {
            Request.startRequest(param, page, ServiceMap.queryByZhidao, mHandler, Request.RequestFeature.BLOCK, Request.RequestFeature.CANCELABLE);
        } else {
            Request.startRequest(param, page, ServiceMap.queryByZhidao, mHandler);
        }
    }


    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.queryByZhidao) {
            final PoliticalListResult result = (PoliticalListResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.partyBranchList)) {
                setData(result.data);
                if ((int) param.ext == 1) {
                    adapter.clearData();
                    adapter.setData(result.data.partyBranchList);
                } else {
                    adapter.addData(result.data.partyBranchList);
                }
            } else {
                showToast("没有更多了");
            }
            srlDownRefresh.setRefreshing(false);
        } else if (ServiceMap.signin == param.key || ServiceMap.signout == param.key) {
            if (param.result.bstatus.code == 0) {
                showToast("成功");
                startRequest(1);
            } else {
                showToast(param.result.bstatus.des);
            }
        }
        return false;
    }

    private void setData(PoliticalListResult.PoliticalData data) {

        BarChartManager barChartManager1 = new BarChartManager(barChart1);
        BarChartManager barChartManager2 = new BarChartManager(barChart2);

        XAxis xAxis = barChart1.getXAxis();

        //设置x轴的数据
        ArrayList<Float> xValues = new ArrayList<>();
//        for (int i = 0; i <2; i++) {
//            xValues.add((float) i);
//        }

        //设置y轴的数据()
        List<List<Float>> yValues = new ArrayList<>();
        List<List<Float>> yValues1 = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            List<Float> yValue = new ArrayList<>();
//            for (int j = 0; j <2; j++) {
//                yValue.add((float) (Math.random() * 80));
//            }
//            yValues.add(yValues);
//        }
        if (data.signins != null) {
            List<Float> sum = new ArrayList<>();
            for (int i = 0; i < data.signins.size(); i++) {
                sum.add((float) data.signins.get(i).shuliang);
            }
            yValues.add(sum);

            List<Float> sum1 = new ArrayList<>();
            for (int i = 0; i < data.signouts.size(); i++) {
                sum1.add((float) data.signouts.get(i).shuliang);
            }
            yValues.add(sum1);
        }
        if (data.worklogs != null) {
            List<Float> sum = new ArrayList<>();
            for (int i = 0; i < data.worklogs.size(); i++) {
                sum.add((float) data.worklogs.get(i).shuliang);
            }
            yValues1.add(sum);
        }

        //颜色集合
        List<Integer> colours = new ArrayList<>();
        colours.add(Color.GREEN);
        colours.add(Color.BLUE);
        List<Integer> colours1 = new ArrayList<>();
        colours1.add(Color.RED);
        //线的名字集合
        final List<String> names = new ArrayList<>();
        if (data.signins != null) {
            for (int i = 0; i < data.signins.size(); i++) {
                names.add(data.signins.get(i).name);
                xValues.add((float) i);
            }
        }

        final List<String> names1 = new ArrayList<>();
        ArrayList<Float> xValues1 = new ArrayList<>();
        if (data.worklogs != null) {
            for (int i = 0; i < data.worklogs.size(); i++) {
                names1.add(data.worklogs.get(i).name);
                xValues1.add((float) i);
            }
        }

        //创建多条折线的图表
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value == 0) {
                    return "";
                }
                return   names.get((int) value - 1) + "                                        .";
            }
        });
        barChart2.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(names1.size()<value){
                    return "";
                }
                return  names1.get((int) value ) + "";

            }
        });
        List<String> libs = new ArrayList<>();
        libs.add("签到");
        libs.add("签退");
        List<String> libs1 = new ArrayList<>();
        libs1.add("工作日志");
        barChartManager1.showBarChart(xValues, yValues, libs, colours);
//        barChartManager2.showBarChart(xValues1, yValues1, libs1, colours1);
        barChartManager2.showBarChart(xValues1, yValues1.get(0), libs1.get(0), colours1.get(0));

        barChartManager1.setXAxis(names.size(), 0f, names.size());
//        barChartManager2.setXAxis(names1.size(), 0f, names1.size());
//        barChartManager2.setXAxis(names1.size()+2, -2f, names1.size());
//        barChartManager2.setXAxis(2f, -1f,2);
    }

    @Override
    public void onItemClickListener(View view, PoliticalItem data, int position) {
//        Bundle bundle = new Bundle();
//        bundle.putString(EventDetailActivity.ID, data.id);
//        qStartActivity(EventDetailActivity.class, bundle);
    }

    @Override
    public void onRefresh(int index) {
        startRequest(1);
    }

    @Override
    public void onLoad(int index) {
        startRequest(++index);
    }

    @OnClick(R.id.text_4)
    public void onViewClicked() {
        qStartActivity(WorkLogListActivity.class);
    }
}
