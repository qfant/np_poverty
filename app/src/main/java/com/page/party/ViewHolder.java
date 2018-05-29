package com.page.party;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.imageload.ImageLoad;
import com.page.party.model.NewsResult.NewsData.NewsItem;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/23.
 */

public class ViewHolder extends BaseViewHolder<NewsItem> {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_read)
    TextView tvRead;
    private NewsItem data;

    public ViewHolder(Context context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, NewsItem data, int position) {
        this.data = data;
        if (data != null) {
            title.setText(data.title);
            tvTime.setText(data.createtime);
            if (data.readCount == 0) {
                tvRead.setText("");
            }else {
                tvRead.setText("已阅：" + data.readCount);
            }
            ivImage.setVisibility(View.GONE);
//            ImageLoad.loadPlaceholder(mContext, data.pic, ivImage,R.drawable.moren,R.drawable.moren);
        }
    }

    public void onViewClicked() {
//        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.phone));
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        mContext.startActivity(intent);
    }
}
