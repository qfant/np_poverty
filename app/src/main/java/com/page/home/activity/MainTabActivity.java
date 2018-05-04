package com.page.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.framework.view.tab.TabItem;
import com.framework.view.tab.TabLayout;
import com.qfant.wuye.R;
import com.framework.activity.BaseActivity;
import com.framework.activity.BaseFragment;

import java.util.ArrayList;

/**
 * Created by shucheng.qu on 2017/5/31.
 */

public class MainTabActivity extends BaseActivity implements TabLayout.OnTabClickListener {

    protected final ArrayList<TabItem> mTabs = new ArrayList<TabItem>();

    //    @BindView(R.id.tl_tab)
    TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabLayout = (TabLayout) findViewById(R.id.title_bar);
    }

    protected void addTab(String text, Class<? extends BaseFragment> clss, Bundle bundle, int... icon) {
        TabItem tabItem = new TabItem(text, icon, clss, bundle);
        if (!mTabs.contains(tabItem)) {
            mTabs.add(tabItem);
        }
    }

    protected void onPostCreate() {
        tabLayout.initData(mTabs, this);
        tabLayout.setCurrentTab(0);
    }

    @Override
    public void onTabClick(TabItem tabItem) {
        int index = mTabs.indexOf(tabItem);
//                try {
//            BaseFragment fragment = tabItem.tagFragmentClz.newInstance();
//            getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, fragment).commitAllowingStateLoss();
//            tabLayout.setCurrentTab(mTabs.indexOf(tabItem));
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
        try {
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            if (fragmentManager1.findFragmentByTag(tabItem.text) == null && !tabItem.isAdd) {
                tabItem.isAdd = true;
                BaseFragment fragment = tabItem.tagFragmentClz.newInstance();
                FragmentTransaction transaction = fragmentManager1.beginTransaction();
                fragment.setArguments(tabItem.bundle);

                transaction.add(R.id.fl_content, fragment, tabItem.text);
                transaction.commitAllowingStateLoss();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            for (int i = 0; i < mTabs.size(); i++) {
                Fragment fragment = fragmentManager.findFragmentByTag(mTabs.get(i).text);
                if (fragment != null) {
                    if (i == index) {
                        transaction.show(fragment);
                    } else {
                        transaction.hide(fragment);
                    }
                }
            }
            try {
                transaction.commitAllowingStateLoss();
            } catch (IllegalStateException e) {
                //修复bug#101825：java.lang.IllegalStateException: Activity has been destroyed
            }

            tabLayout.setCurrentTab(mTabs.indexOf(tabItem));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentTab(int index) {
        tabLayout.setCurrentTab(index);
    }
}
