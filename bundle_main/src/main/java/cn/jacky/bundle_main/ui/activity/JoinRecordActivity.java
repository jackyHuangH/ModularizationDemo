package cn.jacky.bundle_main.ui.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zenchn.support.router.Router;
import com.zenchn.support.widget.TitleBar;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.R2;
import cn.jacky.bundle_main.bean.JoinRecordBean;
import cn.jacky.bundle_main.di.component.AppComponent;
import cn.jacky.bundle_main.ui.adapter.JoinRecordAdapter;
import cn.jacky.bundle_main.ui.baseview.BaseActivity;

/**
 * 参与记录
 */

public class JoinRecordActivity extends BaseActivity {

    @BindView(R2.id.rlv_record)
    RecyclerView rlvRecord;
    @BindView(R2.id.swipe_refresh_join_record)
    SmartRefreshLayout swipeRefreshJoinRecord;
    @BindView(R2.id.ll_root)
    LinearLayout llRoot;
    @BindView(R2.id.title_bar)
    TitleBar mTitleBar;

    private int count = 0;
    private List<JoinRecordBean> listDatas = new ArrayList<>();
    private JoinRecordAdapter joinRecordAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public int getLayoutRes() {
        return R.layout.activity_join_record;
    }


    @Override
    public void initWidget() {
        mTitleBar.titleText("参与记录")
                .setOnLeftClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rlvRecord.setLayoutManager(linearLayoutManager);
        joinRecordAdapter = new JoinRecordAdapter(this, listDatas);
        final EmptyWrapper emptyWrapperAdapter = new EmptyWrapper(joinRecordAdapter);
        emptyWrapperAdapter.setEmptyView(R.layout.view_empty);
        rlvRecord.setAdapter(emptyWrapperAdapter);

        swipeRefreshJoinRecord.setHeaderHeight(40);
        swipeRefreshJoinRecord.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listDatas.add(0, new JoinRecordBean(1, "新手", "2017-08-28"));
                        emptyWrapperAdapter.notifyDataSetChanged();
                        if (null != swipeRefreshJoinRecord) {
                            swipeRefreshJoinRecord.finishRefresh();
                        }
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listDatas.add(new JoinRecordBean(1, "老司机了", "2017-08-14"));
                        emptyWrapperAdapter.notifyDataSetChanged();
                        if (null != swipeRefreshJoinRecord) {
                            swipeRefreshJoinRecord.finishLoadMore();
                        }
                    }
                }, 2000);
            }
        });

        initData();
    }

    protected void initData() {
        for (int i = 0; i < 30; i++) {
            JoinRecordBean joinRecordBean = new JoinRecordBean();
            joinRecordBean.setDate("2016-12-" + i);
            joinRecordBean.setType(i % 5 == 0 ? 0 : 1);
            joinRecordBean.setName("我是第" + i + "条数据哦");
            listDatas.add(joinRecordBean);
        }
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    public static void launch(Activity from) {
        Router
                .newInstance()
                .from(from)
                .to(JoinRecordActivity.class)
                .launch();
    }
}
