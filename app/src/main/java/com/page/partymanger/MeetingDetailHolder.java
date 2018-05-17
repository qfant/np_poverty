package com.page.partymanger;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.imageload.ImageLoad;
import com.framework.view.CircleImageView;
import com.page.uc.chooseavatar.CropImageView;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/9.
 */

public class MeetingDetailHolder extends BaseViewHolder<MeetingDetailResult.MeetingMember> {


    @BindView(R.id.image_pic)
    CircleImageView imagePic;
    @BindView(R.id.text_name)
    TextView textName;

    public MeetingDetailHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.activity_meeting_detail_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, MeetingDetailResult.MeetingMember data, int position) {
//        text1.setText("会议:" + data.title);
//        text2.setText("时间：" + data.createtime + "~" + data.enddate);
//        text3.setText("地点：" + data.address);
        textName.setText(data.username);
        ImageLoad.loadPlaceholder(mContext, data.headpic, imagePic);
    }
}
