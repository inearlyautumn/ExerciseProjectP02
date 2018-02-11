package com.will.weiyue.module;

import com.google.gson.Gson;
import com.will.weiyue.MyApp;
import com.will.weiyue.net.ApiConstants;
import com.will.weiyue.net.JanDanApi;
import com.will.weiyue.net.JanDanApiService;
import com.will.weiyue.net.NewsApi;
import com.will.weiyue.net.NewsApiService;
import com.will.weiyue.net.RetrofitConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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

    @Provides
    NewsApi provideNetEaseApis(OkHttpClient.Builder builder) {
        builder.addInterceptor(RetrofitConfig.sQueryParameterInterceptor);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build());

        return NewsApi.getInstance(retrofitBuilder
                .baseUrl(ApiConstants.sIFengApi)
                .build()
                .create(NewsApiService.class));
    }

    @Provides
    JanDanApi provideJanDanApis(OkHttpClient.Builder builder) {

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build());

        return JanDanApi.getInstance(retrofitBuilder
                .baseUrl(ApiConstants.sJanDanApi)
                .build()
                .create(JanDanApiService.class));
    }
}
