package com.page.partymanger;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2018/5/17.
 */

public class MeetingDetailActivity extends BaseActivity {
    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.text_2)
    TextView text2;
    @BindView(R.id.text_3)
    TextView text3;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.text_send)
    TextView textSend;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_detail_layout);
        ButterKnife.bind(this);
        id = myBundle.getInt("id");
        setTitleBar("会议详情", true);
        requestData();
    }

    private void requestData() {
        MeetingDetailParam meetingDetailParam = new MeetingDetailParam();
        meetingDetailParam.id = id;
        Request.startRequest(meetingDetailParam, ServiceMap.meetingDetail, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.meetingList) {
            if (param.result.bstatus.code == 0) {

            }
        }
        return super.onMsgSearchComplete(param);
    }

    @OnClick({R.id.ll_content, R.id.text_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_content:
                Bundle bundle1 = new Bundle();
                bundle1.putInt("meetingId", id);
                qStartActivity(MeetingInfoActivity.class, bundle1);
                break;
            case R.id.text_send:
                Bundle bundle = new Bundle();
                bundle.putInt("meetingId", id);
                qStartActivity(MeetingSubmitActivity.class, bundle);
                break;
        }
    }
}
