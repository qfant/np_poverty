package com.page.uc;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.page.uc.bean.UserHelpResult;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/5/7.
 * 使用帮助
 */

public class DocsHelpActivity extends BaseActivity {
    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_docs_help_layout);
        ButterKnife.bind(this);
        setTitleBar("使用帮助", true);
        startRequest();
    }

    private void startRequest() {
        Request.startRequest(new BaseParam(), ServiceMap.useHelp, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
    }


    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.useHelp) {
            UserHelpResult result = (UserHelpResult) param.result;
            if (result != null && result.data != null) {
                text.setText(Html.fromHtml(result.data.useHelpResult));
            } else {
                showToast("没有更多了");
            }
        }
        return false;
    }
}
