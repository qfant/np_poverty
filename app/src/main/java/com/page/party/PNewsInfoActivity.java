package com.page.party;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

import com.baidu.platform.comapi.map.B;
import com.framework.activity.BaseActivity;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.page.party.model.NewsDetailResult;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/4/2.
 */

public class PNewsInfoActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView tvContent;

    public static void startActivity(BaseActivity activity, String title, String url, String id) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        bundle.putString("id", id);
        Intent intent = new Intent(activity, PNewsInfoActivity.class);
        intent.putExtras(bundle);
        activity.qStartActivity(intent);
    }

    public static void startActivity(BaseActivity activity, String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        Intent intent = new Intent(activity, PNewsInfoActivity.class);
        intent.putExtras(bundle);
        activity.qStartActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_new_info_layout);
        ButterKnife.bind(this);
        String title = myBundle.getString("title");
        String url = myBundle.getString("url");
        String id = myBundle.getString("id");
        setTitleBar(title, true);
//        NewsParam param = new NewsParam();
//        param.id = id;
//        Request.startRequest(param, ServiceMap.newsDetail, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
//        if (url != null) {
//            tvContent.setText(Html.fromHtml(url));
//        }
        tvContent.loadUrl(url);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
//        if (param.key == ServiceMap.newsDetail) {
//            NewsDetailResult result = (NewsDetailResult) param.result;
//            tvContent.setText(Html.fromHtml(result.data.newsdetialResult.intro));
//        }
        return super.onMsgSearchComplete(param);
    }

    public static class NewsParam extends BaseParam {
        public String id;
    }
}
