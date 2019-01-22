package cn.jacky.bundle_main.ui.activity;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zenchn.support.managers.HFragmentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.R2;
import cn.jacky.bundle_main.di.component.AppComponent;
import cn.jacky.bundle_main.http.entity.CarEntity;
import cn.jacky.bundle_main.ui.baseview.BaseActivity;
import cn.jacky.bundle_main.ui.fragment.HomeFragment;
import cn.jacky.bundle_main.ui.fragment.MineFragment;
import cn.jacky.bundle_main.ui.fragment.ShowOrderFragment;
import cn.jacky.bundle_main.widgets.BottomTabView;

// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = "/main/MainActivity")
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private static final String EXTRA_MSG = "EXTRA_MSG";
    private static final String EXTRA_CAR = "EXTRA_CAR";

    @BindView(R2.id.fl_home_container)
    FrameLayout flHomeContainer;
    @BindView(R2.id.bottom_tab)
    BottomTabView bottomTab;

    //通过注解@AutoWired获取ARouter传递的参数
    @Autowired(name = EXTRA_MSG)
    String msg;
    @Autowired(name = EXTRA_CAR)
    CarEntity mCarEntity;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }


    @Override
    public void initWidget() {
        ARouter.getInstance().inject(this);
        String result = String.format(Locale.CHINA, "msg:%1$s--car:%2$s", msg, mCarEntity);

        Log.d(TAG, "EXTRA: " + result);

        HFragmentManager fragmentManagerHelper = new HFragmentManager(getSupportFragmentManager(), R.id.fl_home_container);
        //初始化所有fragment，
        // 注意：这里切换fragment时要保证每个fragment只有唯一一个实例，不能重复创建！！！
        Fragment[] fragments = {
                HomeFragment.getInstance(),
                ShowOrderFragment.getInstance(),
                MineFragment.getInstance()
        };

        //默认显示首页
        fragmentManagerHelper.add(fragments[0]);

        //初始化底部tab
        List<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();
        tabItemViews.add(new BottomTabView.TabItemView(MainActivity.this, "首页", R.color.coolGreyTwo, R.color.dark, R.drawable.tb_home, R.drawable.tb_home_touch));
        tabItemViews.add(new BottomTabView.TabItemView(MainActivity.this, "晒单", R.color.coolGreyTwo, R.color.dark, R.drawable.tb_order, R.drawable.tb_order_touch));
        tabItemViews.add(new BottomTabView.TabItemView(MainActivity.this, "我的", R.color.coolGreyTwo, R.color.dark, R.drawable.tb_mine, R.drawable.tb_mine_touch));
        bottomTab.setTabItemViews(tabItemViews);
        bottomTab.setOnTabItemSelectListener(new BottomTabView.OnTabItemSelectListener() {
            @Override
            public void onTabItemSelect(int position) {
                switch (position) {
                    //注意：这里切换fragment时要保证每个fragment只有唯一一个实例，不能重复创建！！！！
                    case 0:
                        fragmentManagerHelper.switchFragment(fragments[0]);
                        break;
                    case 1:
                        fragmentManagerHelper.switchFragment(fragments[1]);
                        break;
                    case 2:
                        fragmentManagerHelper.switchFragment(fragments[2]);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    public static void launch(String extraMsg, CarEntity carEntity) {
        //使用ARouter路由简单跳转
        ARouter
                .getInstance()
                .build("/main/MainActivity")
                .withString(EXTRA_MSG, extraMsg)
                .withSerializable(EXTRA_CAR, carEntity)
                .navigation();
    }
}
