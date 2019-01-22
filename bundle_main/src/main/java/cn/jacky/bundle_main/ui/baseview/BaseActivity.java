package cn.jacky.bundle_main.ui.baseview;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OnKeyboardListener;
import com.zenchn.support.base.AbstractAppCompatActivity;
import com.zenchn.support.base.IActivityLifecycle;
import com.zenchn.support.base.IUiController;
import com.zenchn.support.dafault.DefaultActivityLifecycle;
import com.zenchn.support.kit.AndroidKit;
import com.zenchn.support.utils.StringUtils;
import com.zenchn.support.widget.TitleBar;

import javax.inject.Inject;

import cn.jacky.bundle_main.ApplicationKit;
import cn.jacky.bundle_main.di.component.AppComponent;
import cn.jacky.bundle_main.ui.basepresenter.BasePresenterImpl;
import cn.jacky.bundle_main.wrapper.CustomUiController;

/**
 * @author:Hzj
 * @date :2018/9/13/013
 * desc  ：
 * record：
 */
public abstract class BaseActivity<P extends BasePresenterImpl> extends AbstractAppCompatActivity implements BaseView, TitleBar.OnLeftClickListener {

    protected static final int PAGE_SIZE = 10;
    protected ImmersionBar mImmersionBar;

    @Inject
    @Nullable
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppComponent applicationComponent = ApplicationKit.getApplicationComponent();
        componentInject(applicationComponent);

        initInstanceState(savedInstanceState);
        if (savedInstanceState == null) {
            initWidget();
        }
        initStatusBar();
    }

    /**
     * 依赖注入入口
     */
    protected abstract void componentInject(AppComponent appComponent);

    @Override
    protected IUiController getDefaultUiController() {
        return new CustomUiController(this) {
            @Override
            protected View getSnackBarParentView() {
                return findViewById(getSnackBarParentIdRes());
            }
        };
    }

    @IdRes
    protected int getSnackBarParentIdRes() {
        return android.R.id.content;
    }

    protected void initStatusBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .fitsSystemWindows(true)
                //状态栏颜色，不写默认透明色
                .statusBarColor(android.R.color.white)
                //状态栏字体是深色，不写默认为亮色
                .statusBarDarkFont(true, 0.2f);


        //是否需要监听键盘
        if (addOnKeyboardListener() != null) {
            mImmersionBar
                    .keyboardEnable(true)
                    //单独指定软键盘模式
                    .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    .setOnKeyboardListener(addOnKeyboardListener());
        }
        mImmersionBar.init();
    }

    protected OnKeyboardListener addOnKeyboardListener() {
        return null;
    }


    @NonNull
    @Override
    protected IActivityLifecycle getDefaultActivityLifecycle() {
        return DefaultActivityLifecycle.getInstance();
    }

    //界面布局的初始化操作
    protected void initInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onApiFailure(String msg) {
        showMessage(msg);
    }

    @Override
    public void onApiGrantRefuse() {
        ApplicationKit.getInstance().navigateToLogin(true);
    }

    @Override
    public void showMessage(@NonNull CharSequence msg) {
        if (StringUtils.isNonNull(msg)) {
            super.showMessage(msg);
        }
    }

    @Override
    public void onLeftViewClick(View v) {
        onBackPressed();
    }

    @Override
    protected void onPause() {
        AndroidKit.Keyboard.hideSoftInput(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        super.onDestroy();
    }
}
