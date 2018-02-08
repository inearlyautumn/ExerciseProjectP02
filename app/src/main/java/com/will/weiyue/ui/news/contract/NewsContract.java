package com.will.weiyue.ui.news.contract;

import com.will.weiyue.bean.Channel;
import com.will.weiyue.ui.base.BaseContract;

import java.util.List;

/**
 * author: liweixing
 * date: 2018/2/8
 */

public interface NewsContract {
    interface View extends BaseContract.BaseView {
        void loadData(List<Channel> channels, List<Channel> otherChannels);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        /**
         * 初始化频道
         * */
        void getChannel();
    }
}
