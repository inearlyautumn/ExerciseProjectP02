package com.will.weiyue;

import android.app.Application;

import com.will.weiyue.component.ApplicationComponent;
import com.will.weiyue.component.DaggerApplicationComponent;
import com.will.weiyue.module.ApplicationModule;
import com.will.weiyue.module.HttpModule;
import com.will.weiyue.utils.ContextUtils;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

/**
 * author: liweixing
 * date: 2018/2/6
 */

public class MyApp extends LitePalApplication {
    private ApplicationComponent mApplicationComponent;

    private static MyApp sMyApp;

    public static int width = 0;
    public static int height = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        sMyApp = this;
        BGASwipeBackManager.getInstance().init(this);
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();
        LitePal.initialize(this);
        width = ContextUtils.getScreenWidth(MyApp.getContext());
        height = ContextUtils.getScreenHeight(MyApp.getContext());
    }

    public static MyApp getInstance() {
        return sMyApp;
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
