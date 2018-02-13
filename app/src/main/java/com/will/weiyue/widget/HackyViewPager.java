package com.will.weiyue.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * ScaleGestureDatector seems to mess up the touch events,which means that
 * ViewGroups which make use of onInterceptTouchEvent throw a lot of
 * IllegalArgumentException: pointerIndex out of range.
 *
 * There's not much I can do in my code for now, but we can mask the result by
 * just catching the problem and ignoring it.
 *
 * author: liweixing
 * date: 2018/2/13
 */

public class HackyViewPager extends ViewPager {
    public static final String TAG = HackyViewPager.class.getSimpleName();

    public HackyViewPager(Context context) {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            Log.i(TAG, "onInterceptTouchEvent: hacky viewpager error1");
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.i(TAG, "onInterceptTouchEvent: hacky viewpager error2");
            return false;
        }
    }
}
