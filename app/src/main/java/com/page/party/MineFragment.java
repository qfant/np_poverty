package com.page.party;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseFragment;
import com.framework.utils.imageload.ImageLoad;
import com.framework.view.CircleImageView;
import com.page.uc.AboutUsActivity;
import com.page.uc.AccountLoginActivity;
import com.page.uc.ApplyAccountActivity;
import com.page.uc.ChangePwdActivity;
import com.page.uc.DocsHelpActivity;
import com.page.uc.UCUtils;
import com.page.uc.UserInfoActivity;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by chenxi.cui on 2017/6/21.
 * "我的"页面
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_job)
    TextView tvJob;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.image_head)
    CircleImageView imageHead;
    @BindView(R.id.list_view)
    RecyclerView listView;

    public static int[] iconArr = {
            R.drawable.icon_books,
            R.drawable.icon_certification,
            R.drawable.icon_integral,
            R.drawable.icon_activities,
    };

    public static String[] titleArr = {"修改头像", "修改密码", "使用帮助", "关于我们"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_activity_user_info, null);
        Unbinder unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MineAdapter adapter = new MineAdapter(iconArr, titleArr);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemOnClickListener(new MineAdapter.OnItemOnClickListener() {
            @Override
            public void onItemOnClickListener(int pos) {
                switch (pos) {
                    case 0://修改头像
                        qStartActivity(UserInfoActivity.class);
                        break;
                    case 1://修改密码

                        qStartActivity(ChangePwdActivity.class);
                        break;
                    case 2://使用帮助
                        qStartActivity(DocsHelpActivity.class);
                        break;
                    case 3://关于我们
                        qStartActivity(AboutUsActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });
        llHeader.setOnClickListener(this);
        imageHead.setOnClickListener(this);
        tvSetting.setOnClickListener(this);
        setData();
    }

    private void setData() {
        tvName.setText(UCUtils.getInstance().getUserInfo().phone);
//        tvJob.setText("");
        tvJob.setVisibility(View.GONE);
        ImageLoad.loadPlaceholder(getContext(), "", imageHead);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(llHeader)) {
            if (UCUtils.getInstance().isLogin()) {
                qStartActivity(UserInfoActivity.class);
            } else {
                qStartActivity(AccountLoginActivity.class);
            }
        } else if (imageHead.equals(v) || v.equals(tvSetting)) {
            qStartActivity(UserInfoActivity.class);
        }
    }

}
