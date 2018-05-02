package com.page.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.utils.cache.ImageLoader;
import com.haolb.client.R;
import com.page.home.PersonsResult;

/**
 * Created by chenxi.cui on 2017/12/12.
 */

public class PersonDetailActivity extends BaseActivity {
    private LinearLayout llContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        PersonsResult.PersonBean personBean = (PersonsResult.PersonBean) myBundle.getSerializable("item");
        if (personBean == null) {
            finish();
            return;
        }
        setTitleBar(personBean.name, true);
        ImageView imageView = findViewById(R.id.image_head);
        ImageLoader.getInstance(this).loadImage(personBean.headimg, imageView, R.drawable.moren);
        llContent = findViewById(R.id.ll_content);
        genItem("姓名", personBean.name);
        genItem("年龄", personBean.age);
        genItem("电话", personBean.phone);
        genItem("家庭住址", personBean.address);
        genItem("身份证", personBean.iden);
        genItem("开户行", personBean.bank);
        genItem("银行卡号", personBean.banknum);
    }

    private View genItem(String key, String value) {
        View inflate = LinearLayout.inflate(this, R.layout.person_detail_item, null);
        TextView textKey = inflate.findViewById(R.id.tv_key);
        TextView textValue = inflate.findViewById(R.id.tv_value);
        textKey.setText(key);
        textValue.setText(value);
        llContent.addView(inflate);
        return inflate;
    }
}
