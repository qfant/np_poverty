package com.page.political;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.ArrayUtils;
import com.framework.view.LineDecoration;
import com.framework.view.pull.SwipRefreshLayout;
import com.page.community.eventlist.model.EventListResult;
import com.page.political.PoliticalListResult.PoliticalItem;
import com.qfant.wuye.R;

/**
 * Created by chenxi.cui on 2018/5/16.
 */

public class PoliticalListActivity extends BaseActivity implements OnItemClickListener<PoliticalItem>, SwipRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout srlDownRefresh;
    @BindView(R.id.text_0)
    TextView text0;
    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.text_2)
    TextView text2;
    @BindView(R.id.text_4)
    TextView text4;
    private MultiAdapter<PoliticalItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_list_layout);
        ButterKnife.bind(this);
        setTitleBar("指导员管理", true);
        setListView();
        startRequest(1);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setListView() {
//        final PoliticalListViewHolder holder = new PoliticalListViewHolder(this, LayoutInflater.from(this).inflate(R.layout.activity_po_list_item_layout, null, false));
        adapter = new MultiAdapter<PoliticalItem>(getContext()).addTypeView(new ITypeView<PoliticalItem>() {
            @Override
            public boolean isForViewType(PoliticalItem item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new PoliticalListViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.activity_po_list_item_layout, parent, false),new PoliticalListViewHolder.PoCallback() {
                    @Override
                    public void callback1(PoliticalItem data) {
                        if (data.signin == 0) {
                            showToast("已经签到");
                            return;
                        }
                        SignParam signParam = new SignParam(data.latitude, data.longitude);
                        signParam.id = data.id;
                        Request.startRequest(signParam, ServiceMap.signin, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
                    }

                    @Override
                    public void callback2(PoliticalItem data) {
                        if (data.signin == 1) {
                            showToast("已经签退");
                            return;
                        }
                        SignParam signParam1 = new SignParam(data.latitude, data.longitude);
                        signParam1.id = data.id;
                        Request.startRequest(signParam1, ServiceMap.signout, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);

                    }
                });
            }
        });
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);
//        adapter.setOnItemClickListener(this);
        srlDownRefresh.setOnRefreshListener(this);
//        holder.setPoCallback(new PoliticalListViewHolder.PoCallback() {
//            @Override
//            public void callback1(PoliticalItem data) {
//                if (data.signin == 0) {
//                    showToast("已经签到");
//                    return;
//                }
//                SignParam signParam = new SignParam(data.latitude, data.longitude);
//                signParam.id = data.id;
//                Request.startRequest(signParam, ServiceMap.signin, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
//            }
//
//            @Override
//            public void callback2(PoliticalItem data) {
//                if (data.signin == 1) {
//                    showToast("已经签退");
//                    return;
//                }
//                SignParam signParam1 = new SignParam(data.latitude, data.longitude);
//                signParam1.id = data.id;
//                Request.startRequest(signParam1, ServiceMap.signout, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
//
//            }
//        });
    }


    private void startRequest(int page) {
        PoliticalListParam param = new PoliticalListParam();
        if (page == 1) {
            Request.startRequest(param, page, ServiceMap.queryByZhidao, mHandler, Request.RequestFeature.BLOCK, Request.RequestFeature.CANCELABLE);
        } else {
            Request.startRequest(param, page, ServiceMap.queryByZhidao, mHandler);
        }
    }


    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.queryByZhidao) {
            final PoliticalListResult result = (PoliticalListResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.partyBranchList)) {
                setData(result.data);
                if ((int) param.ext == 1) {
                    adapter.clearData();
                    adapter.setData(result.data.partyBranchList);
                } else {
                    adapter.addData(result.data.partyBranchList);
                }
            } else {
                showToast("没有更多了");
            }
            srlDownRefresh.setRefreshing(false);
        } else if (ServiceMap.signin == param.key || ServiceMap.signout == param.key) {
            if (param.result.bstatus.code == 0) {
                showToast("成功");
                startRequest(1);
            } else {
                showToast(param.result.bstatus.des);
            }
        }
        return false;
    }

    private void setData(PoliticalListResult.PoliticalData data) {
        text0.setText("签到数:" + data.signin);
        text1.setText("签退数:" + data.signout);
        text2.setText("工作日志数:" + data.worklog);
    }

    @Override
    public void onItemClickListener(View view,PoliticalItem data, int position) {
//        Bundle bundle = new Bundle();
//        bundle.putString(EventDetailActivity.ID, data.id);
//        qStartActivity(EventDetailActivity.class, bundle);
    }

    @Override
    public void onRefresh(int index) {
        startRequest(1);
    }

    @Override
    public void onLoad(int index) {
        startRequest(++index);
    }

    @OnClick(R.id.text_4)
    public void onViewClicked() {
        qStartActivity(WorkLogListActivity.class);
    }
}
