package cn.jacky.bundle_main.ui.basepresenter;


import android.util.Log;

import com.zenchn.apilib.callback.rx.RxApiCallback;
import cn.jacky.bundle_main.ui.baseview.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author:Hzj
 * @date :2018/7/30/030
 * desc  ：
 * record：
 */
public abstract class BasePresenterImpl<V extends BaseView> implements IPresenter, RxApiCallback {

    private static final String TAG = "BasePresenterImpl";

    protected CompositeDisposable mCompositeDisposable;
    protected V mView;


    public BasePresenterImpl(V view) {
        this.mView = view;
        onStart();
    }

    @Override
    public String getToken() {
        return "";
    }

    @Override
    public void onStart() {
        //do something
    }

    @Override
    public void onDestroy() {
        mView = null;
        unDispose();
    }

    @Override
    public void onApiGrantRefuse() {
        if (null != mView) {
            mView.hideProgress();
            mView.onApiGrantRefuse();
        }
    }

    @Override
    public void onApiFailure(String err_msg) {
        if (null != mView) {
            mView.hideProgress();
            mView.onApiFailure(err_msg);
        }
    }

    /**
     * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
     * 可在 { Activity#onDestroy()} 中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务,避免内存泄漏
     *
     * @param disposable
     */
    @Override
    public void onRegisterObserver(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        //将所有 Disposable 放入集中处理
        mCompositeDisposable.add(disposable);

        Log.d(TAG, "onRegisterObserver:添加了 " + disposable);
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    public void unDispose() {
        if (mCompositeDisposable != null) {
            //保证 Activity 结束时取消所有正在执行的订阅
            mCompositeDisposable.clear();

            Log.d(TAG, "unDispose:取消所有正在执行的订阅 ");
        }
    }
}
