package com.will.weiyue.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.flyco.tablayout.SlidingTabLayout;
import com.will.weiyue.R;
import com.will.weiyue.bean.Channel;
import com.will.weiyue.component.ApplicationComponent;
import com.will.weiyue.component.HttpComponent;
import com.will.weiyue.ui.base.BaseFragment;
import com.will.weiyue.ui.news.contract.NewsContract;
import com.will.weiyue.ui.news.presenter.NewsPresenter;
import com.will.weiyue.widget.CustomViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: liweixing
 * date: 2018/2/8
 */

public class NewsFragment extends BaseFragment<NewsPresenter> implements NewsContract.View {

    private static final String TAG = NewsFragment.class.getSimpleName();

    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.iv_edit)
    ImageButton ivEdit;
    @BindView(R.id.viewpager)
    CustomViewPager viewpager;
    Unbinder unbinder;

    public static NewsFragment newInstance() {

        Bundle args = new Bundle();

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_news_new;
    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {

    }

    @Override
    public void loadData(List<Channel> channels, List<Channel> otherChannels) {

    }

    @Override
    public void bindView(View view, Bundle saveInstanceState) {

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
}
