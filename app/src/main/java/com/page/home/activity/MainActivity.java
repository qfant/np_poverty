package com.page.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.framework.activity.FragmentBackHelper;
import com.framework.domain.param.BaseParam;
import com.framework.domain.response.UpgradeInfo;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.ShopCarUtils;
import com.framework.view.tab.TabLayout;
import com.framework.view.tab.TabView;
import com.page.map.MapFragment;
import com.page.party.MineFragment;
import com.page.party.PHomeFragment;
import com.page.party.PManageFragment;
import com.page.party.QpListFragment;
import com.page.store.orderdetails.activity.OrderDetailsActivity;
import com.page.uc.AccountLoginActivity;
import com.page.uc.UCUtils;
import com.page.uc.UserInfoActivity;
import com.page.update.CheckVersionResult;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_mian_layout);
        ButterKnife.bind(this);
        tabLayout = tlTab;
        addTab("首页", PHomeFragment.class, myBundle, R.string.icon_font_home);
        addTab("党建地图", MapFragment.class, myBundle, R.string.icon_font_manger);
        addTab("个人中心", MineFragment.class, myBundle, R.string.icon_font_my);
        onPostCreate();
        checkVersion();
    }
    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.CHECK_VERSION) {
            final CheckVersionResult checkVersionResult = (CheckVersionResult) param.result;
            if (checkVersionResult.bstatus.code == 0) {
                if (checkVersionResult.data != null
                        && checkVersionResult.data.upgradeInfo != null) {
                    updateDialog(checkVersionResult.data.upgradeInfo);
                }

            } else {
                showToast(param.result.bstatus.des);
            }
        }

        return super.onMsgSearchComplete(param);
    }
    private void updateDialog(final UpgradeInfo upgradeInfo) {
        AlertDialog.Builder dialog = null;
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this);
            dialog.setCancelable(!upgradeInfo.force);
            dialog.setTitle("更新提示");
            dialog.setMessage("最新版本："
                    + upgradeInfo.nversion
                    + "\n"
                    + upgradeInfo.upgradeNote);
            dialog.setPositiveButton("更新", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog,
                                    int which) {
                    Uri uri = Uri
                            .parse(upgradeInfo.upgradeAddress[0].url);
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                    dialog.dismiss();
                }

            });
        }
        dialog.show();
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
        if (!UCUtils.getInstance().isLogin()) {
//            qStartActivity(AccountLoginActivity.class);
        }
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

    private void checkVersion() {
        Request.startRequest(new BaseParam(), ServiceMap.CHECK_VERSION, mHandler);
    }

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
    }

}
