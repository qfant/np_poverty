package com.page.partymanger;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.page.partymanger.MeetingDetailResult.MeetingMember;
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
    private MultiAdapter<MeetingMember> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_detail_layout);
        ButterKnife.bind(this);
        id = myBundle.getInt("id");
        setTitleBar("会议详情", true);
        requestData();
        setListView();
    }

    private void requestData() {
        MeetingDetailParam meetingDetailParam = new MeetingDetailParam();
        meetingDetailParam.id = id;
        Request.startRequest(meetingDetailParam, ServiceMap.meetingDetail, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
    }

    public void setData(MeetingDetailResult.MeetingData data) {
        if (data == null || data.meetingdetial == null) {
            return;
        }
        text1.setText("会议: " + data.meetingdetial.name);
        text2.setText("时间: " + data.meetingdetial.startdate + "~" + data.meetingdetial.enddate);
        text3.setText("地点: " + data.meetingdetial.address);
        textContent.setText(data.statementcount + "条");
        adapter.setData(data.memberList);
//        adapter.setData(list);
    }

    private void setListView() {
        adapter = new MultiAdapter<MeetingMember>(getContext()).addTypeView(new ITypeView<MeetingMember>() {
            @Override
            public boolean isForViewType(MeetingMember item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new MeetingDetailHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.activity_meeting_detail_item_layout, parent, false));
            }
        });
//        rvList.addItemDecoration(new GridDecoration(this));
        rvList.setLayoutManager(new GridLayoutManager(this, 5));
        rvList.setAdapter(adapter);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.meetingDetail) {
            if (param.result.bstatus.code == 0) {
                MeetingDetailResult result = (MeetingDetailResult) param.result;
                setData(result.data);
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
