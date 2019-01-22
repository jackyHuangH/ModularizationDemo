package cn.jacky.bundle_main.ui.activity;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.zenchn.picbrowserlib.widget.BaseViewPager;

import java.util.List;

import butterknife.BindView;
import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.R2;
import cn.jacky.bundle_main.di.component.AppComponent;
import cn.jacky.bundle_main.ui.adapter.BigImageAdapter2;
import cn.jacky.bundle_main.ui.baseview.BaseActivity;
import cn.jacky.bundle_main.widgets.viewpager.NoPreloadViewPager;


/**
 * 左右滑动显示图片
 */
public class ShowPictureActivity extends BaseActivity implements NoPreloadViewPager.OnPageChangeListener, ViewPager.OnPageChangeListener {
    public static final String IMAGE_URLS = "imageUrls";
    public static final String CURRENT_POSITION = "currentPosition";

    @BindView(R2.id.tv_index)
    TextView tvIndex;
    @BindView(R2.id.viewpager)
    BaseViewPager viewPager;
    @BindView(R2.id.viewPagerLayout)
    FrameLayout mViewPagerLayout;

    private int currentPosition = 0; //当前位置
    private BigImageAdapter2 mViewPagerAdapter;
    private List<String> imageUrls;

    @SuppressLint("NewApi")
    private void initialize() {
        if (getIntent() != null) {
            imageUrls = getIntent().getStringArrayListExtra(IMAGE_URLS);
            currentPosition = getIntent().getIntExtra(CURRENT_POSITION, 0);
        }

        mViewPagerAdapter = new BigImageAdapter2(this, imageUrls);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.setCurrentItem(currentPosition);
        mViewPagerAdapter.notifyDataSetChanged();
        tvIndex.setText((currentPosition + 1) + "/" + imageUrls.size());

        mViewPagerAdapter.setOnImageClickListener(new BigImageAdapter2.OnImageClickListener() {
            @Override
            public void onImageClick() {
                onBackPressed();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tvIndex.setText((position + 1) + "/" + imageUrls.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void initWidget() {
        initialize();
    }


    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initStatusBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .fitsSystemWindows(false)
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR);
        mImmersionBar.init();
    }

    @Override
    protected void onDestroy() {
        if (mViewPagerAdapter != null) {
            mViewPagerAdapter.destroy();
        }
        super.onDestroy();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_show_pic;
    }
}
