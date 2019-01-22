package cn.jacky.bundle_main.wrapper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;

import com.zenchn.support.dafault.DefaultUiController;
import com.zenchn.support.widget.tips.SuperToast;


/**
 * 描    述：自定义 消息提示样式
 * 修订记录：
 * @author HZJ
 */

public abstract class CustomUiController extends DefaultUiController {

    public CustomUiController(@NonNull Context mContext) {
        super(mContext);
    }

    @Override
    public void showMessage(@NonNull CharSequence message) {
//        View snackBarParentView = getSnackBarParentView();
//        if (snackBarParentView != null) {
//            SuperSnackBar.createShortSnackbar(snackBarParentView, message, SuperSnackBar.Info).show();
//        } else {
//        }
        SuperToast.showDefaultMessage(mContext, message.toString());
    }

    @Override
    public void showResMessage(@StringRes int resId) {
        showMessage(mContext.getString(resId));
    }

    protected abstract View getSnackBarParentView();
}
