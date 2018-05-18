package com.page.integral;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.ArrayUtils;
import com.framework.view.LineDecoration;
import com.framework.view.pull.SwipRefreshLayout;
import com.page.integral.IntegralResult.IntegralItem;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2018/5/4.
 */

public class IntegralActivity extends BaseActivity implements SwipRefreshLayout.OnRefreshListener {
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.spinner2)
    Spinner spinner2;
    @BindView(R.id.spinner3)
    Spinner spinner3;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SwipRefreshLayout srlDownRefresh;
    private MultiAdapter<IntegralItem> adapter;
    private IntegralResult.IntegralData data = null;
    private int mYear;
    private int mType;
    private int mArea;
    private int mQuarter;
    private boolean isFirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_integral_layout);
        ButterKnife.bind(this);
        setTitleBar("积分管理", true);
//        initData();
        setListView();
        startRequest(1);
    }

    private void setListView() {
        adapter = new MultiAdapter<IntegralItem>(getContext()).addTypeView(new ITypeView<IntegralItem>() {
            @Override
            public boolean isForViewType(IntegralItem item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new IntegralHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.activity_meeting_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);
//        adapter.setOnItemClickListener(this);
        srlDownRefresh.setOnRefreshListener(this);
    }

    private void initData() {
        setSpinner();
        setSpinner1();
        setSpinner2();
        setSpinner3();
    }

    private void setSpinner3() {
        ArrayAdapter<CharSequence> adapter1 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        final String[] arr1 = data.getQuarter();
        adapter1.addAll(arr1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter1);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst) {
                    return;
                }
                mQuarter = data.quarter.get(position).id;
                startRequest(1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        spinner3.setSelection(0);
    }

    private void setSpinner2() {
        ArrayAdapter<CharSequence> adapter1 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        final String[] arr1 = data.getArea();
        adapter1.addAll(arr1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter1);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst) {
                    return;
                }
                mArea = data.area.get(position).id;
                startRequest(1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        spinner2.setSelection(0);
    }

    private void setSpinner1() {
        ArrayAdapter<CharSequence> adapter1 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        final String[] arr1 = data.getType();
        adapter1.addAll(arr1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst) {
                    return;
                }
                mType = data.type.get(position).id;
                startRequest(1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        spinner1.setSelection(0);
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        final String[] arr = data.getYear();
        adapter.addAll(arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirst) {
                    return;
                }
                mYear = data.year.get(position).id;
                startRequest(1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        spinner.setSelection(0);
    }

    private void startRequest(int page) {
        IntegralParam param = new IntegralParam();
        param.pageNo = page;
        param.area = mArea;
        param.quarter = mQuarter;
        param.type = mType;
        param.year = mYear;
        if (page == 1) {
            Request.startRequest(param, page, ServiceMap.integral, mHandler, Request.RequestFeature.BLOCK, Request.RequestFeature.CANCELABLE);
        } else {
            Request.startRequest(param, page, ServiceMap.integral, mHandler);
        }
    }


    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.integral) {
            IntegralResult result = (IntegralResult) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.integralList)) {
                if (data == null) {
                    data = result.data;
                    initData();
                    isFirst = false;
                }
                if ((int) param.ext == 1) {
                    adapter.setData(result.data.integralList);
                } else {
                    adapter.addData(result.data.integralList);
                }
            } else {
                showToast("没有更多了");
            }
            srlDownRefresh.setRefreshing(false);
        }
        return false;
    }

//    @Override
//    public void onItemClickListener(View view, MeetingListResult.MeetingItem data, int position) {
//        Bundle bundle = new Bundle();
//        bundle.putInt("id", data.id);
//        qStartActivity(MeetingDetailActivity.class, bundle);
//    }

    @Override
    public void onRefresh(int index) {
        startRequest(1);
    }

    @Override
    public void onLoad(int index) {
        startRequest(++index);
    }
}
