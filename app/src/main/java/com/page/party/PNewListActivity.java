package com.page.party;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2018/4/2.
 */

public class PNewListActivity extends BaseActivity implements OnItemClickListener<NewsItem>, SwipRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout refreshLayout;
    @BindView(R.id.input_search)
    EditText inputSearch;
    @BindView(R.id.text_search)
    TextView textSearch;
    private MultiAdapter adapter;
    private int ftype;

    public static void startActivity(BaseActivity activity, String title, int type) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("type", type);
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
        ftype = myBundle.getInt("type", 1);
        setTitleBar(title, true);
        setListView();
        startRequest(1, "");
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
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    private void startRequest(int pager, String keyword) {
//            if (serviceMap == null) return;
        ServeParam param = new ServeParam();
        param.pageNo = pager;
        param.type = ftype;
        param.keyword = keyword;
//        if (ftype == 3) {
//            Request.startRequest(param, pager, ServiceMap.worknewsList, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
//        }else {
            Request.startRequest(param, pager, ServiceMap.newsList, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
//        }
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.newsList||param.key == ServiceMap.worknewsList) {
            NewsResult serveResult = (NewsResult) param.result;
            if (serveResult != null && serveResult.data != null && !ArrayUtils.isEmpty(serveResult.data.newsList)) {
                if ((int) param.ext == 1) {
                    adapter.setData(serveResult.data.newsList);
                } else {
                    adapter.addData(serveResult.data.newsList);
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
        PNewsInfoActivity.startActivity(this, data.title, data.intro, data.id);
    }

    @Override
    public void onRefresh(int index) {
        startRequest(1, "");
    }

    @Override
    public void onLoad(int index) {
        startRequest(++index, "");
    }

    @OnClick(R.id.text_search)
    public void onViewClicked() {
        String s = inputSearch.getText().toString();
        if (TextUtils.isEmpty(s)) {
            return;
        }
        startRequest(1, s);
    }
}
