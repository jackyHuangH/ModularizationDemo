package cn.jacky.bundle_main.ui.activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.youth.banner.Banner;
import com.zenchn.support.router.Router;
import com.zenchn.support.widget.dialog.PopupMaster;
import com.zenchn.support.widget.tips.SuperToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jacky.bundle_main.Constants;
import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.R2;
import cn.jacky.bundle_main.bean.MessageEntity;
import cn.jacky.bundle_main.di.component.AppComponent;
import cn.jacky.bundle_main.ui.adapter.SysMessageAdapter;
import cn.jacky.bundle_main.ui.adapter.itemDecoration.TopBottomSpaceItemDecoration;
import cn.jacky.bundle_main.ui.baseview.BaseActivity;
import cn.jacky.bundle_main.util.StatusBarUtil;
import cn.jacky.bundle_main.widgets.BottomSheetDialog.BottomSheetDialogListView;
import cn.jacky.bundle_main.widgets.BottomSheetDialog.BottomSheetRecyclerView;
import cn.jacky.bundle_main.widgets.BottomSheetDialog.SpringBackBottomSheetDialog;
import cn.jacky.bundle_main.widgets.CommonSharePop;
import cn.jacky.bundle_main.widgets.ObservableScrollView;
import cn.jacky.bundle_main.wrapper.glide.BannerImageLoader;

/**
 * 主页夺宝详情
 */
public class RaidDetailActivity extends BaseActivity {

    public static final String EXTRA_HAS_START_GROUP = "has_start_group";
    @BindView(R2.id.banner_home_detail)
    Banner bannerHomeDetail;
    @BindView(R2.id.tv_home_detail_tip)
    TextView tvHomeDetailTip;
    @BindView(R2.id.tv_name_home_detail)
    TextView tvNameHomeDetail;
    @BindView(R2.id.tv_no_home_detail)
    TextView tvNoHomeDetail;
    @BindView(R2.id.pb_home_detail)
    ProgressBar pbHomeDetail;
    @BindView(R2.id.tv_origin_price)
    TextView tvOrginPrice;//原价
    @BindView(R2.id.tv_total_bet)
    TextView tvTotalBet;
    @BindView(R2.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R2.id.ll_good_detail)
    LinearLayout llGoodDetail;
    @BindView(R2.id.ll_pass_publish)
    LinearLayout llPassPublish;
    @BindView(R2.id.ll_raid_rule)
    LinearLayout llRaidRule;
    @BindView(R2.id.tv_detail_record_time)
    TextView tvDetailRecordTime;
    @BindView(R2.id.ll_join_record)
    LinearLayout llJoinRecord;
    @BindView(R2.id.bt_look_all_record)
    Button btLookAllRecord;
    @BindView(R2.id.bt_raid_now)
    Button btRaidNow;
    @BindView(R2.id.scrollview_home_detail)
    ObservableScrollView scrollviewHomeDetail;
    @BindView(R2.id.swipe_refresh_detail)
    SwipeRefreshLayout swipeRefreshDetail;

    @BindColor(R2.color.orangeRedTwo)
    int redTwo;
    @BindView(R2.id.title_text)
    TextView mtvTitle;
    @BindView(R2.id.ibt_back)
    ImageButton mIbtBack;
    @BindView(R2.id.ibt_right)
    ImageButton mIbtRight;

    private int bannerHeight = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private TextView tvBuyNum;//购买数量
    private TextView tvBuyLimit;//购买限制
    private TextView tvPaySumPrice;//购买总金额
    private PopupMaster raidPopMaster;
    private View mPopRootView;
    private int buy_num = 1;//购买数量,默认1
    private String buy_limit = "每位用户限购3份";//购买限制
    private double pay_sum_price = 0;//购买总价
    private double single_price = 10;//购买单价
    private boolean has_start_group = false;//是否已开团,
    private boolean mStatusBarWhiteFont = true;//状态栏是否亮色字体

    @OnClick(R2.id.ibt_back)
    public void onBackClicked() {
        onBackPressed();
    }

    @OnClick(R2.id.ibt_right)
    public void onShareClicked(View v) {
        // 分享
        CommonSharePop sharePop = new CommonSharePop.Builder(RaidDetailActivity.this, v, handler)
                .create();
        sharePop.showPopWin();
        //showBottomDialog();
    }


