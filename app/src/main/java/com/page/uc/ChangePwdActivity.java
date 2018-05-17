package com.page.uc;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2018/5/7.
 */

public class ChangePwdActivity extends BaseActivity {
    @BindView(R.id.input_old_pwd)
    EditText inputOldPwd;
    @BindView(R.id.input_new_pwd_1)
    EditText inputNewPwd1;
    @BindView(R.id.input_new_pwd)
    EditText inputNewPwd;
    @BindView(R.id.text_submit)
    TextView textSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_change_pwd_layout);
        ButterKnife.bind(this);
        setTitleBar("修改密码", true);
        if (!UCUtils.getInstance().isLogin()) {
            qStartActivity(AccountLoginActivity.class);
            return;
        }
    }

    @OnClick(R.id.text_submit)
    public void onViewClicked() {
        if (!UCUtils.getInstance().isLogin()) {
            qStartActivity(AccountLoginActivity.class);
            return;
        }
        String newPWD0 = inputNewPwd1.getText().toString();
        String newPWD1 = inputNewPwd.getText().toString();
        String oldPWD1 = inputOldPwd.getText().toString();
        if (TextUtils.isEmpty(oldPWD1)) {
            showErrorTip(inputOldPwd, "请输入初始密码");
            return;
        }
        if (TextUtils.isEmpty(newPWD0)) {
            showErrorTip(inputNewPwd1, "请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(newPWD1)) {
            showErrorTip(inputNewPwd, "请输入确认密码");
            return;
        }
        if (!TextUtils.equals(newPWD0, newPWD1)) {
            showErrorTip(inputNewPwd, "请确认两次输入的新密码是否相同");
            return;
        }
        if (TextUtils.equals(oldPWD1, newPWD1)) {
            showErrorTip(inputNewPwd, "原始密码和新密码相同");
            return;
        }
        ChangePwdParam pwdParam = new ChangePwdParam();
        pwdParam.phone = UCUtils.getInstance().getUserInfo().phone;
        pwdParam.oldPassword = oldPWD1;
        pwdParam.newPassword = newPWD0;
        Request.startRequest(pwdParam, ServiceMap.editPassword, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.editPassword) {
            if (param.result.bstatus.code == 0) {

            }
            showToast(param.result.bstatus.des);

        }
        return super.onMsgSearchComplete(param);
    }
}
