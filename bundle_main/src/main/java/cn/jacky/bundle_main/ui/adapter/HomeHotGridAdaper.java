package cn.jacky.bundle_main.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jacky.bundle_main.Constants;
import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.R2;
import cn.jacky.bundle_main.wrapper.glide.GlideApp;

/**
 * Created by Hzj on 2017/8/15.
 * 首页夺宝热门GridView适配器
 */

public class HomeHotGridAdaper extends BaseAdapter {
    private Context mContext;

    public HomeHotGridAdaper(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeHotViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_hot, null);
            holder = new HomeHotViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HomeHotViewHolder) convertView.getTag();
        }

        GlideApp.with(mContext)
                .load(Constants.imgUrls[position%5])
                .centerCrop()
                .into(holder.ivHomeHot);
        holder.tvHomeHotTip.setText("剩余2"+position);

        return convertView;
    }

    class HomeHotViewHolder {
        @BindView(R2.id.iv_home_hot)
        ImageView ivHomeHot;
        @BindView(R2.id.tv_home_hot_tip)
        TextView tvHomeHotTip;

        public HomeHotViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
