package cn.jacky.bundle_main.widgets.BottomSheetDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * author:Hzj
 * date  :2018/4/10/010
 * desc  ：
 * record：
 */

public class BottomSheetRecyclerView extends RecyclerView {

    private static final String TAG = "BottomSheetRecyclerView";

    private float downY;
    private float moveY;
    private CoordinatorLayout bottomCoordinator;

    public BottomSheetRecyclerView(Context context) {
        super(context);
        init();
    }

    public BottomSheetRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomSheetRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //do something
    }

    /**
     * 让coordinator不拦截事件
     */
    public void setCoordinatorDisallow() {
        if (bottomCoordinator == null) {
            return;
        }
        bottomCoordinator.requestDisallowInterceptTouchEvent(true);
    }

    /**
     * 绑定需要被拦截 intercept 的 CoordinatorLayout
     * canScrollVertically(1)是否滑动到顶部
     * canScrollVertically(-1)是否滑动到底部
     *
     * @param contentView View
     */
    public void bindBottomSheetDialog(View contentView) {
        // try throw illegal
        try {
            FrameLayout parentOne = (FrameLayout) contentView.getParent();
            this.bottomCoordinator = (CoordinatorLayout) parentOne.getParent();
            setOnTouchListener(
                    new OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (bottomCoordinator == null) {
                                return false;
                            }
                            int firstVisiblePos = getFirstVisiblePosition();
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    Log.e("aaaaa", "child onTouch ACTION_DOWN");
                                    downY = event.getRawY();
                                    bottomCoordinator.requestDisallowInterceptTouchEvent(true);
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    moveY = event.getRawY();
                                    Log.e("aaaaa", "child onTouch ACTION_MOVE firstVisiblePos " + firstVisiblePos + " -- isOverScroll " + canScrollVertically((int) (moveY - downY)));
                                    if ((moveY - downY) > 10) {
                                        if (firstVisiblePos == 0 && canScrollVertically((int) (moveY - downY))) {
                                            bottomCoordinator.requestDisallowInterceptTouchEvent(false);
                                            Log.e("aaaaa", "child onTouch 阻断");
                                            break;
                                        }
                                    }
                                    bottomCoordinator.requestDisallowInterceptTouchEvent(true);
                                    Log.e("aaaaa", "child onTouch 不阻断");
                                    break;
                                case MotionEvent.ACTION_UP:
                                    Log.e("aaaaa", "child onTouch ACTION_UP");
                                    break;
                                default:
                                    break;
                            }
                            return false;
                        }
                    }
            );
        } catch (Exception e) {
            // maybe 可能是强转异常
            // todo
        }
    }

    private int getFirstVisiblePosition() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            //获取第一个可见view的位置
            int firstItemPosition = linearManager.findFirstVisibleItemPosition();
            return firstItemPosition;
        } else {
            throw new IllegalArgumentException("you should use LinearLayoutManager!!!");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (bottomCoordinator == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int size = (int) ((float) (getResources().getDisplayMetrics().heightPixels * 0.618));
        int newHeightSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHeightSpec);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        Log.d(TAG, "onScrolled:dy " + dy);
    }
}
