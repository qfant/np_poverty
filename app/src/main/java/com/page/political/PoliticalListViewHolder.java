package com.page.political;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.qfant.wuye.R;

/**
 * Created by shucheng.qu on 2017/8/9.
 */


public class PoliticalListViewHolder extends BaseViewHolder<PoliticalListResult.PoliticalItem> {

    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.text_2)
    TextView text2;
    @BindView(R.id.text_3)
    TextView text3;
    private PoCallback callback;

    public PoliticalListViewHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.activity_po_list_item_layout;
        ButterKnife.bind(this, itemView);
    }

    public PoliticalListViewHolder(Context mContext, View inflate, PoCallback poCallback) {
        super(mContext, inflate);
        ButterKnife.bind(this, itemView);
        this.callback = callback;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, PoliticalListResult.PoliticalItem data, int position) {
        text1.setText(data.name);
        text2.setEnabled(data.signin == 1);
        text3.setEnabled(data.signin == 0);
        text2.setTag(data);
        text3.setTag(data);

//        text2.setEnabled(data.signin);
//        text3.setText(data.name);
    }

    public interface PoCallback {
        void callback1(PoliticalListResult.PoliticalItem data);

        void callback2(PoliticalListResult.PoliticalItem data);
    }

    public void setPoCallback(PoCallback callback) {
        this.callback = callback;
    }


    @OnClick({R.id.text_2, R.id.text_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_2:
                PoliticalListResult.PoliticalItem tag = (PoliticalListResult.PoliticalItem) view.getTag();
                callback.callback1(tag);
                break;
            case R.id.text_3:
                PoliticalListResult.PoliticalItem tag1 = (PoliticalListResult.PoliticalItem) view.getTag();
                callback.callback2(tag1);
                break;
        }
    }
}
