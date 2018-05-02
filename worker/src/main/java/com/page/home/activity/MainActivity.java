package com.page.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.framework.app.MainApplication;
import com.framework.utils.ArrayUtils;
import com.framework.view.tab.TabLayout;
import com.haolb.client.R;
import com.page.login.UCUtils;
import com.page.login.activity.LoginActivity;
import com.page.uc.UserInfoActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by shucheng.qu on 2017/5/27.
 */

public class MainActivity extends MainTabActivity {

    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.tv_right)
    TextView tv_right;
    private boolean mIsExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_mian_layout);
        ButterKnife.bind(this);
        tabLayout = tlTab;
        addTab("视频监控", HomeFragment.class, myBundle, R.string.icon_font_a);
        addTab("内部通知", MessageFragment.class, myBundle, R.string.icon_font_b);
        addTab("六员一岗", TownsFragment.class, myBundle, R.string.icon_font_c);
        addTab("企业用工", TownsFragment.class, myBundle, R.string.icon_font_d);
        onPostCreate();
        tv_right.setTypeface(MainApplication.getIconFont());
        tv_right.setText(R.string.icon_font_my);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qStartActivity(UserInfoActivity.class);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!UCUtils.getInstance().isLogin()) {
            Intent intent = new Intent();
            intent.setClass(getContext(), LoginActivity.class);
            qStartActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (!onBackPressedWithFragment()) {
            exitBy2Click();
        }
    }

    public void exitBy2Click() {
        Timer tExit;
        if (!mIsExit) {
            mIsExit = true;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    mIsExit = false;
                }
            }, 2000);
        } else {
            finish();
        }
    }

    boolean onBackPressedWithFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            List<Fragment> fragments = fragmentManager.getFragments();
            if (!ArrayUtils.isEmpty(fragments)) {
                for (Fragment fragment : fragments) {
                    if (fragment == null) {
                        return false;
                    }
//                    if (fragment.isVisible()) {
//                        FragmentOnBackListener backListener = (FragmentOnBackListener) fragment;
//                        if (backListener.onBackPressed()) {
//                            return true;
//                        }
//                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


}
