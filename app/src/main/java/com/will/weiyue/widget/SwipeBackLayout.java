package com.will.weiyue.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.target.Target;

/**
 * author: liweixing
 * date: 2018/2/12
 */

public class SwipeBackLayout extends ViewGroup {
    public static final String TAG = SwipeBackLayout.class.getSimpleName();
    private View target;
    private  ViewDragHelper viewDragHelper;

    /**
     * Whether allow to pull this layout.
     */
    private boolean enablePullToBack = true;
    private int verticalDragRange = 0;
    private int horizontalDragRange = 0;
    private View scrollChild;
    private int draggingState = 0;
    private int draggingOffset;
    /**
     * the anchor of calling finish.
     */
    private float finishAnchor = 0;
    private boolean enableFlingBack = true;

    public enum DragDirectMode {
        EDGE,
        VERTICAL,
        HORIZONTAL
    }

    public enum DragEdge {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM
    }

    private DragDirectMode dragDirectMode = DragDirectMode.EDGE;

    private DragEdge dragEdge = DragEdge.TOP;

    public void setDragEdge(DragEdge dragEdge) {
        this.dragEdge = dragEdge;
    }

    public void setDragDirectMode(DragDirectMode dragDirectMode) {
        this.dragDirectMode = dragDirectMode;
        if (dragDirectMode == DragDirectMode.VERTICAL) {
            this.dragEdge = DragEdge.TOP;
        } else if (dragDirectMode == DragDirectMode.HORIZONTAL) {
            this.dragEdge = DragEdge.LEFT;
        }
    }

    private static final double AUTO_FINISHED_SPEED_LIMIT = 2000.0;


    public SwipeBackLayout(Context context) {
        super(context);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelperCallback());
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private class ViewDragHelperCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == target && enablePullToBack;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return verticalDragRange;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return horizontalDragRange;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {

            int result = 0;

            if (dragDirectMode == DragDirectMode.VERTICAL) {
                if (!canChildScrollUp() && top > 0) {
                    dragEdge = DragEdge.TOP;
                } else if (!canChildScrollDown() && top < 0) {
                    dragEdge = DragEdge.BOTTOM;
                }
            }

            if (dragEdge == DragEdge.TOP && !canChildScrollUp() && top > 0) {
                final int topBound = getPaddingTop();
                final int bottomBound = verticalDragRange;
                result = Math.min(Math.max(top, topBound), bottomBound);
            } else if (dragEdge == DragEdge.BOTTOM && !canChildScrollDown() && top < 0) {
                final int topBound = -verticalDragRange;
                final int bottomBound = getPaddingTop();
                result = Math.min(Math.max(top, topBound), bottomBound);
            }
            return result;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int result = 0;

            if (dragDirectMode == DragDirectMode.HORIZONTAL) {
                if (!canChildScrollRight() && left > 0) {
                    dragEdge = DragEdge.LEFT;
                } else if (!canChildScrollLeft() && left < 0) {
                    dragEdge = DragEdge.RIGHT;
                }
            }

            if (dragEdge == DragEdge.LEFT && !canChildScrollRight() && left > 0) {
                final int leftBound = getPaddingLeft();
                final int rightBound = horizontalDragRange;
                result = Math.min(Math.max(left, leftBound), rightBound);
            } else if (dragEdge == DragEdge.RIGHT && !canChildScrollLeft() && left < 0) {
                final int leftBound = -horizontalDragRange;
                final int rightBound = getPaddingLeft();
                result = Math.min(Math.max(left, leftBound), rightBound);
            }
            return result;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            if (state == draggingState) {
                return;
            }

            if (draggingState == ViewDragHelper.STATE_DRAGGING || draggingState == ViewDragHelper.STATE_SETTLING
                    && state == ViewDragHelper.STATE_IDLE) {
                //the view stopped from moving.
                if (draggingOffset == getDragRange()) {
                    finish();
                }
            }
            draggingState = state;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            switch (dragEdge) {
                case TOP:
                case BOTTOM:
                    draggingOffset = Math.abs(top);
                    break;
                case LEFT:
                case RIGHT:
                    draggingOffset = Math.abs(left);
                    break;
                default:
                    break;
            }

            //The proportion of the sliding
            float fractionAnchor = draggingOffset / finishAnchor;
            if (fractionAnchor >= 1) {
                fractionAnchor = 1;
            }

            float fractionScreen = (float) draggingOffset / (float) getDragRange();
            if (fractionScreen >= 1) {
                fractionScreen = 1;
            }

            if (swipeBackListener != null) {
                swipeBackListener.onViewpositionChanged(fractionAnchor, fractionScreen);
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (draggingOffset == 0) {
                return;
            }

            if (draggingOffset == getDragRange()) {
                return;
            }

            boolean isBack = false;

            if (enableFlingBack && backBySpeed(xvel, yvel)) {
                isBack = !canChildScrollUp();
            } else if (draggingOffset >= finishAnchor) {
                isBack = true;
            } else if (draggingOffset < finishAnchor) {
                isBack = false;
            }
            int finalLeft;
            int finalTop;
            switch (dragEdge) {
                case LEFT:
                    finalLeft = isBack ? horizontalDragRange : 0;
                    smoothScrollToX(finalLeft);
                    break;
                case RIGHT:
                    finalLeft = isBack ? -horizontalDragRange : 0;
                    smoothScrollToX(finalLeft);
                    break;
                case TOP:
                    finalTop = isBack ? verticalDragRange : 0;
                    smoothScrollToY(finalTop);
                    break;
                case BOTTOM:
                    finalTop = isBack ? -verticalDragRange : 0;
                    smoothScrollToY(finalTop);
                    break;
            }
        }
    }

