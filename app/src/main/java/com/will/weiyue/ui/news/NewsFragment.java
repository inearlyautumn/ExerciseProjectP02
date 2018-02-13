package com.will.weiyue.ui.news;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import com.flyco.tablayout.SlidingTabLayout;
import com.will.weiyue.R;
import com.will.weiyue.bean.Channel;
import com.will.weiyue.component.ApplicationComponent;
import com.will.weiyue.component.DaggerHttpComponent;
import com.will.weiyue.ui.adapter.ChannelPagerAdapter;
import com.will.weiyue.ui.base.BaseFragment;
import com.will.weiyue.ui.news.contract.NewsContract;
import com.will.weiyue.ui.news.presenter.NewsPresenter;
import com.will.weiyue.widget.CustomViewPager;
import org.simple.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
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

//    private ChannelPagerAdapter mChannelPagerAdapter;

    private List<Channel> mSelectedDatas;
    private List<Channel> mUnSelectedDatas;

    private int selectedIndex;
    private String selectedChannel;
    private ChannelPagerAdapter mChannelPagerAdapter;

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
        DaggerHttpComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
                .inject(this);
    }

    @Override
    public void bindView(View view, Bundle saveInstanceState) {
        EventBus.getDefault().register(this);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedIndex = position;
                selectedChannel = mSelectedDatas.get(position).getChannelName();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        mSelectedDatas = new ArrayList<>();
        mUnSelectedDatas = new ArrayList<>();
        mPresenter.getChannel();
    }

    @Override
    public void loadData(List<Channel> channels, List<Channel> otherChannels) {
        if (channels != null) {
            mSelectedDatas.clear();
            mSelectedDatas.addAll(channels);
            mUnSelectedDatas.clear();
            mUnSelectedDatas.addAll(otherChannels);
            mChannelPagerAdapter = new ChannelPagerAdapter(getChildFragmentManager(), channels);
            viewpager.setAdapter(mChannelPagerAdapter);

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
