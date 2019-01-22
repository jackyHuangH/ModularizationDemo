package cn.jacky.bundle_main.di.component;


import javax.inject.Singleton;

import cn.jacky.bundle_main.ApplicationKit;
import cn.jacky.bundle_main.di.module.AppModule;
import dagger.Component;

/**
 * @author:Hzj
 * @date :2018/7/30/030
 * desc  ：
 * record：
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(ApplicationKit applicationKit);
}
