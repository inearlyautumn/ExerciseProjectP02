package com.will.weiyue.module;

import com.will.weiyue.MyApp;
import com.will.weiyue.net.RetrofitConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * author: liweixing
 * date: 2018/2/6
 */

@Module
public class HttpModule {
    @Provides
    OkHttpClient.Builder provideOkHttpClient() {
        //指定缓存路径，缓存大小 100MB
        Cache cache = new Cache(new File(MyApp.getContext().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);

        return new OkHttpClient().newBuilder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(RetrofitConfig.sLoggingInterceptor)
                .addInterceptor(RetrofitConfig.sRewriteCacheControlInterceptor)
                .addNetworkInterceptor(RetrofitConfig.sRewriteCacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS);
    }
}
