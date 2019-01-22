package cn.jacky.bundle_main.ui.adapter.itemDelegate;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.bean.JoinRecordBean;

/**
 * Created by Hzj on 2017/8/17.
 * 参与记录内容
 */

public class RecordContentItemDelegate implements ItemViewDelegate<JoinRecordBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_join_record;
    }

    @Override
    public boolean isForViewType(JoinRecordBean item, int position) {
        return item.getType() == 1;
    }

    @Override
    public void convert(ViewHolder holder, JoinRecordBean joinRecordBean, int position) {
        holder.setText(R.id.tv_record_user_name, joinRecordBean.getName());
        holder.setImageResource(R.id.iv_ring_record, position % 2 == 0 ?
                R.drawable.ring_green : R.drawable.ring_blue);
    }
}
