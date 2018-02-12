package com.will.weiyue.ui.news;

import android.os.Bundle;
import android.view.View;

import com.will.weiyue.R;
import com.will.weiyue.bean.NewsArticleBean;
import com.will.weiyue.component.ApplicationComponent;
import com.will.weiyue.ui.base.BaseActivity;
import com.will.weiyue.ui.news.contract.ArticleContract;
import com.will.weiyue.ui.news.presenter.ArticleReadPresenter;
import com.will.weiyue.widget.SwipeBackLayout;

/**
 * author: liweixing
 * date: 2018/2/12
 */

class ImageBrowseActivity extends BaseActivity<ArticleReadPresenter> implements ArticleContract.View{
    public static final String AID = "aid";
    public static final String ISCMPP = "isCmpp";
    private boolean isShow = true;
    private NewsArticleBean newsArticleBean;

    @Override
    public int getContentLayout() {
        SwipeBackLayout
        return R.layout.activity_imagebrowse;
    }

    @Override
    public void loadData(NewsArticleBean articleBean) {

    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {

    }

    @Override
    public void bindView(View view, Bundle saveInstanceState) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onRetry() {

    }
}
