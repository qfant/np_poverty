package com.page.partymanger;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.framework.net.Request;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.imageload.ImageLoad;
import com.framework.view.CircleImageView;
import com.page.partymanger.MeetingInfoResult.MeetingInfoItem;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/9.
 */

public class MeetingInfoViewHolder extends BaseViewHolder<MeetingInfoItem> {
    @BindView(R.id.image_pic)
    CircleImageView imagePic;
    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.text_2)
    TextView text2;
    @BindView(R.id.text_3)
    TextView text3;
    @BindView(R.id.text_edit)
    TextView textEdit;
    @BindView(R.id.text_delete)
    TextView textDelete;

    public MeetingInfoViewHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.activity_meeting_info_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, MeetingInfoItem data, int position) {
        text1.setText("会议:" + data.name);
        text2.setText("时间：" + data.createtime);
        text3.setText("内容：" + data.content);
        ImageLoad.loadPlaceholder(mContext, data.headpic, imagePic);
    }

    @OnClick({R.id.text_edit, R.id.text_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_edit:
                break;
            case R.id.text_delete:
                break;
        }
    }
}
