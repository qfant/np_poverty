package com.page.uc;

import android.os.Bundle;

import com.framework.activity.BaseActivity;
import com.qfant.wuye.R;

/**
 * Created by chenxi.cui on 2018/5/7.
 * 使用帮助
 */

public class DocsHelpActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_docs_help_layout);
        setTitleBar("使用帮助", true);
    }
}
