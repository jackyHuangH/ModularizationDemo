package cn.jacky.bundle_main.behavior;


import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

/**
 * 作   者： by Hzj on 2017/12/7/007.
 * 描   述：   简单上下平移的behavior
 * 修订记录：
 */

public class TranslateBehavior extends CoordinatorLayout.Behavior<View> {

    // 列表顶部和title底部重合时，列表的滑动距离。
    private float deltaY;

    public TranslateBehavior() {
    }

    public TranslateBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof ScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (deltaY == 0) {
            deltaY = dependency.getY() - child.getHeight();
        }
        float dy = dependency.getY() - child.getHeight();
        if (dy < 0) {
            dy = 0;
        }
        Log.d("滑动啦", "onDependentViewChanged: "+dy);
        //平移
        float moveY = -(dy / deltaY) * child.getHeight();
        child.setTranslationY(moveY);

        //透明度
        float alpha = 1 - (dy / deltaY);
        child.setAlpha(alpha);
        return true;
    }
}
