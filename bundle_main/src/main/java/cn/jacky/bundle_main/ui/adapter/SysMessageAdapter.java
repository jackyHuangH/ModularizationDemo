package cn.jacky.bundle_main.ui.adapter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.bean.MessageEntity;
import cn.jacky.bundle_main.wrapper.glide.GlideApp;

/**
 * Created by Hzj on 2017/8/23.
 * 系统消息适配器
 */

public class SysMessageAdapter extends CommonAdapter<MessageEntity> {
    public SysMessageAdapter(Context context, int layoutId, List<MessageEntity> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MessageEntity item, int position) {
        holder.setText(R.id.tv_message_title, item.message);

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mOnItemTouchCallback != null) {
                        mOnItemTouchCallback.onItemTouch();
                    }
                }
                return false;
            }
        });


        ImageView ivMessage = holder.getView(R.id.iv_message);


        GlideApp
                .with(mContext)
                .asBitmap()
                .load(item.imgUrl)
                .centerCrop()
                .placeholder(R.drawable.default_no_pic)
                .into(ivMessage);

    }

    public interface OnItemTouchCallback {
        void onItemTouch();
    }

    private OnItemTouchCallback mOnItemTouchCallback;

    public void setOnItemTouchCallback(OnItemTouchCallback onItemTouchCallback) {
        mOnItemTouchCallback = onItemTouchCallback;
    }
}
