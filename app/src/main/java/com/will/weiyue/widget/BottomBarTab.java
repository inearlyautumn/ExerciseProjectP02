package com.will.weiyue.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.will.weiyue.R;
import com.will.weiyue.utils.ContextUtils;

/**
 * author: liweixing
 * date: 2018/2/8
 */

class BottomBarTab extends LinearLayout {

    private Context mContext;
    private int icon;
    private ImageView mIcon;
    private TextView mTextView;
    private int mTabPosition = -1;

    public BottomBarTab(Context context, int icon, String title) {
        this(context, null, icon, title);
    }

    public BottomBarTab(Context context, @Nullable AttributeSet attrs, int icon, String title) {
        this(context, attrs, 0, icon, title);
    }

    public BottomBarTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int icon, String title) {
        super(context, attrs, defStyleAttr);
        init(context, icon, title);
    }

    private void init(Context context, int icon, String title) {
        mContext = context;
        this.icon = icon;
        setOrientation(VERTICAL);
        mIcon = new ImageView(context);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20,
                getResources().getDisplayMetrics());
        LayoutParams params = new LayoutParams(size, size);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = ContextUtils.dip2px(context, 2.5f);
        mIcon.setImageResource(icon);
        mIcon.setLayoutParams(params);

        LayoutParams textViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textViewParams.gravity = Gravity.CENTER_HORIZONTAL;
        textViewParams.topMargin = ContextUtils.dip2px(context, 2.5f);
        textViewParams.bottomMargin = ContextUtils.dip2px(context, 2.5f);
        mTextView = new TextView(context);
        mTextView.setText(title);
        mTextView.setTextSize(ContextUtils.dip2px(context, 3.2f));
        mTextView.setLayoutParams(textViewParams);
        mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.tab_unselect));
        addView(mIcon);
        addView(mTextView);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
            mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        } else {
            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.tab_unselect));
            mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.tab_unselect));
        }
    }

    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }
}
