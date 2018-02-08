package com.will.weiyue;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.will.weiyue.component.ApplicationComponent;
import com.will.weiyue.ui.base.BaseActivity;
import com.will.weiyue.ui.base.SupportFragment;
import com.will.weiyue.ui.news.NewsFragment;
import com.will.weiyue.ui.utils.StatusBarUtil;
import com.will.weiyue.widget.BottomBar;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private SupportFragment[] mFragments = new SupportFragment[4];

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {

    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void bindView(View view, Bundle saveInstanceState) {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, 0, null);
        if (saveInstanceState == null) {
            mFragments[0] = NewsFragment.newInstance();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onRetry() {

    }
}
