package cn.jacky.bundle_main.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import cn.jacky.bundle_main.presenter.contract.HomeContract;
import cn.jacky.bundle_main.ui.basepresenter.BasePresenterImpl;


/**
 * Created by Hzj on 2017/8/17.
 * 首页Presenter
 */

public class HomePresenterImpl extends BasePresenterImpl implements HomeContract.Presenter {
    private HomeContract.View iHomeView;

    public HomePresenterImpl(HomeContract.View view) {
        super(view);
        this.iHomeView = view;
    }

    @Override
    public void getHomeNotice(String id) {
        List<String> info = new ArrayList<>();
        info.add("1. 恭喜“pachira”1分钟前获得“iPhone8 Plus”");
        info.add("2. 欢迎大家关注我哦！欢迎大家关注我哦！欢迎大家关注我哦！欢迎大家关注我哦！欢迎大家关注我哦！");
        info.add("3. 恭喜“pachira”1分钟前获得“iPhone8 Plus”");
        info.add("4. 新浪微博：孙福生微博");
        info.add("5. 恭喜“pachira”1分钟前获得“iPhone8 Plus”");
        info.add("6. 微信公众号：孙福生");

        if (null != iHomeView) {
            iHomeView.showMessage("获取数据成功啦！！");
            iHomeView.showNotice(info);
        }
    }

    @Override
    public void unbindView() {
        iHomeView = null;
    }
}
