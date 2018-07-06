package com.page.information;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.github.mikephil.charting.charts.BarChart;
import com.page.information.ManagermentResult.ManagermentItem;
import com.qfant.wuye.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxi.cui on 2018/7/1.
 */

public class InfoChartActivity extends BaseActivity {
    @BindView(R.id.bar_chart1)
    BarChart barChart1;
    @BindView(R.id.bar_chart2)
    BarChart barChart2;
    @BindView(R.id.ll_0)
    LinearLayout ll0;
    private ManagermentItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_chart_layout);
        item = (ManagermentItem) myBundle.getSerializable("id");
        ButterKnife.bind(this);
        setTitleBar(item.name, true);
        requestData();
    }

    private void requestData() {
        QueryNumParam param = new QueryNumParam();
        param.managerId = item.id;
        Request.startRequest(param, ServiceMap.tongji, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
//        Request.startRequest(param, ServiceMap.tongji2, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.tongji) {
            TongjiResult result = (TongjiResult) param.result;
            setData(result.data);

        } else if (param.key == ServiceMap.tongji2) {

        }
        return super.onMsgSearchComplete(param);
    }

    private void setData(TongjiResult.TojiData data) {
        BarChart barChart1 = (BarChart) findViewById(R.id.bar_chart1);
        BarChart barChart2 = (BarChart) findViewById(R.id.bar_chart2);
        BarChartManager barChartManager1 = new BarChartManager(barChart1);
        BarChartManager barChartManager2 = new BarChartManager(barChart2);

        //设置x轴的数据
        ArrayList<Float> xValues = new ArrayList<>();
        for (int i = 0; i <2; i++) {
            xValues.add((float) i);
        }

        //设置y轴的数据()
        List<List<Float>> yValues = new ArrayList<>();
        List<List<Float>> yValues1 = new ArrayList<>();
        yValues.add(data.sum);
        yValues.add(data.sumguimo);
        yValues1.add(data.isparty);
        yValues1.add(data.isstandard);
//        for (int i = 0; i < 2; i++) {
//            List<Float> yValue = new ArrayList<>();
//            for (int j = 0; j <2; j++) {
//                yValue.add((float) (Math.random() * 80));
//            }
//            yValues.add(yValues);
//        }

        //颜色集合
        List<Integer> colours = new ArrayList<>();
        colours.add(Color.GREEN);
        colours.add(Color.BLUE);

        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("数量");
        names.add("规模以上企业");
        List<String> names1 = new ArrayList<>();
        names1.add("成立党支部比例");
        names1.add("标准化建设达标比例");

        //创建多条折线的图表
        barChartManager1.showBarChart(xValues, yValues, names, colours);
        barChartManager2.showBarChart(xValues, yValues1, names1, colours);
        barChartManager1.setXAxis(2f, 0f, 2);
        barChartManager2.setXAxis(2f, 0f, 2);
    }

    @OnClick({R.id.text_1, R.id.text_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_2:
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.id);
                bundle.putInt("type", 2);
                qStartActivity(InfoPlatformActivity.class, bundle);
                break;
            case R.id.text_1:
                Bundle bundle1 = new Bundle();
                bundle1.putInt("id", item.id);
                bundle1.putInt("type", 1);
                qStartActivity(InfoPlatformActivity.class, bundle1);
                break;
        }
    }
}
