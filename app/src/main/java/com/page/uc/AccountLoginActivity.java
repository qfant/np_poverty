package com.page.uc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.igexin.sdk.PushManager;
import com.page.home.activity.MainActivity;
import com.page.uc.bean.LoginParam;
import com.page.uc.bean.LoginResult;
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
        setTitleBar("登录", true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @OnClick({R.id.text_login, R.id.tv_rig})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_login:
                sendLogin();
                break;
            case R.id.tv_rig:
                qStartActivity(ApplyAccountActivity.class);
                break;
        }
    }

    private void sendLogin() {

        String phone = tietName.getText().toString();
        String psw = tilPassword.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(psw)) {
            showToast("请输入密码");
            return;
        }
        LoginParam loginParam = new LoginParam();
        loginParam.password = psw;
        loginParam.phone = phone;
        Request.startRequest(loginParam, ServiceMap.customerLogin, mHandler, Request.RequestFeature.BLOCK, Request.RequestFeature.CANCELABLE);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.customerLogin) {
            if (param.result.bstatus.code == 0) {
                //登录成功
                LoginResult result = (LoginResult) param.result;
                UCUtils.getInstance().saveUserInfo(result.data);
                PushManager.getInstance().bindAlias(getContext(), result.data.phone);

                Intent intent = new Intent();
                intent.setClass(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                qStartActivity(intent);
                finish();
            } else {
                showToast(param.result.bstatus.des);
            }

        }
        return super.onMsgSearchComplete(param);
    }

}
