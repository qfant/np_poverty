package com.page.partymanger;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.imageload.ImageLoad;
import com.page.partymanger.MeetingListResult.MeetingItem;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/9.
 */

public class MeetingListViewHolder extends BaseViewHolder<MeetingItem> {


    @BindView(R.id.image_pic)
    ImageView imagePic;
    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.text_2)
    TextView text2;
    @BindView(R.id.text_3)
    TextView text3;

    public MeetingListViewHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.activity_meeting_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, MeetingItem data, int position) {
        text1.setText("会议:" + data.title);
        text2.setText("时间：" + data.startdate + "~" + data.enddate);
        text3.setText("地点：" + data.address);
        ImageLoad.loadPlaceholder(mContext, data.headpic, imagePic);
    }
}
