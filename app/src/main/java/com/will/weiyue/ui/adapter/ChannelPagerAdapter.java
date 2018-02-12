package com.will.weiyue.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.will.weiyue.bean.Channel;
import com.will.weiyue.ui.news.DetailFragment;

import java.util.List;

/**
 * author: liweixing
 * date: 2018/2/11
 */

public class ChannelPagerAdapter extends FragmentStatePagerAdapter {

    private List<Channel> mChannels;

    public ChannelPagerAdapter(FragmentManager fm, List<Channel> channels) {
        super(fm);
        mChannels = channels;
    }

    public void updateChannel(List<Channel> channels) {
        mChannels = channels;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return DetailFragment.new;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
