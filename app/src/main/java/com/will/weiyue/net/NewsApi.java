package com.will.weiyue.net;

import android.support.annotation.StringDef;

import com.will.weiyue.bean.NewsArticleBean;
import com.will.weiyue.bean.NewsDetail;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import io.reactivex.Observable;

/**
 * author: liweixing
 * date: 2018/2/8
 */

public class NewsApi {

    public static final String ACTION_DEFAULT = "default";
    public static final String ACTION_DOWN = "down";
    public static final String ACTION_UP = "up";

    public Observable<List<NewsDetail>> getNewsDetail(String id, String action, int pullNum) {
//        return mService.getNewsDetail
        return null;
    }

    /**
     * 获取新闻文章详情
     * @param aid   文章aid 此处baseurl可能不同，需要特殊处理
     *              1：aid 以 cmpp 开头则调用 getNewsArticleWithCmpp
     * @return
     */
    public Observable<NewsArticleBean> getnewsArticle(String aid) {
        if (aid.startsWith("sub")) {
            return mService.getNewsArticleWidthSub(aid);
        } else {
            return mService.getNewsArticleWidthCmpp(ApiConstants.sGetNewsArticleCmppApi + ApiConstants.sGetNewsArticleDocCmppApi, aid);
        }
    }

    @StringDef({ACTION_DEFAULT, ACTION_DOWN, ACTION_UP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Actions {
    }

    public static NewsApi sInstance;

    private NewsApiService mService;

    public NewsApi(NewsApiService newsApiService) {
        this.mService = newsApiService;
    }

    public static NewsApi getInstance(NewsApiService newsApiService) {
        if (sInstance == null) {
            sInstance = new NewsApi(newsApiService);
        }
        return sInstance;
    }




}
