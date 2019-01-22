package cn.jacky.bundle_main.widgets.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * author:Hzj
 * date  :2018/2/2/002
 * desc  ：
 * record：
 */

public class CircleView extends ViewGroup {
    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //View测量三步骤：

        //第一步：设置默认宽高
        int defaultWith=200;
        int defaultHeight=200;

        //第二步：调用resolvsize()处理
        defaultWith = resolveSize(defaultWith, widthMeasureSpec);
        defaultHeight=resolveSize(defaultHeight,heightMeasureSpec);

        //第三步：调用setMeasuredDimension设置最终测量结果
        setMeasuredDimension(defaultWith,defaultHeight);

//        measureChildren();
    }
}
