package com.page.partymanger;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.page.uc.UCUtils;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/5/17.
 */

public class MeetingSubmitActivity extends BaseActivity {
    @BindView(R.id.input_submit)
    EditText inputSubmit;
    private int meetingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_submit_layout);
        ButterKnife.bind(this);
        meetingId = myBundle.getInt("meetingId");
        setTitleBar("发言", true, "提交", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSubmit();
            }
        });
    }

    private void requestSubmit() {
        MeetingSubmitParam param = new MeetingSubmitParam();
        String content = inputSubmit.getText().toString();
        if (TextUtils.isEmpty(content)) {
            showToast("请输入发言内容");
            return;
        }
        param.id = UCUtils.getInstance().getUserid();
        param.meetingid = meetingId;
        param.content = content;
        Request.startRequest(param, ServiceMap.submitStatement, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.submitStatement) {
            if (param.result.bstatus.code == 0) {
                finish();
            }
            showToast(param.result.bstatus.des);
        }
        return super.onMsgSearchComplete(param);
    }

}
