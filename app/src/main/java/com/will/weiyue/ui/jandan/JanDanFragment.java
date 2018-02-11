package com.will.weiyue.ui.jandan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.weiyue.R;
import com.will.weiyue.ui.base.SupportFragment;

/**
 * author: liweixing
 * date: 2018/2/11
 */

public class JanDanFragment extends SupportFragment{

    public static JanDanFragment newInstance() {
        
        Bundle args = new Bundle();
        
        JanDanFragment fragment = new JanDanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video, container, false);
        return view;
    }
}
