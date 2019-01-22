package cn.jacky.bundle_main.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zenchn.support.router.Router;
import com.zenchn.support.widget.TitleBar;
import com.zenchn.support.widget.tips.SuperToast;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jacky.bundle_main.Constants;
import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.R2;
import cn.jacky.bundle_main.bean.ShowOrderBean;
import cn.jacky.bundle_main.di.component.AppComponent;
import cn.jacky.bundle_main.ui.adapter.ShowOrderAdapter;
import cn.jacky.bundle_main.ui.baseview.BaseActivity;
import cn.jacky.bundle_main.util.ItemClickSupport;

/**
 * 我的晒单
 */
public class MyShowOrderActivity extends BaseActivity {

    @BindView(R2.id.rlv_my_order)
    RecyclerView rlvMyOrder;
    @BindView(R2.id.swipe_refresh_my_order)
    SmartRefreshLayout swipeRefreshMyOrder;
    @BindView(R2.id.ll_root)
    LinearLayout llRoot;
    @BindView(R2.id.title_bar)
    TitleBar mTitleBar;

    private List<ShowOrderBean> datas = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private EmptyWrapper emptyWrapper;

    @Override
    public void initWidget() {
        mTitleBar.titleText(getString(R.string.my_show_order))
                .setOnLeftClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rlvMyOrder.setLayoutManager(linearLayoutManager);
        ShowOrderAdapter showOrderAdapter = new ShowOrderAdapter(this, R.layout.item_list_show_order, datas, true);

        //todo 列表点赞,删除
        ItemClickSupport.addTo(rlvMyOrder, R.id.ll_order_praise, R.id.bt_delete_show_order, R.id.ll_item_show_order)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        if (v.getId() == R.id.ll_order_praise) {
                            SuperToast.showDefaultMessage(MyShowOrderActivity.this, "我的晒单点赞了" + position);
                        } else if (v.getId() == R.id.ll_item_show_order) {
                            //跳转详情
                            Intent intent = new Intent(MyShowOrderActivity.this, ShowOrderDetailActivity.class);
                            intent.putExtra(ShowOrderDetailActivity.CAN_DELETE_EXTRA, true);
                            startActivity(intent);
                        } else if (v.getId() == R.id.bt_delete_show_order) {
                            SuperToast.showDefaultMessage(MyShowOrderActivity.this, "我的晒单删除了" + position);
                        }
                    }
                });


        emptyWrapper = new EmptyWrapper(showOrderAdapter);
        emptyWrapper.setEmptyView(R.layout.view_empty);
        rlvMyOrder.setAdapter(emptyWrapper);

        swipeRefreshMyOrder.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ShowOrderBean model4 = new ShowOrderBean();
                        for (int i = 0; i < 1; i++) {
                            model4.urlList.add(Constants.imgUrls[4]);
                        }
                        model4.isShowAll = false;
                        model4.setHasImg(true);
                        datas.add(0, model4);
                        emptyWrapper.notifyDataSetChanged();
                        if (null != swipeRefreshMyOrder) {
                            swipeRefreshMyOrder.finishRefresh();
                        }
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ShowOrderBean model4 = new ShowOrderBean();
                        for (int i = 0; i < 1; i++) {
                            model4.urlList.add(Constants.imgUrls[5]);
                        }
                        model4.isShowAll = false;
                        model4.setHasImg(true);
                        datas.add(model4);
                        emptyWrapper.notifyDataSetChanged();
                        if (null != swipeRefreshMyOrder) {
                            swipeRefreshMyOrder.finishLoadMore();
                        }
                    }
                }, 2000);
            }
        });

        initData();
    }

    protected void initData() {

        //一张图片
        ShowOrderBean model4 = new ShowOrderBean();
        for (int i = 0; i < 1; i++) {
            model4.urlList.add(Constants.imgUrls[i]);
        }
        model4.isShowAll = false;
        model4.setHasImg(true);
        datas.add(model4);

        //五张图片
        ShowOrderBean model5 = new ShowOrderBean();
        for (int i = 0; i < 5; i++) {
            model5.urlList.add(Constants.imgUrls[i]);
        }
        model5.isShowAll = true;//显示全部图片
        model5.setHasImg(true);
        datas.add(model5);

        //9张图片
        ShowOrderBean model6 = new ShowOrderBean();
        for (int i = 0; i < 9; i++) {
            model6.urlList.add(Constants.imgUrls[i]);
        }
        model6.setHasImg(true);
        model6.isShowAll = true;
        datas.add(model6);

        //10张图片
        ShowOrderBean model7 = new ShowOrderBean();
        for (int i = 0; i < 10; i++) {
            model7.urlList.add(Constants.imgUrls[i]);
        }
        model7.setHasImg(true);
        model7.isShowAll = true;
        datas.add(model7);

        //没有图片
        ShowOrderBean model8 = new ShowOrderBean();
        model8.setHasImg(false);
        datas.add(model8);

        emptyWrapper.notifyDataSetChanged();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_my_show_order;
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    public static void launch(Activity from) {
        Router
                .newInstance()
                .from(from)
                .to(MyShowOrderActivity.class)
                .launch();
    }
}
