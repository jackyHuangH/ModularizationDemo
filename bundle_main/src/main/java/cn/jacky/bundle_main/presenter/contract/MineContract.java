package cn.jacky.bundle_main.presenter.contract;

import java.util.List;

import cn.jacky.bundle_main.bean.PCABean;
import cn.jacky.bundle_main.presenter.base.IPresenter;
import cn.jacky.bundle_main.presenter.base.IView;

/**
 * 作   者： by Hzj on 2017/12/13/013.
 * 描   述：
 * 修订记录：
 */

public interface MineContract {
    interface View extends IView {
        void onGetPCASuccess(List<PCABean> pcaBeanList);
    }

    interface Presenter extends IPresenter {
        void getPCAList();
    }

}
