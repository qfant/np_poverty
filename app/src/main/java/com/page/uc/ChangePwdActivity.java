package com.page.uc;

import android.os.Bundle;

import com.framework.activity.BaseActivity;
import com.qfant.wuye.R;

/**
 * Created by chenxi.cui on 2018/5/7.
 */

public class ChangePwdActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_change_pwd_layout);
        setTitleBar("修改密码", true);
    }
}
