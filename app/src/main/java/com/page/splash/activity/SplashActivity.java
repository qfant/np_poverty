package com.page.splash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.framework.activity.BaseActivity;
import com.igexin.sdk.PushManager;
import com.page.home.activity.MainActivity;
import com.page.uc.LoginActivity;
import com.page.uc.UCUtils;
import com.qfant.wuye.R;


/**
 * Created by shucheng.qu on 2017/5/27.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_layout);
        PushManager.getInstance().initialize(this.getApplication(), com.qfant.wuye.push.PushService.class);

//        qStartActivity(LoginActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (UCUtils.getInstance().isLogin()) {
                startMainActivity();
//                } else {
//                    qStartActivity(LoginActivity.class);
//                }
                finish();
            }
        }, 100);
    }


    private void startMainActivity() {
        Intent intent = new Intent();
        intent.setClass(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        qStartActivity(intent);
    }
}
