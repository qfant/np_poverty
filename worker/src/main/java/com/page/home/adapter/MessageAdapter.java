package com.page.home.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.adapter.utils.QSimpleAdapter;
import com.haolb.client.R;
import com.page.home.CamerasResult;
import com.page.home.NoticesResult;

/**
 * Created by chenxi.cui on 2017/9/12.
 */

public class MessageAdapter extends QSimpleAdapter<NoticesResult.NoticeBean> {

    public MessageAdapter(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, ViewGroup parent) {
        return inflate(R.layout.pub_message_item, null, false);
    }

    @Override
    protected void bindView(View view, Context context, NoticesResult.NoticeBean item, int position) {
        TextView tvTitle = (TextView) view.findViewById(R.id.text_name);
        TextView tvInfo = (TextView) view.findViewById(R.id.text_detail);
        tvTitle.setText(item.title);
        tvInfo.setText(Html.fromHtml(item.intro));
    }

}
