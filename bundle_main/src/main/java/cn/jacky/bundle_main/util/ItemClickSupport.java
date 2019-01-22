package cn.jacky.bundle_main.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.jacky.bundle_main.R;


/**
 * 处理recylerView  item 内部点击事件
 */
public class ItemClickSupport {
    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            if( mOnItemClickListener != null )
                mOnItemClickListener.onItemClicked(mRecyclerView, position, view);
        }
    };

    private View.OnLongClickListener itemLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View view) {
            View root = view.getRootView();
            if( mOnItemLongClickListener != null ){
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(root);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), view);
            }

            return false;
        }
    };

    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int position = (int) v.getTag();
            if (mOnItemLongClickListener != null)
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, position, v);
            return false;
        }
    };

    private int[] ids;
    private RecyclerView.OnChildAttachStateChangeListener mAttachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(view);
            int position = holder.getAdapterPosition();
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
                if( ids != null && ids.length > 0 ){
                    for (int id : ids) {
                        View child = view.findViewById(id);
                        if( child != null ) {
                            child.setTag(position);
                            child.setOnClickListener(itemClickListener);
                        }
                    }
                }
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
                if( ids != null && ids.length > 0 ){
                    for (int id : ids) {
                        View child = view.findViewById(id);
                        if( child != null ) {
                            child.setTag(position);
                            child.setOnLongClickListener(itemLongClickListener);
                        }
                    }
                }
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {}
    };

    private ItemClickSupport(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(R.id.item_click_support, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    public static ItemClickSupport addTo(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support == null) {
            support = new ItemClickSupport(view);
        }
        return support;
    }

    public static ItemClickSupport addTo(RecyclerView view, int... ids) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support == null) {
            support = new ItemClickSupport(view);
        }
        support.ids = ids;
        return support;
    }

    public static ItemClickSupport removeFrom(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public ItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.item_click_support, null);
    }

    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}
