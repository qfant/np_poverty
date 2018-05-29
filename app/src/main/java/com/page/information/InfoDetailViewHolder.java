package com.page.information;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.page.information.InfoPlatformResult.InfoItem;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/9.
 */

public class InfoDetailViewHolder extends BaseViewHolder<InfoDetailResult.InfoDetailItem> {

    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.text_2)
    TextView text2;

    public InfoDetailViewHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.activity_info_detail_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, InfoDetailResult.InfoDetailItem data, int position) {
        text1.setText(data.key);
        text2.setText(TextUtils.equals(data.name, "0") ? "否" : TextUtils.equals(data.name, "1") ? "是" : data.name);
//        ImageLoad.loadPlaceholder(mContext, data.headpic, imagePic);
    }
}