    @Override
    public void initWidget() {
        //初始透明度为0
        mtvTitle.setAlpha(0);
        StatusBarUtil.setSmartPadding(this, mtvTitle);

        bannerHomeDetail.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bannerHeight = bannerHomeDetail.getHeight();
                bannerHomeDetail.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                scrollviewHomeDetail.setScrollViewListener(scrollViewListener);
            }
        });

        //解决swiperefreshLayout 与ScrollView滑动冲突
        if (scrollviewHomeDetail != null) {
            scrollviewHomeDetail.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (swipeRefreshDetail != null) {
                        swipeRefreshDetail.setEnabled(scrollviewHomeDetail.getScrollY() == 0);
                    }
                }
            });
        }

        initRaidDialog();
        initData();
    }

    private void showBottomDialog() {
        SpringBackBottomSheetDialog bottomSheetDialog = new SpringBackBottomSheetDialog(this);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.content_bottomsheet_dialog, null, false);
        BottomSheetRecyclerView bottomSheetDialogListView = contentView.findViewById(R.id.listview);

        initListView(bottomSheetDialogListView);

        bottomSheetDialog.setContentView(contentView);
        bottomSheetDialogListView.bindBottomSheetDialog(contentView);
        bottomSheetDialog.addSpringBackDisLimit(-1);

        bottomSheetDialog.show();
    }

    private void initListView(final BottomSheetRecyclerView bottomSheetDialogListView) {
        final List<MessageEntity> datas = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.message = "hhhhhh" + i;
            messageEntity.imgUrl = Constants.imgUrls[i % 4];
            datas.add(messageEntity);
        }

        bottomSheetDialogListView.setLayoutManager(new LinearLayoutManager(this));
        bottomSheetDialogListView.addItemDecoration(new TopBottomSpaceItemDecoration(this, 10f, true));
        SysMessageAdapter sysMessageAdapter = new SysMessageAdapter(this, R.layout.item_list_sys_message, datas);
        sysMessageAdapter.setOnItemTouchCallback(new SysMessageAdapter.OnItemTouchCallback() {
            @Override
            public void onItemTouch() {
                bottomSheetDialogListView.setCoordinatorDisallow();
            }
        });

        bottomSheetDialogListView.setAdapter(sysMessageAdapter);

    }

    private void initListView(final BottomSheetDialogListView bottomSheetDialogListView) {
        final List<MessageEntity> datas = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.message = "hhhhhh" + i;
            messageEntity.imgUrl = Constants.imgUrls[i % 4];
            datas.add(messageEntity);
        }

        bottomSheetDialogListView.setAdapter(
                new ListAdapter() {
                    @Override
                    public boolean areAllItemsEnabled() {
                        return false;
                    }

                    @Override
                    public boolean isEnabled(int position) {
                        return false;
                    }

                    @Override
                    public void registerDataSetObserver(DataSetObserver observer) {

                    }

                    @Override
                    public void unregisterDataSetObserver(DataSetObserver observer) {

                    }

                    @Override
                    public int getCount() {
                        return datas.size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return datas.get(position);
                    }

                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public boolean hasStableIds() {
                        return false;
                    }

                    @Override
                    public View getView(final int position, View convertView, final ViewGroup parent) {
                        if (convertView == null) {
                            convertView = new TextView(parent.getContext());
                            convertView.setLayoutParams(
                                    new AbsListView.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            40 * 3 // 40dp
                                    )
                            );
                        }
                        TextView t = (TextView) convertView;
                        t.setTextColor(Color.BLACK);
                        t.setGravity(Gravity.CENTER);
                        t.setText(datas.get(position).message);
                        t.setTextSize(17);
                        t.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(parent.getContext(), "" + position, Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
                        t.setOnTouchListener(
                                new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                            bottomSheetDialogListView.setCoordinatorDisallow();
                                        }
                                        return false;
                                    }
                                }
                        );
                        return t;
                    }

                    @Override
                    public int getItemViewType(int position) {
                        return 0;
                    }

                    @Override
                    public int getViewTypeCount() {
                        return 1;
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }
                }
        );
    }

    /**
     * 初始化购买弹窗
     */
    private void initRaidDialog() {
        raidPopMaster = new PopupMaster.Builder()
                .setContext(RaidDetailActivity.this)
                .setHandler(handler)
                .setFocusable(true)
                .setLayout(R.layout.pop_raid)
                .setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setGravity(Gravity.BOTTOM)
                .setLayoutInit(new PopupMaster.Builder.WindowLayoutInit() {
                    @Override
                    public void OnWindowLayoutInit(View rootView) {
                        mPopRootView = rootView;
                        //初始化控件
                        tvBuyNum = (TextView) rootView.findViewById(R.id.tv_buy_num);
                        tvBuyLimit = (TextView) rootView.findViewById(R.id.tv_buy_limit);
                        tvPaySumPrice = (TextView) rootView.findViewById(R.id.tv_pay_sum_price);

                        tvBuyLimit.setText(String.format(Locale.CHINA, "%s", buy_limit));
                    }
                })
                .setItemClickListener(R.id.btn_minus, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //减
                        buy_num--;
                        if (buy_num < 1) {
                            buy_num = 1;
                            SuperToast.showDefaultMessage(RaidDetailActivity.this, "购买数量至少为1");
                        }
                        tvBuyNum.setText(String.format(Locale.CHINA, "%d", buy_num));
                        pay_sum_price = buy_num * single_price;
                        tvPaySumPrice.setText(String.format(Locale.CHINA, "%.2f", pay_sum_price));
                    }
                })
                .setItemClickListener(R.id.btn_add, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //加
                        buy_num++;
                        tvBuyNum.setText(String.format(Locale.CHINA, "%d", buy_num));
                        pay_sum_price = buy_num * single_price;
                        tvPaySumPrice.setText(String.format(Locale.CHINA, "%.2f", pay_sum_price));
                    }
                })
                .setItemClickListener(R.id.btn_pay_now, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo 立即支付
                        ConfirmOrderActivity.launch(RaidDetailActivity.this);
                        raidPopMaster.close();
                    }
                })
                .setItemClickListener(R.id.btn_turn_off, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        raidPopMaster.close();
                    }
                })
                .setDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        raidPopMaster.backgroundAlpha(RaidDetailActivity.this, 1.0f);
                    }
                })
                .create();
    }

    private ObservableScrollView.ScrollViewListener scrollViewListener = new ObservableScrollView.ScrollViewListener() {
        @Override
        public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
            float alpha;
            if (y <= 0) {
                alpha = 0;
                mStatusBarWhiteFont = true;
                mIbtBack.setImageResource(R.drawable.ic_back_white);
                mIbtRight.setImageResource(R.drawable.top_share_white);
                initStatusBar();
            } else if (y <= bannerHeight) {
                alpha = (float) y / (bannerHeight / 3);
                if (alpha >= 0.8F) {
                    mStatusBarWhiteFont = false;
                    mIbtBack.setImageResource(R.drawable.ic_back_black);
                    mIbtRight.setImageResource(R.drawable.top_share_black);
                    initStatusBar();
                }
            } else {
                alpha = 1;
            }
            //设置标题透明度
            mtvTitle.setAlpha(alpha);
        }
    };

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initStatusBar() {
        mImmersionBar = ImmersionBar.with(this);
        if (mStatusBarWhiteFont) {
            mImmersionBar
                    .transparentStatusBar()
                    .statusBarDarkFont(false);
        } else {
            mImmersionBar
                    .transparentStatusBar()
                    .statusBarDarkFont(true, 0.2f);
        }
        mImmersionBar.init();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_raid_detail;
    }

    protected void initData() {
        if (getIntent() != null) {
            Intent intentFrom = getIntent();
            if (intentFrom.hasExtra(EXTRA_HAS_START_GROUP)) {
                has_start_group = intentFrom.getBooleanExtra(EXTRA_HAS_START_GROUP, false);
            }
        }

        //TODO 已经开团
        if (has_start_group) {
            tvOrginPrice.setVisibility(View.VISIBLE);
            pbHomeDetail.setVisibility(View.GONE);

            tvOrginPrice.setText(String.format(Locale.CHINA, "原价¥%d", 1999));
            tvOrginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            tvTotalPrice.setTextSize(19);
            tvTotalPrice.setText(String.format(Locale.CHINA, "团购价¥%d", 1998));
            tvTotalPrice.setTextColor(redTwo);

            btRaidNow.setText("开团中,立即抢购");
            btRaidNow.setBackgroundResource(R.drawable.bg_bt_coral_four);

            //todo 只有预约过的用户才能点击购买,否则提示:您没有参与预约拼团，只能以原价购买该商品
        }


        //todo 下拉刷新
        swipeRefreshDetail.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SuperToast.showDefaultMessage(RaidDetailActivity.this, "我要重新获取数据啦!!!");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //然刷新控件停留两秒后消失
                            Thread.sleep(2000);
                            handler.post(new Runnable() {//在主线程执行
                                @Override
                                public void run() {
                                    //更新数据

                                    //停止刷新
                                    swipeRefreshDetail.setRefreshing(false);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        //TODO 轮播图
        List<String> imgs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            imgs.add(Constants.imgUrls[i]);
        }
        bannerHomeDetail.setImages(imgs)
                .setImageLoader(new BannerImageLoader())
                .start();

        //todo 参与记录
        for (int i = 0; i < 5; i++) {
            View recordView = LayoutInflater.from(RaidDetailActivity.this).inflate(R.layout.item_join_record, llJoinRecord, false);
            TextView tvRecordDate = (TextView) recordView.findViewById(R.id.tv_record_date);
            TextView tvRecordTime = (TextView) recordView.findViewById(R.id.tv_record_time);
            ImageView ivRing = (ImageView) recordView.findViewById(R.id.iv_ring_record);
            TextView tvRecordUserName = (TextView) recordView.findViewById(R.id.tv_record_user_name);
            TextView tvRecordIp = (TextView) recordView.findViewById(R.id.tv_record_user_ip);
            TextView tvRecordNum = (TextView) recordView.findViewById(R.id.tv_record_num);

            if (i % 2 == 0)
                ivRing.setImageResource(R.drawable.ring_blue);
            else
                ivRing.setImageResource(R.drawable.ring_green);

            llJoinRecord.addView(recordView);
        }

    }

    @OnClick(R2.id.ll_good_detail)
    public void onMLlGoodDetailClicked() {
        //商品详情
        Intent intent = new Intent(RaidDetailActivity.this, WebDetailActivity.class);
        intent.putExtra("title", getString(R.string.good_detail_str));
        startActivity(intent);

    }

    @OnClick(R2.id.ll_pass_publish)
    public void onMLlPassPublishClicked() {
        //往期揭晓
        PassAnnounceActivity.launch(this);
    }

    @OnClick(R2.id.ll_raid_rule)
    public void onMLlRaidRuleClicked() {
        //夺宝规则
        Intent intent = new Intent(RaidDetailActivity.this, WebDetailActivity.class);
        intent.putExtra("title", getString(R.string.group_buy_rule_str));
        startActivity(intent);
    }

    @OnClick(R2.id.bt_look_all_record)
    public void onMBtLookAllRecordClicked() {
        JoinRecordActivity.launch(this);
    }

    @OnClick(R2.id.bt_raid_now)
    public void onMBtRaidNowClicked() {
        //立即夺宝
        if (null != raidPopMaster) {
            raidPopMaster.show(swipeRefreshDetail);
            raidPopMaster.backgroundAlpha(RaidDetailActivity.this, 0.8f);
        }

        //揭露动画API21
        if (mPopRootView != null) {
            //控件的宽高还没有计算出来，调用view的post方法
            mPopRootView.post(new Runnable() {
                @Override
                public void run() {
                    Animator circularReveal = ViewAnimationUtils.createCircularReveal(mPopRootView,
                            (int) (mPopRootView.getWidth() * 0.5F),
                            mPopRootView.getHeight(),
                            0,
                            (float) Math.hypot(mPopRootView.getWidth(), mPopRootView.getHeight()));
                    circularReveal.setDuration(2000);
                    circularReveal.setInterpolator(new BounceInterpolator());
                    circularReveal.start();
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != bannerHomeDetail)
            bannerHomeDetail.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != bannerHomeDetail)
            bannerHomeDetail.stopAutoPlay();
    }

    public static void launch(Activity from, boolean hasStartGroup) {
        Router
                .newInstance()
                .from(from)
                .putBoolean(EXTRA_HAS_START_GROUP, hasStartGroup)
                .to(RaidDetailActivity.class)
                .launch();
    }

}
