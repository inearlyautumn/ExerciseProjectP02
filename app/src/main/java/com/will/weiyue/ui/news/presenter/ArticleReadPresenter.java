package com.will.weiyue.ui.news.presenter;

import com.will.weiyue.bean.NewsArticleBean;
import com.will.weiyue.net.NewsApi;
import com.will.weiyue.net.RxSchedulers;
import com.will.weiyue.ui.base.BasePresenter;
import com.will.weiyue.ui.news.contract.ArticleContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author: liweixing
 * date: 2018/2/12
 */

public class ArticleReadPresenter extends BasePresenter<ArticleContract.View> implements ArticleContract.Presenter {

    NewsApi mNewsApi;

    @Inject
    public ArticleReadPresenter(NewsApi mNewsApi) {
        this.mNewsApi = mNewsApi;
    }

    @Override
    public void getData(String aid) {
        mNewsApi.getnewsArticle(aid)
                .compose(RxSchedulers.<NewsArticleBean>applySchedulers())
                .compose(mView.<NewsArticleBean>bindToLife())
                .subscribe(new Observer<NewsArticleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsArticleBean newsArticleBean) {
                        mView.loadData(newsArticleBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showFaild();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
