package cn.jacky.bundle_main.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.zenchn.support.utils.TimeUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.R2;
import cn.jacky.bundle_main.di.component.AppComponent;
import cn.jacky.bundle_main.ui.activity.BuyRecordActivity;
import cn.jacky.bundle_main.ui.activity.GroupBuyRecordActivity;
import cn.jacky.bundle_main.ui.activity.LoginActivity;
import cn.jacky.bundle_main.ui.activity.MyShowOrderActivity;
import cn.jacky.bundle_main.ui.activity.SysMessageActivity;
import cn.jacky.bundle_main.ui.activity.TableActivity;
import cn.jacky.bundle_main.ui.activity.UserInfoActivity;
import cn.jacky.bundle_main.ui.activity.WebDetailActivity;
import cn.jacky.bundle_main.ui.baseview.BaseFragment;
import cn.jacky.bundle_main.wrapper.glide.CircleTransform;
import cn.jacky.bundle_main.wrapper.glide.GlideApp;

/**
 * 我的
 */

public class MineFragment extends BaseFragment {
    @BindView(R2.id.ll_root)
    LinearLayout llRoot;
    @BindView(R2.id.iv_user_head)
    ImageView ivUserHead;
    @BindView(R2.id.tv_user_name)
    TextView tvUserName;
    @BindView(R2.id.btn_message)
    TextView btnMessage;
    @BindView(R2.id.ll_my_show_order)
    LinearLayout llMyShowOrder;
    @BindView(R2.id.ll_my_raid_record)
    LinearLayout llMyRaidRecord;
    @BindView(R2.id.ll_my_prize_record)
    LinearLayout llMyPrizeRecord;
    @BindView(R2.id.ll_mine_introduce)
    LinearLayout llMineIntroduce;
    @BindView(R2.id.ll_raid_rules)
    LinearLayout llRaidRules;
    @BindView(R2.id.rl_mine_bg)
    RelativeLayout rlMineBg;//我的信息背景
    @BindView(R2.id.bt_go_login)
    Button mBtGoLogin;
    Unbinder unbinder;

    public static MineFragment getInstance() {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initStatusBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .transparentStatusBar()
                .statusBarDarkFont(false)
                .init();
    }

    @Override
    public void initWidget() {
        int hour = TimeUtils.getNowHour();
        if (hour > 6 && hour < 18) {
            //白天
            rlMineBg.setBackgroundResource(R.drawable.mine_bg_light);
        } else {
            //黑夜
            rlMineBg.setBackgroundResource(R.drawable.mine_bg_night);
        }

        tvUserName.setText("超级学校霸王");

        //加载圆形头像
        RequestBuilder<Bitmap> bmRequestBuilder = GlideApp.with(getActivity())
                .asBitmap()
                .load(R.drawable.bg_splash)
                .centerCrop()
                .placeholder(R.drawable.bg_splash);
        bmRequestBuilder.apply(new RequestOptions()
                .transform(new CircleTransform()))
                .into(ivUserHead);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.frag_mine;
    }


    @OnClick(R2.id.iv_user_head)
    public void onMIvUserHeadClicked() {
        //用户资料
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        startActivity(intent);
    }

    @OnClick(R2.id.btn_message)
    public void onMBtnMessageClicked() {
        //消息
        Intent intent = new Intent(getActivity(), SysMessageActivity.class);
        startActivity(intent);
    }

    @OnClick(R2.id.ll_my_show_order)
    public void onMLlMyShowOrderClicked() {
        //我的晒单
        MyShowOrderActivity.launch(getActivity());
    }

    @OnClick(R2.id.ll_my_raid_record)
    public void onMLlMyRaidRecordClicked() {
        //夺宝记录
        GroupBuyRecordActivity.launch(getActivity());
    }

    @OnClick(R2.id.ll_my_prize_record)
    public void onMLlMyPrizeRecordClicked() {
        //中奖记录
        BuyRecordActivity.launch(getActivity());
    }

    @OnClick(R2.id.ll_mine_introduce)
    public void onMLlMineIntroduceClicked() {
        //表格
        TableActivity.launch(getActivity());
    }

    @OnClick(R2.id.ll_raid_rules)
    public void onMLlRaidRulesClicked() {
        //夺宝规则
        Intent intent = new Intent(getActivity(), WebDetailActivity.class);
        intent.putExtra("title", getString(R.string.group_buy_rule_str));
        startActivity(intent);
    }

    @OnClick(R2.id.bt_go_login)
    public void onLoginClicked() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Nullable
    @Override
    protected void componentInject(AppComponent appComponent) {

    }

}
