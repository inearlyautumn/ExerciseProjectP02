package com.will.weiyue.ui.video;

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

public class VideoFragment extends SupportFragment {

    public static VideoFragment newInstance() {

        Bundle args = new Bundle();

        VideoFragment fragment = new VideoFragment();
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
