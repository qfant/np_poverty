package com.page.information;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.view.CircleImageView;
import com.page.information.InfoPlatformResult.InfoItem;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/9.
 */

public class InfoViewListHolder extends BaseViewHolder<String> {
    @BindView(R.id.text_1)
    TextView text1;

    public InfoViewListHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.activity_info_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, String data, int position) {
        text1.setText( data);
//        ImageLoad.loadPlaceholder(mContext, data.headpic, imagePic);
    }

//    @OnClick(R.id.text_1)
//    public void onViewClicked() {
//        ((BaseActivity)mContext).processAgentPhoneCall(phone);
//    }
}
