package com.page.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.framework.activity.FragmentBackHelper;
import com.framework.utils.ShopCarUtils;
import com.framework.view.tab.TabLayout;
import com.framework.view.tab.TabView;
import com.page.party.MineFragment;
import com.page.party.PHomeFragment;
import com.page.party.PManageFragment;
import com.page.party.QpListFragment;
import com.page.store.orderdetails.activity.OrderDetailsActivity;
import com.qfant.wuye.R;
import com.page.uc.UserCenterFragment;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by shucheng.qu on 2017/5/27.
 */

public class MainActivity extends MainTabActivity {

    public static final String REFRESH_TAB_ACTION = "com.qfant.wuye.refreshtab";

    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    private boolean mIsExit;
    private TabNumberReceiver tabNumberReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_mian_layout);
        ButterKnife.bind(this);
        tabLayout = tlTab;
        addTab("智慧党建", PHomeFragment.class, myBundle, R.string.icon_font_home);
        addTab("党员管理", PManageFragment.class, myBundle, R.string.icon_font_manger);
        addTab("党员生活", QpListFragment.class, myBundle, R.string.icon_font_camera);
        addTab("个人中心", MineFragment.class, myBundle, R.string.icon_font_my);
        onPostCreate();
        tabNumberReceiver = new TabNumberReceiver();
        IntentFilter filter = new IntentFilter(REFRESH_TAB_ACTION);
        registerReceiver(tabNumberReceiver, filter);


    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent != null && intent.getExtras() != null) {
            String aGoto = intent.getExtras().getString("goto");
            if (TextUtils.equals(aGoto, "orderDetail")) {
                String id = intent.getExtras().getString("id");
                Bundle bundle = new Bundle();
                bundle.putString(OrderDetailsActivity.ID, "" + id);
                qBackToActivity(OrderDetailsActivity.class, bundle);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendBroadcast(new Intent(REFRESH_TAB_ACTION));
    }

//    @Override
//    public void onTabClick(TabItem tabItem) {
////        if ("随手拍".equals(tabItem.text)) {
////            qStartActivity(AddQPaiActivity.class);
////        } else {
//        super.onTabClick(tabItem);
////        }
//    }


    @Override
    public void onBackPressed() {
        if (!FragmentBackHelper.onBackPressed(this)) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(tabNumberReceiver);
    }

    public class TabNumberReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            TabView shopCarTab = (TabView) tabLayout.getChildAt(3);
            shopCarTab.setNumber(ShopCarUtils.getInstance().getShopCarSize());
        }
    }

}
