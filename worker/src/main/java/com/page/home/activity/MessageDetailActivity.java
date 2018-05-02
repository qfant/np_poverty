package com.page.home.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.haolb.client.R;
import com.page.home.NoticesResult;

/**
 * Created by chenxi.cui on 2017/12/12.
 */

public class MessageDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detaill);
        NoticesResult.NoticeBean noticeBean = (NoticesResult.NoticeBean) myBundle.getSerializable("item");
        if (noticeBean == null) {
            finish();
            return;
        }
        setTitleBar(noticeBean.title, true);
        TextView textView = findViewById(R.id.tv_intro);
        textView.setText(Html.fromHtml(noticeBean.intro));
    }
}
