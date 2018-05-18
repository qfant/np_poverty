package com.page.integral;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.view.CircleImageView;
import com.page.information.InfoPlatformResult.InfoItem;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/9.
 */

public class IntegralHolder extends BaseViewHolder<IntegralResult.IntegralItem> {

//    @BindView(R.id.image_pic)
//    CircleImageView imagePic;
    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.text_2)
    TextView text2;
    @BindView(R.id.text_3)
    TextView text3;

    public IntegralHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.activity_info_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, IntegralResult.IntegralItem data, int position) {
        text1.setText("企业:" + data.title);
        text2.setText("时间：" + data.createtime);
        text3.setText("积分：" + data.score + "分");
//        ImageLoad.loadPlaceholder(mContext, data.headpic, imagePic);
    }
}
