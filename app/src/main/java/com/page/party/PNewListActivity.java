package com.page.party;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.page.community.serve.model.ServeParam;
import com.page.community.serve.model.ServeResult;
import com.page.party.model.NewsResult;
import com.page.party.model.NewsResult.NewsData.NewsItem;
import com.qfant.wuye.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/4/2.
 */

public class PNewListActivity extends BaseActivity implements OnItemClickListener<NewsItem>, SwipRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout refreshLayout;
    private MultiAdapter adapter;

    public static void startActivity(BaseActivity activity, String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        Intent intent = new Intent(activity, PNewListActivity.class);
        intent.putExtras(bundle);
        activity.qStartActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_new_list_layout);
        ButterKnife.bind(this);
        String title = myBundle.getString("title");
        String url = myBundle.getString("url");
        setTitleBar(title, true);
        setListView();
//            startRequest(1);
    }

    private void setListView() {
        ArrayList<NewsItem> list = new ArrayList<>();
        adapter = new MultiAdapter<NewsItem>(getContext(), list).addTypeView(new ITypeView<NewsItem>() {
            @Override
            public boolean isForViewType(NewsItem item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.a_activity_new_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        DefaultItemAnimator animator = new DefaultItemAnimator();
        rvList.setItemAnimator(animator);
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        List<NewsItem> mock = NewsResult.NewsData.mock();
        adapter.setData(mock);
    }

    private void startRequest(int pager) {
//            if (serviceMap == null) return;
        ServeParam param = new ServeParam();
        param.pageNo = pager;
        Request.startRequest(param, pager, ServiceMap.checkVersion, mHandler);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.alipayPayProduct) {
            ServeResult serveResult = (ServeResult) param.result;
            if (serveResult != null && serveResult.data != null && !ArrayUtils.isEmpty(serveResult.data.waterList)) {
                if ((int) param.ext == 1) {
                    adapter.setData(serveResult.data.waterList);
                } else {
                    adapter.addData(serveResult.data.waterList);
                }
            } else {
                if ((int) param.ext == 1) {
                    showToast("没有数据");
                } else {
                    showToast("没有更多了");
                }
            }
            refreshLayout.setRefreshing(false);
        }
        return false;
    }

    @Override
    public void onNetEnd(NetworkParam param) {
        super.onNetEnd(param);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClickListener(View view, NewsItem data, int position) {
        PNewsInfoActivity.startActivity(this, data.title, data.content);
    }

    @Override
    public void onRefresh(int index) {
        startRequest(1);
    }

    @Override
    public void onLoad(int index) {
        startRequest(++index);
    }

}