    private boolean backBySpeed(float xvel, float yvel) {
        switch (dragEdge) {
            case TOP:
            case BOTTOM:
                if (Math.abs(yvel) > Math.abs(xvel) && Math.abs(yvel) > AUTO_FINISHED_SPEED_LIMIT) {
                    return dragEdge == DragEdge.TOP ? !canChildScrollUp() : !canChildScrollDown();
                }
                break;
            case LEFT:
            case RIGHT:
                if (Math.abs(xvel) > Math.abs(yvel) && Math.abs(xvel) > AUTO_FINISHED_SPEED_LIMIT) {
                    return dragEdge == DragEdge.LEFT ? !canChildScrollLeft() : !canChildScrollRight();
                }
                break;
        }
        return false;
    }

    private void smoothScrollToX(int finalLeft) {
        if (viewDragHelper.settleCapturedViewAt(finalLeft, 0)) {
            ViewCompat.postInvalidateOnAnimation(SwipeBackLayout.this);
        }
    }

    private void smoothScrollToY(int finalTop) {
        if (viewDragHelper.settleCapturedViewAt(0, finalTop)) {
            ViewCompat.postInvalidateOnAnimation(SwipeBackLayout.this);
        }
    }

    private void finish() {
        Activity act = (Activity) getContext();
        act.finish();
        act.overridePendingTransition(0, android.R.anim.fade_out);
    }

    private int getDragRange() {
        switch (dragEdge) {
            case TOP:
            case BOTTOM:
                return verticalDragRange;
            case LEFT:
            case RIGHT:
                return horizontalDragRange;
            default:
                return verticalDragRange;
        }
    }

    private boolean canChildScrollUp() {
        return ViewCompat.canScrollVertically(scrollChild, -1);
    }

    private boolean canChildScrollDown() {
        return ViewCompat.canScrollVertically(scrollChild, 1);
    }

    private boolean canChildScrollRight() {
        return ViewCompat.canScrollHorizontally(scrollChild, -1);
    }

    private boolean canChildScrollLeft() {
        return ViewCompat.canScrollHorizontally(scrollChild, 1);
    }

    private SwipeBackListener swipeBackListener;

    public interface SwipeBackListener {
        /**
         * Return scrolled fraction of the layout.
         *
         * @param fractionAnchor relative to the anchor.
         * @param fractionScreen relative to the screen.
         */
        void onViewpositionChanged(float fractionAnchor, float fractionScreen);
    }

    public void setOnSwipeBackListener(SwipeBackListener listener) {
        swipeBackListener = listener;
    }
}
