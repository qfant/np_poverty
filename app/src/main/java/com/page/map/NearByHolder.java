package com.page.map;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.page.integral.IntegralResult;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/9.
 */

public class NearByHolder extends BaseViewHolder<NearbyResult.NearbyItem> {

    @BindView(R.id.text_1)
    TextView text1;

    public NearByHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.activity_info_item_layout;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, NearbyResult.NearbyItem data, int position) {
        text1.setText("企业:" + data.name);
//        text2.setText("时间：" + data.createtime);
//        text3.setText("积分：" + data.score + "分");
//        ImageLoad.loadPlaceholder(mContext, data.headpic, imagePic);
    }
}
