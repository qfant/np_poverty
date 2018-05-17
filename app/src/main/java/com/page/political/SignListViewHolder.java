package com.page.political;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.page.political.SignListResult.SignListItem;

/**
 * Created by shucheng.qu on 2017/8/9.
 */

public class SignListViewHolder extends BaseViewHolder<SignListItem> {

    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.text_2)
    TextView text2;
    @BindView(R.id.text_3)
    TextView text3;

    public SignListViewHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.activity_sign_list_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, SignListItem data, int position) {
        text1.setText("公司: " + data.partybranchname);
        if ("1".equals(data.type)) {
            text2.setText("签到：" + data.signdate);
        } else {
            text2.setText("签退：" + data.signdate);
        }
        text3.setText(data.day);
//        ImageLoad.loadPlaceholder(mContext, data.pic1, ivImage);
    }

}
