package com.will.weiyue.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.SupportHelper;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * author: liweixing
 * date: 2018/2/5
 */

class SupportActivity extends RxAppCompatActivity implements ISupportActivity {
    final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);

    @Override
    public SupportActivityDelegate getSupportDelegate() {
        return mDelegate;
    }

    /**
     * Perform some extra transactions
     *
     * @return
     */
    @Override
    public ExtraTransaction extraTransaction() {
        return mDelegate.extraTransaction();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ？？？
        mDelegate.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        mDelegate.onDestroy();
        super.onDestroy();
    }

    /**
     * Note: return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return super.dispatchTouchEvent(ev);
        return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    /**
     * 不建议复写该方法，请使用{@link #onBackPressedSupport()} 代替
     */
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        mDelegate.onBackPressed();
    }

    /**
     * 获取设置的全局动画 copy
     * @return
     */
    @Override
    public FragmentAnimator getFragmentAnimator() {
        return mDelegate.getFragmentAnimator();
    }

    /**
     * set all fragments animation
     * 设置Fragment内的全局动画
     * @param fragmentAnimator
     */
    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    /**set all fragment animation
     * 构建Fragment 转场动画
     * 如果是在Activity内实现，则构建的是Activity内所有的Fragment的转场动画，
     * 如果是在Fragment内实现，则构建的是该Fragment的转场动画，此时优先级 >Activity的onCreateFragmentAnimator()
     * @return FragmentAnimator对象
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return null;
    }

    /**
     * 该方法回调时机为,Activity回退堆内Fragment的数量，小于等于1时，默认finish Activity
     * 请尽量复写该方法，避免复写 onBackPress(),以保证SupportFragment 内的onBackPressedSupport()回退事件正常执行
     */
    @Override
    public void onBackPressedSupport() {
        mDelegate.onBackPressedSupport();
    }

    /**
     * 获取栈内的Fragment对象
     * @param fragmentClass
     * @param <T>
     * @return
     */
    public <T extends ISupportFragment> T findFragment(Class<T> fragmentClass) {
        return SupportHelper.findFragment(getSupportFragmentManager(), fragmentClass);
    }
}
