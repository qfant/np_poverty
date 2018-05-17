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

public class ApplyAccountActivity extends BaseActivity {
    @BindView(R.id.input_phone)
    EditText inputPhone;
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_company)
    EditText inputCompany;
    @BindView(R.id.text_login)
    TextView textLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_apply_account_layout);
        ButterKnife.bind(this);
        setTitleBar("账号申请", true);
    }

    @OnClick(R.id.text_login)
    public void onViewClicked() {
        String company = inputCompany.getText().toString();
        String name = inputName.getText().toString();
        String phone = inputPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showErrorTip(inputPhone, "请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            showErrorTip(inputName, "请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(company)) {
            showErrorTip(inputCompany, "请输入公司");
            return;
        }
        ApplyAccountParam param = new ApplyAccountParam();
        param.name = name;
        param.dpartment = company;
        param.phone = phone;
        Request.startRequest(param, ServiceMap.applyaccount, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.applyaccount) {
            if (param.result.bstatus.code == 0) {
                finish();
            }
            showToast(param.result.bstatus.des);
        }
        return super.onMsgSearchComplete(param);
    }

}
