package cn.jacky.bundle_main.widgets.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.ViewGroup;

/**
 * 作   者： by Hzj on 2017/12/13/013.
 * 描   述：   仿安卓通知栏通知层叠效果
 * <p>
 * 实现generateDefaultLayoutParams()方法，生成自己所定义扩展的LayoutParams。
 * 在onLayoutChildren()中实现初始列表中各个itemView的位置
 * 在scrollVerticallyBy()和scrollHorizontallyBy()中处理横向和纵向滚动，还有view的回收复用。
 * <p>
 * 作者：大头呆
 * 链接：https://juejin.im/post/5a2f8c476fb9a0450b665b11
 * 来源：掘金
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * 修订记录：
 */

public class OverLayLayoutManager extends RecyclerView.LayoutManager {
    // 用于保存item的位置信息
    private SparseArray<Rect> allItemRects = new SparseArray<>();
    // 用于保存item是否处于可见状态的信息
    private SparseBooleanArray itemStates = new SparseBooleanArray();


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollVerticallyBy(dy, recycler, state);

    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollHorizontallyBy(dx, recycler, state);

    }
}
