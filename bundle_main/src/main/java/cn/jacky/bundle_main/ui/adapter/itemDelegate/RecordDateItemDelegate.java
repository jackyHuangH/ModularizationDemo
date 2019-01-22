package cn.jacky.bundle_main.ui.adapter.itemDelegate;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zenchn.support.kit.AndroidKit;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.bean.JoinRecordBean;

/**
 * Created by Hzj on 2017/8/17.
 * 参与记录显示日期头部
 */

public class RecordDateItemDelegate implements ItemViewDelegate<JoinRecordBean> {
    private Context mContext;
    public RecordDateItemDelegate(Context context) {
        mContext=context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_record_date;
    }

    @Override
    public boolean isForViewType(JoinRecordBean item, int position) {
        return item.getType() == 0;
    }

    @Override
    public void convert(ViewHolder holder, JoinRecordBean joinRecordBean, int position) {
        if (position == 0) {
            holder.setAlpha(R.id.top_line, 0);
            View topLine = holder.getView(R.id.top_line);
            ViewGroup.LayoutParams layoutParams = topLine.getLayoutParams();
            layoutParams.height= AndroidKit.Dimens.dp2px(20);
            topLine.setLayoutParams(layoutParams);
        }else {
            holder.setAlpha(R.id.top_line, 1);
            View topLine = holder.getView(R.id.top_line);
            ViewGroup.LayoutParams layoutParams = topLine.getLayoutParams();
            layoutParams.height= AndroidKit.Dimens.dp2px(6);
            topLine.setLayoutParams(layoutParams);
        }
        holder.setText(R.id.record_date_tip, joinRecordBean.getDate());
    }
}
