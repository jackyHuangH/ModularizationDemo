package cn.jacky.bundle_main.ui.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

import cn.jacky.bundle_main.bean.JoinRecordBean;
import cn.jacky.bundle_main.ui.adapter.itemDelegate.RecordContentItemDelegate;
import cn.jacky.bundle_main.ui.adapter.itemDelegate.RecordDateItemDelegate;

/**
 * Created by Hzj on 2017/8/17.
 * 参与记录adapter
 */

public class JoinRecordAdapter extends MultiItemTypeAdapter<JoinRecordBean> {

    public JoinRecordAdapter(Context context, List<JoinRecordBean> datas) {
        super(context, datas);
        addItemViewDelegate(new RecordDateItemDelegate(mContext));
        addItemViewDelegate(new RecordContentItemDelegate());
    }

}
