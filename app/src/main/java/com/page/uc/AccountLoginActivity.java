package com.page.uc;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2018/5/7.
 */

public class AccountLoginActivity extends BaseActivity {
    @BindView(R.id.tiet_username)
    EditText tietName;
    @BindView(R.id.tiet_password)
    EditText tilPassword;
    @BindView(R.id.text_login)
    TextView textLogin;
    @BindView(R.id.tv_rig)
    TextView tvRig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login_layout);
        ButterKnife.bind(this);
        setTitleBar("登录", false);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }


    @OnClick({R.id.text_login, R.id.tv_rig})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_login:

                break;
            case R.id.tv_rig:
                qStartActivity(ApplyAccountActivity.class);
                break;
        }
    }
}
