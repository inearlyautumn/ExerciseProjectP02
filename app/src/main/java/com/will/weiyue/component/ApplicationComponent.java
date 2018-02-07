package com.will.weiyue.component;

import com.will.weiyue.module.ApplicationModule;

import dagger.Component;

/**
 * author: liweixing
 * date: 2018/2/5
 */

@Component(modules = {ApplicationModule.class,HttpModule.class})
public class ApplicationComponent {
}
