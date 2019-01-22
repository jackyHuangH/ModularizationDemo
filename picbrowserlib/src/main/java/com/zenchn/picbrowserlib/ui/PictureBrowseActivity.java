package com.zenchn.picbrowserlib.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zenchn.picbrowserlib.R;
import com.zenchn.picbrowserlib.adapter.BigImageBrowserAdapter;
import com.zenchn.picbrowserlib.pojo.ImageSourceInfo;
import com.zenchn.picbrowserlib.widget.BaseViewPager;

import java.util.ArrayList;


/**
 * 左右滑动显示图片
 */
public class PictureBrowseActivity extends Activity implements ViewPager.OnPageChangeListener {
    public static final String EXTRA_IMAGE_URLS = "EXTRA_IMAGE_URLS";
    public static final String EXTRA_CURRENT_POSITION = "EXTRA_CURRENT_POSITION";

    TextView tvIndex;
    BaseViewPager viewPager;
    FrameLayout mViewPagerLayout;

    private int currentPosition = 0; //当前位置
    private BigImageBrowserAdapter mViewPagerAdapter;
    private ArrayList<ImageSourceInfo> mImageSourceInfoList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_browse);
        initView();
        initWidget();
    }

    private void initView() {
        tvIndex = findViewById(R.id.tv_index);
        viewPager = findViewById(R.id.viewpager);
        mViewPagerLayout = findViewById(R.id.viewPagerLayout);
    }


    public void initWidget() {
        mImageSourceInfoList = getIntent().getParcelableArrayListExtra(EXTRA_IMAGE_URLS);
        currentPosition = getIntent().getIntExtra(EXTRA_CURRENT_POSITION, 0);

        mViewPagerAdapter = new BigImageBrowserAdapter(this, mImageSourceInfoList);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.setCurrentItem(currentPosition);
        mViewPagerAdapter.notifyDataSetChanged();
        tvIndex.setText((currentPosition + 1) + "/" + mImageSourceInfoList.size());

        mViewPagerAdapter.setOnImageClickListener(new BigImageBrowserAdapter.OnImageClickListener() {
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
        tvIndex.setText((position + 1) + "/" + mImageSourceInfoList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static void launch(@NonNull Activity from, @NonNull ArrayList<ImageSourceInfo> infoList, int currentPosition) {
        Intent intent = new Intent(from, PictureBrowseActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_IMAGE_URLS, infoList)
                .putExtra(EXTRA_CURRENT_POSITION, currentPosition);
        from.startActivity(intent);
    }
}
