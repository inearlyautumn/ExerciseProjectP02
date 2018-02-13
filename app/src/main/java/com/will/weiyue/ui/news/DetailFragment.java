package com.will.weiyue.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.will.weiyue.MyApp;
import com.will.weiyue.R;
import com.will.weiyue.bean.NewsDetail;
import com.will.weiyue.component.ApplicationComponent;
import com.will.weiyue.component.DaggerHttpComponent;
import com.will.weiyue.net.NewsApi;
import com.will.weiyue.ui.adapter.NewsDetailAdapter;
import com.will.weiyue.ui.base.BaseFragment;
import com.will.weiyue.ui.news.contract.DetailContract;
import com.will.weiyue.ui.news.presenter.DetailPresenter;
import com.will.weiyue.utils.ContextUtils;
import com.will.weiyue.widget.CustomLoadMoreView;
import com.will.weiyue.widget.NewsDelPop;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * author: liweixing
 * date: 2018/2/11
 */

public class DetailFragment extends BaseFragment<DetailPresenter> implements DetailContract.View {
    public static final String TAG = DetailFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mPtrFrameLayout)
    PtrClassicFrameLayout mPtrFrameLayout;
    @BindView(R.id.tv_toast)
    TextView tvToast;
    @BindView(R.id.rl_top_toast)
    RelativeLayout rlTopToast;

    Unbinder unbinder;
    private View view_Focus;//项部banner
    private boolean isRemoveHeaderView;
    private String newsid;
    private int downpullNum;
    private NewsDelPop newsDelPop;
    private List<NewsDetail.ItemBean> beanList;
    private List<NewsDetail.ItemBean> mBannerList;
    private NewsDetailAdapter detailAdapter;
    private int upPullNum = 1;

    public static DetailFragment newInstance(String newsid, int position) {
        Bundle args = new Bundle();
        args.putString("newsid", newsid);
        args.putInt("position", position);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {
        DaggerHttpComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this);
    }

    @Override
    public void bindView(View view, Bundle saveInstanceState) {
        mPtrFrameLayout.disableWhenHorizontalMove(true);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, recyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isRemoveHeaderView = true;
                mPresenter.getData(newsid, NewsApi.ACTION_DOWN, downpullNum);
            }
        });
        beanList = new ArrayList<>();
        mBannerList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        detailAdapter = new NewsDetailAdapter(beanList, getActivity());
        recyclerView.setAdapter(detailAdapter);
        detailAdapter.setEnableLoadMore(true);
        detailAdapter.setLoadMoreView(new CustomLoadMoreView());
        detailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        detailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getData(newsid, NewsApi.ACTION_UP, upPullNum);
            }
        }, recyclerView);

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewsDetail.ItemBean itemBean = (NewsDetail.ItemBean) baseQuickAdapter.getItem(position);
                toRead(itemBean);
            }
        });

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewsDetail.ItemBean itemBean = (NewsDetail.ItemBean) baseQuickAdapter.getItem(position);
                switch (view.getId()) {
                    case R.id.iv_close:
                        view.getHeight();
                        int[] location = new int[2];
                        view.getLocationInWindow(location);
                        if (itemBean.getStyle() == null) {
                            return;
                        }
                        if (ContextUtils.getScreenWidth(MyApp.getContext()) - 50 - location[1] < ContextUtils.dip2px(MyApp.getContext(), 80)) {
                            newsDelPop.anchorView(view)
                                    .gravity(Gravity.TOP)
                                    .setBackReason(itemBean.getStyle().getBackreason(), true, position)
                                    .show();
                        } else {
                            newsDelPop.anchorView(view)
                                    .gravity(Gravity.BOTTOM)
                                    .setBackReason(itemBean.getStyle().getBackreason(), false, position)
                                    .show();
                        }
                        break;
                }
            }
        });
        view_Focus = getView().inflate(getActivity(), R.layout.news_detail_headerview, null);
    }

    @Override
    public void loadBannerData(NewsDetail newsDetail) {

    }

    @Override
    public void loadTopNewsData(NewsDetail newsDetail) {

    }

    @Override
    public void loadData(List<NewsDetail.ItemBean> itemBeanList) {

    }

    @Override
    public void loadMoreData(List<NewsDetail.ItemBean> itemBeanList) {

    }

    @Override
    public void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void toRead(NewsDetail.ItemBean itemBean) {
        if (itemBean == null) {
            return;
        }
        switch (itemBean.getItemType()) {
            case NewsDetail.ItemBean.TYPE_DOC_TITLEIMG:
            case NewsDetail.ItemBean.TYPE_DOC_SLIDEIMG:
                /*Intent intent = new Intent(getActivity(), ArticleReadActivity.class);
                intent.putExtra("aid", itemBean.getDocumentId());
                startActivity(intent);*/
                break;
            case NewsDetail.ItemBean.TYPE_SLIDE:
                ImageBrowseActivity.launch(getActivity(), itemBean);
                break;

        }
    }
}
