package com.will.weiyue.component;

import com.will.weiyue.ui.news.DetailFragment;
import com.will.weiyue.ui.news.ImageBrowseActivity;
import com.will.weiyue.ui.news.NewsFragment;
import com.will.weiyue.ui.video.VideoFragment;
import dagger.Component;

/**
 * author: liweixing
 * date: 2018/2/9
 */
@Component(dependencies = ApplicationComponent.class)
public interface HttpComponent {

    void inject(VideoFragment videoFragment);

    void inject(DetailFragment detailFragment);

    void inject(ImageBrowseActivity imageBrowseActivity);

//    void inject(JdDetailFragment jdDetailFragment);
//
//    void inject(ArticleReadActivity articleReadActivity);

    void inject(NewsFragment newsFragment);
}
