package com.page.information;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.framework.activity.BaseActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.qfant.wuye.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxi.cui on 2018/7/1.
 */

public class InfoChartActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_chart_layout);
        String  name = myBundle.getString("id");
        setTitleBar(name,true);
        BarChart barChart1 = (BarChart) findViewById(R.id.bar_chart1);
        BarChart barChart2 = (BarChart) findViewById(R.id.bar_chart2);
        LinearLayout ll1 = (LinearLayout) findViewById(R.id.ll_1);
        LinearLayout ll0 = (LinearLayout) findViewById(R.id.ll_0);
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qStartActivity(InfoPlatformActivity.class);
            }
        });
        ll0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qStartActivity(InfoPlatformActivity.class);
            }
        });
        barChart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qStartActivity(InfoPlatformActivity.class);
            }
        });
        barChart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qStartActivity(InfoPlatformActivity.class);
            }
        });
        BarChartManager barChartManager1 = new BarChartManager(barChart1);
        BarChartManager barChartManager2 = new BarChartManager(barChart2);

        //设置x轴的数据
        ArrayList<Float> xValues = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            xValues.add((float) i);
        }

        //设置y轴的数据()
        List<List<Float>> yValues = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<Float> yValue = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                    yValue.add((float) (Math.random() * 80));
            }
            yValues.add(yValue);
        }

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
        barChartManager2.showBarChart(xValues, yValues, names1, colours);
    }
}
