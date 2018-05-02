package com.page.party;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.framework.activity.BaseFragment;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.click.OnItemClickListener;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.ArrayUtils;
import com.framework.utils.imageload.ImageLoad;
import com.framework.view.LineDecoration;
import com.framework.view.circlerefresh.CircleRefreshLayout;
import com.framework.view.sivin.Banner;
import com.framework.view.sivin.BannerAdapter;
import com.page.home.activity.TextViewActivity;
import com.page.home.activity.WebActivity;
import com.page.home.model.HomeModel;
import com.page.home.model.LinksParam;
import com.page.home.model.LinksResult;
import com.page.home.model.LinksResult.Data.Links;
import com.page.home.model.NoticeResult;
import com.page.home.model.NoticeResult.Data.Datas;
import com.page.home.view.MRecyclerView;
import com.page.home.view.ModeView;
import com.page.party.model.NewsResult;
import com.page.party.model.NewsResult.NewsData.NewsItem;
import com.page.store.home.model.FoodRecResult;
import com.qfant.wuye.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.page.community.serve.activity.ServeActivity.TITLE;

/**
 * Created by chenxi.cui on 2017/8/13.
 * 首页
 */

public class PManageFragment extends BaseFragment {


    @BindView(R.id.circlerefreshlayout)
    CircleRefreshLayout circlerefreshlayout;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.gl_mode)
    GridLayout glMode;
    @BindView(R.id.rv_711_list)
    MRecyclerView rv711List;
    private BannerAdapter bannerAdapter;
    private Unbinder unbinder;
    private MultiAdapter adapter711;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreateViewWithTitleBar(inflater, container, R.layout.a_fragment_manage_layout);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitleBar("党员管理", false);
        setRefresh();
//        setBanner();
        setModel();
        set711();
    }

    private void setRefresh() {
        circlerefreshlayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                circlerefreshlayout.finishRefreshing();
                            }
                        });
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getNotices();
        getLinks();
    }


    private void set711() {
        adapter711 = new MultiAdapter<NewsItem>(getContext()).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.a_activity_new_item_layout, parent, false));
            }
        });
        rv711List.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv711List.setAdapter(adapter711);
        rv711List.addItemDecoration(new LineDecoration(getContext()));
        adapter711.setOnItemClickListener(new OnItemClickListener<NewsItem>() {
            @Override
            public void onItemClickListener(View view, NewsItem data, int position) {
                PNewsInfoActivity.startActivity(getContext(), data.title, data.content);
            }
        });
        adapter711.setData(NewsResult.NewsData.mock());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void setModel() {
        ArrayList<HomeModel> list = new ArrayList<>();
        list.add(new HomeModel("党员学习", R.drawable.icon_party_announcement));
        list.add(new HomeModel("党费缴纳", R.drawable.icon_study_count));
        list.add(new HomeModel("关系转移", R.drawable.icon_dynamic_phase));
        list.add(new HomeModel("关怀申请", R.drawable.icon_party_activity));
        list.add(new HomeModel("两学一做", R.drawable.icon_learn));
        list.add(new HomeModel("三会一课", R.drawable.icon_meeting));
        list.add(new HomeModel("民主议事", R.drawable.icon_democracy));
        list.add(new HomeModel("主题党日", R.drawable.icon_thematic));

        for (final HomeModel homeModel : list) {
            ModeView itemView = new ModeView(getContext());
            itemView.setData(homeModel);
            itemView.setTag(homeModel.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
//                    switch ((String) v.getTag()) {
                    PNewListActivity.startActivity(getContext(), homeModel.title, "");
//                    }
                }
            });
            glMode.addView(itemView);
        }
    }

    private void setBanner() {
        ArrayList<Links> arrayList = new ArrayList<>();
        bannerAdapter = new BannerAdapter<Links>(arrayList) {
            @Override
            protected void bindTips(TextView tv, Links bannerModel) {
//                tv.setText(bannerModel.getTips());
            }

            @Override
            public void bindImage(ImageView imageView, Links bannerModel) {
                ImageLoad.loadPlaceholder(getContext(), bannerModel.imgurl, imageView, R.drawable.banner, R.drawable.banner);
            }

        };
        banner.setBannerAdapter(bannerAdapter);
        banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                try {
                    Links links = (Links) bannerAdapter.getmDataList().get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString(TITLE, links.title);
                    bundle.putString(WebActivity.URL, links.link);
                    qStartActivity(WebActivity.class, bundle);
                } catch (Exception e) {

                }
            }
        });
    }

    public void getNotices() {
        Request.startRequest(new BaseParam(), ServiceMap.getNoticeList, mHandler);
    }


    private void getLinks() {
        LinksParam param = new LinksParam();
        param.type = 1;
        Request.startRequest(param, ServiceMap.getLinks, mHandler);
    }


    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getLinks) {
            LinksResult linksResult = (LinksResult) param.result;
            if (linksResult != null && linksResult.data != null && linksResult.data.links != null) {
                updataBanner(linksResult.data.links);
            }

        } else if (param.key == ServiceMap.getRecommendCategorys) {
//            FoodRecResult result = (FoodRecResult) param.result;
//            updataList(result);
        }
        return false;
    }


    private void updataBanner(List<Links> links) {
        bannerAdapter.setImages(links);
        banner.notifyDataHasChanged();
    }

    private void updataList(FoodRecResult result) {
        if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.products)) {
            rv711List.setVisibility(View.VISIBLE);
            adapter711.setData(result.data.products);
        } else {
            rv711List.setVisibility(View.GONE);
        }
    }

}
