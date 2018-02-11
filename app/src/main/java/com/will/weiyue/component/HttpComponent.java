package com.will.weiyue.component;

import com.will.weiyue.ui.news.NewsFragment;

import dagger.Component;

/**
 * author: liweixing
 * date: 2018/2/9
 */
@Component(dependencies = ApplicationComponent.class)
public interface HttpComponent {
    void inject(NewsFragment newsFragment);
}
