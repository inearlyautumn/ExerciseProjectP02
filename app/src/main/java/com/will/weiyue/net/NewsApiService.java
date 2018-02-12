package com.will.weiyue.net;

import com.will.weiyue.bean.NewsArticleBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * author: liweixing
 * date: 2018/2/11
 */

public interface NewsApiService {

    @GET("api_vampire_article_detail")
    Observable<NewsArticleBean> getNewsArticleWidthSub(@Query("aid") String aid);

    @GET
    Observable<NewsArticleBean> getNewsArticleWidthCmpp(@Url String url,
                                                        @Query("aid") String aid);
}
