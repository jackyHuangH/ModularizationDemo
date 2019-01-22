package cn.jacky.bundle_main.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zenchn.support.router.Router;
import com.zenchn.support.widget.TitleBar;
import com.zenchn.support.widget.dialog.PopupMaster;
import com.zenchn.support.widget.tips.SuperToast;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.R2;
import cn.jacky.bundle_main.di.component.AppComponent;
import cn.jacky.bundle_main.ui.adapter.BuyRecordAdapter;
import cn.jacky.bundle_main.ui.adapter.itemDecoration.TopBottomSpaceItemDecoration;
import cn.jacky.bundle_main.ui.baseview.BaseActivity;
import cn.jacky.bundle_main.util.ItemClickSupport;

/**
 * 购买记录
 */
public class BuyRecordActivity extends BaseActivity {

    @BindView(R2.id.rlv_bingo_record)
    RecyclerView rlvBingoRecord;
    @BindView(R2.id.swipe_refresh_bingo_record)
    SmartRefreshLayout swipeRefreshBingoRecord;
    @BindView(R2.id.ll_root)
    LinearLayout llRoot;
    @BindView(R2.id.title_bar)
    TitleBar mTitleBar;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private EmptyWrapper emptyWrapper;
    private List<String> datas = new ArrayList<>();
    private TextView tvTakeGoodAddress;
    private PopupMaster takePrizePop;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_bingo_record;
    }


    @Override
    public void initWidget() {
        mTitleBar.titleText(getString(R.string.buy_record))
                .setOnLeftClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rlvBingoRecord.setLayoutManager(linearLayoutManager);
        rlvBingoRecord.addItemDecoration(new TopBottomSpaceItemDecoration(this, 10f, true));
        BuyRecordAdapter buyRecordAdapter = new BuyRecordAdapter(datas);

        emptyWrapper = new EmptyWrapper(buyRecordAdapter);
        emptyWrapper.setEmptyView(R.layout.view_empty);
        rlvBingoRecord.setAdapter(emptyWrapper);

        swipeRefreshBingoRecord.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        datas.add(0, "新手2017-08-17");
                        emptyWrapper.notifyDataSetChanged();
                        if (null != swipeRefreshBingoRecord) {
                            swipeRefreshBingoRecord.finishRefresh();
                        }
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        datas.add("老司机2017-08-17");
                        emptyWrapper.notifyDataSetChanged();
                        if (null != swipeRefreshBingoRecord) {
                            swipeRefreshBingoRecord.finishLoadMore();
                        }
                    }
                }, 2000);
            }
        });

        //确认收货
        ItemClickSupport.addTo(rlvBingoRecord, R.id.btn_confirm_received)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        if (v.getId() == R.id.btn_confirm_received)
                            showTakePrizeDialog(position);
                    }
                });

        initData();
        initZhiHuAd();
    }

    /**
     * 知乎滑动广告效果
     */
    private void initZhiHuAd() {
        rlvBingoRecord.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = rlvBingoRecord.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;

                    int first = linearLayoutManager.findFirstCompletelyVisibleItemPosition(); //第一个完全显示的item
                    int last = linearLayoutManager.findLastCompletelyVisibleItemPosition(); //最后一个完全显示的item
                    int firstPosition = linearLayoutManager.findFirstVisibleItemPosition(); //第一个显示的item
                    int lastPosition = linearLayoutManager.findLastVisibleItemPosition(); //最后一个显示的item

                    //循环遍历当前屏幕中显示的所有item
                    for (int i = firstPosition; i <= lastPosition; i++) {
                        RecyclerView.ViewHolder viewHolder = rlvBingoRecord.findViewHolderForAdapterPosition(i);
                        //找出屏幕中的广告item
                        if (viewHolder instanceof BuyRecordAdapter.ZhiHuViewHolder) {
                            BuyRecordAdapter.ZhiHuViewHolder zhiHuHolder = (BuyRecordAdapter.ZhiHuViewHolder) viewHolder;
                            View itemView = zhiHuHolder.itemView;
                            //获取到广告item的位置 (item的顶部 与 recycleView顶部的距离)
                            int top = itemView.getTop();
                            //获取recycleView的高度
                            int height = rlvBingoRecord.getHeight();
                            //调用自定义imageView中的方法，实现图片的移动
                            zhiHuHolder.zhIv.setDy(top, height);
                        }
                    }
                }
            }
        });
    }


    /**
     * 确认收货弹窗
     *
     * @param position
     */
    private void showTakePrizeDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BuyRecordActivity.this);
        builder.setMessage("确认收货吗?")
                .setPositiveButton(getString(R.string.confirm_str), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO 确定收货,刷新列表
                        SuperToast.showDefaultMessage(BuyRecordActivity.this, "确认收货了" + position);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(true)
                .create()
                .show();

    }

    protected void initData() {
        for (int i = 0; i < 20; i++) {
            datas.add("中奖记录" + i);
        }
        emptyWrapper.notifyDataSetChanged();
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    public static void launch(Activity from) {
        Router
                .newInstance()
                .from(from)
                .to(BuyRecordActivity.class)
                .launch();
    }
}
