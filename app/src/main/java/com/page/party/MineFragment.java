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
import com.page.uc.LoginActivity;
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
            R.drawable.icon_management,
            R.drawable.icon_record,
            R.drawable.icon_unity,
            R.drawable.icon_voice,
            R.drawable.icon_vote,
    };

    public static String[] titleArr = {"我的书籍", "党员认证", "积分兑换", "我的活动"
            , "村级管理", "学习记录", "结亲连心", "百姓心声", "在线投票"
    };

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
                String path = null;
                switch (pos) {
                    case 0://个人资料
                        break;
                    case 1://修改密码
                        path = "mine/mine-modify-new-password.js";
                        break;
                    case 2://意见反馈
                        path = "mine/mine-advice-feedback.js";
                        break;
                    case 3://关于我们
                        path = "mine/mine-about-us.js";
                        break;
                    case 4://更多设置
                        path = "mine/mine-more.js";
                        break;
                    default:
                        break;
                }
                showToast("功能开发中...");
            }
        });
        llHeader.setOnClickListener(this);
        imageHead.setOnClickListener(this);
        tvSetting.setOnClickListener(this);
        setData();
    }

    private void setData() {
        tvName.setText("习大大");
        tvJob.setText("中华人民共和国主席");
        ImageLoad.loadPlaceholder(getContext(), "", imageHead);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(llHeader)) {
            if (UCUtils.getInstance().isLogin()) {
                qStartActivity(UserInfoActivity.class);
            } else {
                qStartActivity(LoginActivity.class);
            }
        } else if (imageHead.equals(v)||v.equals(tvSetting)) {
            qStartActivity(UserInfoActivity.class);
        }
    }

}
