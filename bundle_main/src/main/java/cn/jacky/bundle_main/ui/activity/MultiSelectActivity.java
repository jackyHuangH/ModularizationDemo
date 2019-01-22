package cn.jacky.bundle_main.ui.activity;

import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.di.component.AppComponent;
import cn.jacky.bundle_main.ui.baseview.BaseActivity;

public class MultiSelectActivity extends BaseActivity {
    private static final String TAG = "MultiSelectActivity";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_multi_select;
    }


    @Override
    public void initWidget() {

    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }
}
