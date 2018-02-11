package com.will.weiyue.net;

/**
 * author: liweixing
 * date: 2018/2/8
 */

public class NewsApi {

    private static final String ACTION_DEFAULT = "default";
    private static final String ACTION_DOWN = "down";
    private static final String action_up = "up";

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
