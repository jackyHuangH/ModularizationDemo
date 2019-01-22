package cn.jacky.bundle_main.widgets.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

import cn.jacky.bundle_main.R;


/**
 * author:Hzj
 * date  :2018/1/25/025
 * desc  ：农房日历周视图
 * record：
 */

public class ZenchnWeekView extends MonthView {

    /**
     * 背景圆半径
     */
    private int mBgRadius;


    /**
     * 自定义魅族标记的文本画笔
     */
    private Paint mTextPaint = new Paint();

    /**
     * 自定义右上角圆形标记的圆形背景
     */
    private Paint mSchemeRTPaint = new Paint();
    /**
     * 圆形标记半径
     */
    private float mSchemeRTRadio;
    /**
     * 圆形标记内部边距
     */
    private int mSchemeCircleRTPadding;
    /**
     * 圆形标记内部文字基准线
     */
    private float mSchemeRTBaseLine;

    /**
     * 自定义底部圆点标记
     */
    private Paint mSchemeBottomPaint = new Paint();

    /**
     * 底部圆点标记半径
     */
    private float mSchemeBottomRadio;

    private int mSchemeBottomPadding;

    public ZenchnWeekView(Context context) {
        super(context);
    }

    /**
     * 初始化画笔，颜色等
     */
    @Override
    protected void onPreviewHook() {
        mBgRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.FILL);

        mSelectedPaint.setColor(getResources().getColor(R.color.color_32b4ca));

        mSchemeRTRadio = dipToPx(getContext(), 7);
        mSchemeCircleRTPadding = dipToPx(getContext(), 3);
        Paint.FontMetrics metrics = mSchemeRTPaint.getFontMetrics();
        mSchemeRTBaseLine = mSchemeRTRadio - metrics.descent + (metrics.bottom - metrics.top) / 2 + dipToPx(getContext(), 1);

        mSchemeBottomPaint.setColor(getResources().getColor(R.color.color_32b4ca));
        mSchemeBottomPaint.setAntiAlias(true);
        mSchemeBottomPaint.setStyle(Paint.Style.FILL);
        mSchemeBottomPaint.setTextAlign(Paint.Align.CENTER);
        mSchemeBottomPaint.setFakeBoldText(true);
        mSchemeBottomRadio = dipToPx(getContext(), 2);
        mSchemeBottomPadding = dipToPx(getContext(), 2);
    }

    @Override
    protected void onLoopStart(int x, int y) {

    }

    /**
     * 绘制选中效果
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param y         日历Card y起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
        int cy = mItemHeight / 2;
        canvas.drawCircle(cx, cy, mBgRadius, mSelectedPaint);
        return false;
    }


    /**
     * 绘制标记效果
     *
     * @param canvas   canvas
     * @param calendar 日历calendar
     * @param x        日历Card x起点坐标
     * @param y        日历Card y起点坐标
     */
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        //右上角数量标记
        mSchemeRTPaint.setColor(calendar.getSchemeColor());
        canvas.drawCircle(x + mItemWidth - mSchemeCircleRTPadding - mSchemeRTRadio / 2, mSchemeCircleRTPadding + mSchemeRTRadio, mSchemeRTRadio, mSchemeRTPaint);
        canvas.drawText(calendar.getScheme(), x + mItemWidth - mSchemeCircleRTPadding - mSchemeRTRadio, mSchemeRTRadio + mSchemeRTBaseLine, mTextPaint);

        //下面的圆点标记
        canvas.drawCircle(x + mItemWidth / 2, mItemHeight - mSchemeBottomPadding, mSchemeBottomRadio, mSchemeBottomPaint);
    }

    /**
     * 绘制文字，包括农历，这里不需要农历
     *
     * @param canvas     canvas
     * @param calendar   日历calendar
     * @param x          日历Card x起点坐标
     * @param y          日历Card y起点坐标
     * @param hasScheme  是否是标记的日期
     * @param isSelected 是否选中
     */
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine;
        int cx = x + mItemWidth / 2;

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    mSelectTextPaint);
        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
