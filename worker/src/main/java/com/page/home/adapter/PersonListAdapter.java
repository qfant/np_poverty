package com.page.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.framework.adapter.utils.QSimpleAdapter;
import com.framework.utils.cache.ImageLoader;
import com.haolb.client.R;
import com.page.home.PersonsResult;

import static com.page.home.TownsResult.*;

/**
 * Created by chenxi.cui on 2017/9/12.
 */

public class PersonListAdapter extends QSimpleAdapter<PersonsResult.PersonBean> {

    public PersonListAdapter(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, ViewGroup parent) {
        return inflate(R.layout.pub_person_item, null, false);
    }

    @Override
    protected void bindView(View view, Context context, PersonsResult.PersonBean item, int position) {
        ImageView imageView = view.findViewById(R.id.image_head);
        TextView tvName = view.findViewById(R.id.text_name);
        TextView tvPhone = view.findViewById(R.id.text_phone);
        ImageLoader.getInstance(context).loadImage(item.headimg, imageView, R.drawable.moren);
        tvName.setText(item.name);
        tvPhone.setText(item.phone);
    }

}
