package com.page.partymanger;

import android.os.Bundle;

import com.framework.activity.BaseActivity;
import com.qfant.wuye.R;

/**
 * Created by chenxi.cui on 2018/5/7.
 */

public class PartyMangerActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_party_manger_layout);
        setTitleBar("党员管理", true);
    }
}
