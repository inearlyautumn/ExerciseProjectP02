package com.will.weiyue.ui.base;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.will.weiyue.MyApp;
import com.will.weiyue.R;
import com.will.weiyue.ui.inter.IBase;
import com.will.weiyue.ui.utils.StatusBarUtil;
import com.will.weiyue.ui.widget.MultiStateView;
import com.will.weiyue.ui.widget.SimpleMultiStateView;
import com.will.weiyue.utils.ToastUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * author: liweixing
 * date: 2018/2/5
 */

public abstract class BaseActivity<T1 extends BaseContract.BasePresenter> extends SupportActivity
        implements IBase, BaseContract.BaseView, BGASwipeBackHelper.Delegate {

    protected View mRootView;
    protected Dialog mLoadingDialog = null;
    Unbinder unbinder;
    protected BGASwipeBackHelper mSwipeBackHelper;

    @Nullable
    @Inject
    protected T1 mPresenter;

    //    @Nullable
//    @BindView(R.id.SimpleMultiStateView)
    SimpleMultiStateView mSimpleMultiStateView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        mRootView = createView(null, null, savedInstanceState);
        setContentView(mRootView);
        initInjector(MyApp.getInstance().getApplicationComponent());
        attachView();
        bindView(mRootView, savedInstanceState);
        initStateView();

    }

    @Override
    public View getView() {
        return mRootView;
    }

    private void initStateView() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.setEmptyResource(R.layout.view_empty)
                    .setRetryResource(R.layout.view_retry)
                    .setLoadingResource(R.layout.view_loading)
                    .setNoNetResource(R.layout.view_nonet)
                    .build()
                    .setonReLoadlistener(new MultiStateView.onReLoadlistener() {
                        @Override
                        public void onReload() {
                            onRetry();
                        }
                    });

        }
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(getContentLayout(), container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 初始化滑动返回。在super.onCreate(savedInstanceState)之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);
        //[必须在Application 的onCreate 方法中执行 BGASwi0peBackHelper.getInstance().init（this）来初始化滑动返回]
        //下面几项可以不配置，这里只是为了讲述接口用法
        //设置滑动返回是否可用，默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        //设置是否仅仅跟踪在侧边缘的滑动返回，默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(false);
        //设置是否是微信滑动返回样式。默认值 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        //设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        //设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        //设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        //设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
    }

    /**
     * 是否支持滑动返回，这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

/*    *//**
     * 设置状态栏颜色
     *
     * @param
     *//*
    protected void setStatusBarColor(@ColorInt int color) {
        setStatusBarColor(color, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    public void setStatusBarColor(@ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        StatusBarUtil.setColorForSwipBack(this, color, statusBarAlpha);
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showLoading() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showLoadingView();
        }
    }

    @Override
    public void showSuccess() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showContent();
        }
    }

    @Override
    public void showFaild() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showErrorView();
        }
    }

    @Override
    public void showNoNet() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showNoNetView();
        }
    }

    protected void T(String string) {
        ToastUtil.showShort(MyApp.getContext(), string);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {

    }

    @Override
    public void onSwipeBackLayoutCancel() {

    }

    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }
}
