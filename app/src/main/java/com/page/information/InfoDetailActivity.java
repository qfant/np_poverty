package com.page.information;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.view.LineDecoration;
import com.page.information.InfoDetailResult.InfoDetailItem;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/5/18.
 */

public class InfoDetailActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private MultiAdapter<InfoDetailItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail_layout);
        ButterKnife.bind(this);
        int id = myBundle.getInt("id");
        setTitleBar("信息详情", true);
        InfoDetailParam param = new InfoDetailParam();
        param.id = id;
        Request.startRequest(param, ServiceMap.InfoDetail, mHandler, Request.RequestFeature.CANCELABLE, Request.RequestFeature.BLOCK);
        setListView();
    }

    private void setListView() {
        adapter = new MultiAdapter<InfoDetailItem>(getContext()).addTypeView(new ITypeView<InfoDetailItem>() {
            @Override
            public boolean isForViewType(InfoDetailItem item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new InfoDetailViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.activity_info_detail_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);
    }


    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.InfoDetail) {
            InfoDetailResult resul = (InfoDetailResult) param.result;
            adapter.setData(resul.data.infoDetialResult);
        }
        return super.onMsgSearchComplete(param);
    }

}
