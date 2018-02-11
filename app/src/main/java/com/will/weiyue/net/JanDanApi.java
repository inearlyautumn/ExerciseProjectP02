package com.will.weiyue.net;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Observable;

/**
 * author: liweixing
 * date: 2018/2/8
 */

public class JanDanApi {

    public static final String TYPE_FRESH = "get_recent_posts";
    public static final String TYPE_FRESHARTICLE = "get_post";
    public static final String TYPE_BORED = "jandan.get_pic_comments";
    public static final String TYPE_GIRLS = "jandan.get_ooxx_comments";
    public static final String TYPE_Duan = "jandan.get_duan_comments";

    @StringDef({TYPE_FRESH, TYPE_BORED, TYPE_GIRLS, TYPE_Duan})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {

    }

    public static JanDanApi sInstance;

    private JanDanApiService mService;

    public JanDanApi(JanDanApiService mService) {
        this.mService = mService;
    }

    public static JanDanApi getInstance(JanDanApiService janDanApiService) {
        if (sInstance == null) {
            sInstance = new JanDanApi(janDanApiService);
        }
        return sInstance;
    }


}

