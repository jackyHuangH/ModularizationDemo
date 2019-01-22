package cn.jacky.bundle_main.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hzj on 2017/8/28.
 * 状态栏透明适配工具
 */

public class StatusBarUtil {

    /**
     * 获取状态栏高度——方法1
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 增加View的paddingTop,增加的值为状态栏高度 (智能判断，并设置高度)
     * 配合toolbar使用
     */
    public static void setSmartPadding(Context context, View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        int statusBarHeight = getStatusBarHeight(context);
        if (lp != null && lp.height > 0) {
            //增高
            lp.height += statusBarHeight;
        }
        view.setLayoutParams(lp);
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + statusBarHeight,
                view.getPaddingRight(), view.getPaddingBottom());
    }
}
