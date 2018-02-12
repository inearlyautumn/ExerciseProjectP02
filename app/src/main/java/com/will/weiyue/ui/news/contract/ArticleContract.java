package com.will.weiyue.ui.news.contract;

import com.will.weiyue.bean.NewsArticleBean;
import com.will.weiyue.ui.base.BaseContract;

/**
 * author: liweixing
 * date: 2018/2/12
 */

public interface ArticleContract {
    interface View extends BaseContract.BaseView {
        void loadData(NewsArticleBean articleBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getData(String aid);
    }
}
