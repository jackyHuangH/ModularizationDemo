package cn.jacky.bundle_main.widgets.zhihu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 作   者： by Hzj on 2018/1/24/024.
 * 描   述：高仿知乎滑动广告iamgeview
 * 修订记录：
 */

public class ZhiHuAdImageView extends AppCompatImageView {
    private static final String TAG = "ZhiHuAdImageView";

    //广告控件的高度
    private int itemHeight = 0;

    //初始化显示比率
    private float rate = 1;

    public ZhiHuAdImageView(Context context) {
        this(context, null);
    }

    public ZhiHuAdImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZhiHuAdImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 获取广告控件高度
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        itemHeight = h;
    }

    /**
     * 移动图片
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        int maxWidth = getWidth();
        //drawable.getIntrinsicWidth()获取固有宽度，单位dp
        //最大宽度 / 图片原本宽度 = 最大高度 / 图片原本高度
        int maxHeight = (int) (getWidth() * 1.0 / drawable.getIntrinsicWidth() * drawable.getIntrinsicHeight());
        //设置图片显示的绝对范围
        drawable.setBounds(0, 0, maxWidth, maxHeight);

        //图片可以移动的最大距离为(图片有效移动距离)：   (0 ~ -maxDy)
        int maxDy = maxHeight - itemHeight;
        canvas.save();
        canvas.translate(0, -rate * maxDy);
        super.onDraw(canvas);
        canvas.restore();
    }

    /**
     * 有效滑动高度(广告有效移动距离)
     * 控制滑动比率
     *
     * @param itemDy   广告控件高度
     * @param rvheight recyclerview 高度
     */
    public void setDy(int itemDy, int rvheight) {
        int allHeight = rvheight - itemHeight;
        rate = itemDy * 1f / allHeight;

        if (rate <= 0) {
            rate = 0;
        }
        if (rate >= 1) {
            rate = 1;
        }

        Log.d(TAG, "rate: "+rate);
        invalidate();
    }
}
