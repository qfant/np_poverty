package com.page.information;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.adapter.utils.QSimpleAdapter;
import com.framework.utils.cache.ImageLoader;
import com.page.information.InfoPlatformResult.InfoPlatformItem;
import com.qfant.wuye.R;

/**
 * Created by chenxi.cui on 2018/5/7.
 */

public class InfoPlatformAdapter extends QSimpleAdapter<InfoPlatformItem> {
    public InfoPlatformAdapter(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, ViewGroup parent) {
        return inflate(R.layout.a_activity_info_platform_list_item, null, false);
    }

    @Override
    protected void bindView(View view, Context context, InfoPlatformItem item, int position) {
        ImageView imageView = (ImageView) view.findViewById(R.id.image_head);
        TextView textName = (TextView) view.findViewById(R.id.text_name);
        TextView textPhone = (TextView) view.findViewById(R.id.text_phone);
        ImageLoader.getInstance(getContext()).loadImage(item.portrait, imageView, R.drawable.moren);
        textName.setText(item.name);
        textPhone.setText(item.phone);
    }
}
