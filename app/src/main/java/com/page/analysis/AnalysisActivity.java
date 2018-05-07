package com.page.analysis;

import android.os.Bundle;

import com.framework.activity.BaseActivity;
import com.qfant.wuye.R;

/**
 * Created by chenxi.cui on 2018/5/7.
 */

public class AnalysisActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_analysis_layout);
        setTitleBar("统计分析", true);
    }
}
