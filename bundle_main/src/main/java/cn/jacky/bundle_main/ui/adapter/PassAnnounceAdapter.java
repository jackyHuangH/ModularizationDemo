package cn.jacky.bundle_main.ui.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.jacky.bundle_main.R;


/**
 * Created by Hzj on 2017/8/22.
 * 往期揭晓适配器
 */

public class PassAnnounceAdapter extends CommonAdapter<String>{
    public PassAnnounceAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.tv_publish_user,s);
    }
}
