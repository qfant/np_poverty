package com.page.political;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.imageload.ImageLoad;
import com.page.community.eventlist.model.EventListResult.Data.ActivityList;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.page.political.WorkLogListResult.*;

/**
 * Created by shucheng.qu on 2017/8/9.
 */

public class ViewHolder extends BaseViewHolder<WorkLogItem> {


    @BindView(R.id.image_head)
    ImageView ivImage;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.text_content)
    TextView textContent;

    public ViewHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.activity_work_log_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, WorkLogItem data, int position) {
        textTitle.setText("标题: " + data.title);
        textAddress.setText("地点：" + data.address);
        textContent.setText("内容：" + data.content);
        ImageLoad.loadPlaceholder(mContext, data.pic1, ivImage);
    }

}
