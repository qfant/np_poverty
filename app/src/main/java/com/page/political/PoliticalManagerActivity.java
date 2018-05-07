package com.page.political;

import android.os.Bundle;

import com.framework.activity.BaseActivity;
import com.qfant.wuye.R;

/**
 * Created by chenxi.cui on 2018/5/7.
 */

public class PoliticalManagerActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_political_layout);
        setTitleBar("指导员管理",true);
    }
}
