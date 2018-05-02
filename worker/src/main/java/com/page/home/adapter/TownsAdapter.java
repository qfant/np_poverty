package com.page.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framework.adapter.utils.QSimpleAdapter;
import com.haolb.client.R;
import com.page.home.CamerasResult;
import com.page.home.TownsResult;

/**
 * Created by chenxi.cui on 2017/9/12.
 */

public class TownsAdapter extends QSimpleAdapter<TownsResult.TownBean> {

    public TownsAdapter(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, ViewGroup parent) {
        return inflate(R.layout.pub_town_item, null, false);
    }

    @Override
    protected void bindView(View view, Context context, TownsResult.TownBean item, int position) {
        TextView textView =  view.findViewById(R.id.text_title);
        textView.setText(item.name);
    }

}
