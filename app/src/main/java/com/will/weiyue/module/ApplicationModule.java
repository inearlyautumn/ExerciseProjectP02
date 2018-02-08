package com.will.weiyue.module;

import android.content.Context;

import com.will.weiyue.MyApp;

import dagger.Module;
import dagger.Provides;

/**
 * author: liweixing
 * date: 2018/2/5
 */

@Module
public class ApplicationModule {
    private Context mContext;

    public ApplicationModule(Context mContext) {
        this.mContext = mContext;
    }

    @Provides
    MyApp provideApplication() {
        return (MyApp) mContext.getApplicationContext();
    }

    @Provides
    Context kprovideContext() {
        return mContext;
    }
}
