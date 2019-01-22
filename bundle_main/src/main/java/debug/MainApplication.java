package debug;

import android.app.Application;

import cn.jacky.bundle_main.ApplicationKit;

/**
 * @author:Hzj
 * @date :2019/1/21/021
 * desc  ：
 * record：
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //do test
        ApplicationKit.getInstance().initKit(this);
    }
}
