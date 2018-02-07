package com.will.weiyue.ui.inter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.weiyue.component.ApplicationComponent;

/**
 * author: liweixing
 * date: 2018/2/5
 */

public interface IBase {
    View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    View getView();

    int getContentLayout();

    void initInjector(ApplicationComponent applicationComponent);

    void BindView(View view, Bundle saveInstanceState);

    void initData();
}
