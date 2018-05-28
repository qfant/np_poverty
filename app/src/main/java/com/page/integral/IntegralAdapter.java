package com.page.integral;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qfant.wuye.R;

/**
 * Created by chenxi.cui on 2018/5/24.
 */

public class IntegralAdapter extends BaseQuickAdapter<IntegralResult.IntegralItem, BaseViewHolder> {
    public IntegralAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, IntegralResult.IntegralItem item) {
        helper.setText(R.id.text_1, "企业:" + item.name)
                .setText(R.id.text_2, "时间：" + item.createtime)
                .setText(R.id.text_3, "积分：" + item.score + "分")
        ;
    }
}
