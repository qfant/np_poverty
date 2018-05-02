package com.bz.poverty;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.framework.domain.param.BaseParam;
import com.framework.domain.response.BaseResult;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.view.tab.TabItem;
import com.framework.view.tab.TabLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chenxi.cui on 2017/9/30.
 */

public class MainActivity extends MainTabActivity {
    TabLayout tlTab;
    private WeatherLayout weatherView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        tlTab = (TabLayout) findViewById(R.id.tl_tab);
        weatherView = (WeatherLayout) findViewById(R.id.weather_view);
        tabLayout = tlTab;
        addTab("首页", HomeFragment.class, myBundle, R.drawable.icon_userinfo);
        addTab("党建地图", MapFragment.class, myBundle, R.drawable.icon_mark);
        addTab("基本情况", WebFragment.class, getBundle("基本情况","http://dj.qfant.com/index.php/App/Index/baseinfo/id/11"), R.drawable.icon_online);
        addTab("标准化建设", WebFragment.class, getBundle("标准化建设","http://dj.qfant.com/index.php/App/Index/news/catid/26"), R.drawable.icon_feature);
        addTab("工作动态", WebFragment.class, getBundle("工作动态","http://dj.qfant.com/index.php/App/Index/news/catid/23"), R.drawable.icon_digitize);
        addTab("组织生活", WebFragment.class, getBundle("组织生活","http://dj.qfant.com/index.php/App/Index/news/catid/20"), R.drawable.icon_twolearn);
        addTab("党员信息", WebFragment.class, getBundle("党员信息","http://dj.qfant.com/index.php/App/Index/dangyuan"), R.drawable.icon_userinfo);
        addTab("数字化阵地", WebFragment.class, getBundle("数字化阵地","http://dj.qfant.com/index.php/App/Index/cameras/id/2"), R.drawable.iocn_base);
        addTab("组织关系转接", WebFragment.class, getBundle("组织关系转接","http://dj.qfant.com/index.php/App/Index/groupindex"), R.drawable.icon_connection);
        addTab("微信党支部", WebFragment.class, getBundle("微信党支部","http://dj.qfant.com/index.php/App/Index/dangyuan"), R.drawable.iocn_signin);
        addTab("个人中心", WebFragment.class, getBundle("个人中心","http://dj.qfant.com/index.php/App/User/userinfo"), R.drawable.icon_usercenter);
        onPostCreate();
        initView();
    }

    private void initView() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Request.startRequest(new BaseParam(), ServiceMap.weather, mHandler);
            }
        };
        timer.schedule(timerTask, 0, 1000 * 60 * 30);

    }

    @Override
    public void onTabClick(TabItem tabItem) {
        if ("微信党支部".equals(tabItem.text)) {
            try {
                showToast("打开微信");
                Intent intent = new Intent();
                ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(cmp);
                startActivity(intent);
            } catch (Exception e) {
                showToast("未安装微信");
            }

        } else {
            super.onTabClick(tabItem);
        }
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.weather) {
            WeatherResult result = (WeatherResult) param.result;
            if (result.data != null && result.data.size() > 0) {
                weatherView.setData(result.data.get(0));
            }
        }
        return super.onMsgSearchComplete(param);
    }

    private Bundle getBundle(String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        return bundle;
    }
}
