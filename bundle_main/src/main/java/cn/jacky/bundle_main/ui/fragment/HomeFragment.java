package cn.jacky.bundle_main.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zenchn.support.kit.AndroidKit;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import cn.jacky.bundle_main.Constants;
import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.R2;
import cn.jacky.bundle_main.di.component.AppComponent;
import cn.jacky.bundle_main.presenter.contract.HomeContract;
import cn.jacky.bundle_main.presenter.impl.HomePresenterImpl;
import cn.jacky.bundle_main.ui.activity.RaidDetailActivity;
import cn.jacky.bundle_main.ui.adapter.HomeHotGridAdaper;
import cn.jacky.bundle_main.ui.adapter.HomeMoreGridAdapter;
import cn.jacky.bundle_main.ui.baseview.BaseFragment;
import cn.jacky.bundle_main.util.StatusBarUtil;
import cn.jacky.bundle_main.widgets.AutoSizeTextView;
import cn.jacky.bundle_main.widgets.GridViewForScrollView;
import cn.jacky.bundle_main.widgets.MarqueeView;
import cn.jacky.bundle_main.widgets.ObservableScrollView;
import cn.jacky.bundle_main.wrapper.glide.BannerImageLoader;
import cn.jacky.bundle_main.wrapper.glide.GlideApp;

/**
 * 首页
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {
    @BindView(R2.id.fl_root)
    FrameLayout mRoot;
    @BindView(R2.id.scrollview_home)
    ObservableScrollView scrollView;
    @BindView(R2.id.banner_home)
    Banner bannerHome;
    @BindView(R2.id.marquee_home)
    MarqueeView marqueeHome;
    @BindView(R2.id.gv_hot_home)
    GridViewForScrollView gvHotHome;
    @BindView(R2.id.ll_home_quick_open)
    LinearLayout llHomeQuickOpen;
    @BindView(R2.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R2.id.gv_more_introduce_home)
    GridViewForScrollView gvMoreIntroduceHome;

    @BindColor(R2.color.mangoFour)
    int mangoColor;
    @BindColor(R2.color.dark)
    int darkColor;
    @BindView(R2.id.title_text)
    TextView mTitleText;

    private int bannerHeight = 0;

    //处理magicIndicator单独使用时的点击事件
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();

    private HomePresenterImpl homePresenter = new HomePresenterImpl(this);
    // 列表顶部和title底部重合时，列表的滑动距离。
    private float deltaY;

    //状态栏是否亮色字体
    private boolean mStatusbarWhiteFont = true;

    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initWidget() {
        mTitleText.setText("首页");

        mTitleText.setAlpha(0);
        StatusBarUtil.setSmartPadding(getActivity(), mTitleText);

        bannerHome.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bannerHeight = bannerHome.getHeight();
                bannerHome.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                scrollView.setScrollViewListener(scrollViewListener);
            }
        });
    }

    @Override
    protected void lazyLoad() {
        initData();
    }

    private ObservableScrollView.ScrollViewListener scrollViewListener = new ObservableScrollView.ScrollViewListener() {
        @Override
        public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
            float alpha;
            if (y <= 0) {
                alpha = 0;
                mStatusbarWhiteFont = true;
                initStatusBar();
            } else if (y <= bannerHeight) {
                alpha = (float) y / bannerHeight;
            } else {
                alpha = 1;
                mStatusbarWhiteFont = false;
                initStatusBar();
            }
            //设置标题透明度
            mTitleText.setAlpha(alpha);
        }
    };

    @Nullable
    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initStatusBar() {
        mImmersionBar = ImmersionBar.with(this);
        if (mStatusbarWhiteFont) {
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


    protected void initData() {
        final int windowWidth = AndroidKit.Dimens.getScreenWidth();

        //TODO 轮播图
        List<String> imgs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            imgs.add(Constants.imgUrls[i]);
        }
        bannerHome.setImages(imgs)
                .setImageLoader(new BannerImageLoader())
                .start();
        bannerHome.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });

        //TODO 公告
        homePresenter.getHomeNotice("2");

        //点击监听
        marqueeHome.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                showMessage("点击了" + position);
            }
        });

        //todo 热门拼团
        HomeHotGridAdaper homeHotAdaper = new HomeHotGridAdaper(getActivity());
        gvHotHome.setAdapter(homeHotAdaper);
        gvHotHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RaidDetailActivity.launch(getActivity(), false);
            }
        });

        //todo 快速拼团
        for (int i = 0; i < 16; i++) {
            View quickView = LayoutInflater.from(getActivity()).inflate(R.layout.item_home_quick_open, llHomeQuickOpen, false);
            ImageView iv_quick = (ImageView) quickView.findViewById(R.id.iv_home_quick_open);
            TextView tv_quick_name = (TextView) quickView.findViewById(R.id.tv_name_home_quick);
            AutoSizeTextView tv_quick_price = (AutoSizeTextView) quickView.findViewById(R.id.tv_price_home_quick);

            GlideApp.with(getActivity())
                    .load(Constants.imgUrls[i % 10])
                    .centerCrop()
                    .into(iv_quick);
            tv_quick_name.setText("小米笔记本" + i + "代");
            tv_quick_price.setText("¥" + 399 + i);

            final int finalI = i;
            quickView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RaidDetailActivity.launch(getActivity(), true);
                }
            });

            llHomeQuickOpen.addView(quickView);
        }

        //todo 更多推荐
        final List<String> mTitles = new ArrayList<>();
        mTitles.add("精选好货");
        mTitles.add("数码3C");
        mTitles.add("奢侈品");
        mTitles.add("土豪专区");
        mTitles.add("吃货专区嘿嘿");
        mTitles.add("精选好货");

//        mTitles.add("数码3C");
//        mTitles.add("奢侈品");
//        mTitles.add("土豪专区");
//        mTitles.add("吃货专区嘿嘿");


        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        //自适应模式,平分宽度
        commonNavigator.setAdjustMode(mTitles.size() < 6 ? true : false);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(darkColor);
                simplePagerTitleView.setSelectedColor(mangoColor);
                simplePagerTitleView.setText(mTitles.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFragmentContainerHelper.handlePageSelected(index, true);
                        showMessage("我要重新加载数据了" + mTitles.get(index));
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setLineHeight(AndroidKit.Dimens.dp2px(1));
                indicator.setColors(mangoColor);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);

        HomeMoreGridAdapter homeMoreAdapter = new HomeMoreGridAdapter(getActivity());
        gvMoreIntroduceHome.setAdapter(homeMoreAdapter);
        gvMoreIntroduceHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RaidDetailActivity.launch(getActivity(), false);
            }
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.frag_home;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != marqueeHome)
            marqueeHome.startFlipping();
        if (null != bannerHome)
            bannerHome.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != marqueeHome) {
            marqueeHome.stopFlipping();
        }
        if (null != bannerHome) {
            bannerHome.stopAutoPlay();
        }
    }

    //==========presenter 回调

    @Override
    public void showNotice(List<String> list) {
        // 在代码里设置自己的动画
        marqueeHome.startWithList(list, R.anim.anim_bottom_in, R.anim.anim_top_out);
    }
}
