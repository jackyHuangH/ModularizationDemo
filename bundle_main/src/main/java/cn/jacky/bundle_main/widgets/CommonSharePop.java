package cn.jacky.bundle_main.widgets;

import android.app.Activity;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;

import com.zenchn.support.widget.dialog.PopupMaster;
import com.zenchn.support.widget.tips.SuperToast;

import cn.jacky.bundle_main.R;


/**
 * Created by Hzj on 2017/8/24.
 * 公共分享弹窗,方便需要的界面调用
 */

public class CommonSharePop {
    private String mShareTitle;//分享的标题
    private String mShareContent;//分享的内容
    private String mShareImageUrl;//分享的图片地址
    private String mShareUrl;//分享的链接地址
    private Activity mActivity;
    private Handler mHandler;
    private View view;//依附的view
    private View popContainer;

    PopupMaster sharePop;

    public static class Builder {
        private String shareTitle = "";//分享的标题
        private String shareContent = "";//分享的内容
        private String shareImageUrl = "";//分享的图片地址
        private String shareUrl = "";//分享的链接地址

        private Activity context;
        private Handler handler;
        private View v;

        public Builder(Activity ctx, View v, Handler handler) {
            this.context = ctx;
            this.v = v;
            this.handler = handler;
        }

        public Builder setShareTitle(String title) {
            this.shareTitle = title;
            return this;
        }

        public Builder setShareContent(String content) {
            this.shareContent = content;
            return this;
        }

        public Builder setShareImageUrl(String imgUrl) {
            this.shareImageUrl = imgUrl;
            return this;
        }

        public Builder setShareUrl(String url) {
            this.shareUrl = url;
            return this;
        }

        public CommonSharePop create() {
            return new CommonSharePop(this);
        }
    }

    public CommonSharePop(Builder builder) {
        this.mShareTitle = builder.shareTitle;
        this.mShareContent = builder.shareContent;
        this.mShareImageUrl = builder.shareImageUrl;
        this.mShareUrl = builder.shareUrl;

        this.mActivity = builder.context;
        this.mHandler = builder.handler;
        this.view = builder.v;

        initViewAndShow();
    }

    private void initViewAndShow() {
        if (null == sharePop) {
            sharePop = new PopupMaster.Builder()
                    .setContext(mActivity)
                    .setHandler(mHandler)
                    .setFocusable(true)
                    .setLayout(R.layout.pop_share)
                    .setLayoutInit(new PopupMaster.Builder.WindowLayoutInit() {
                        @Override
                        public void OnWindowLayoutInit(View rootView) {
                            popContainer = rootView.findViewById(R.id.share_pop_container);
                        }
                    })
                    .setGravity(Gravity.BOTTOM)
                    .setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setItemClickListener(R.id.ll_share_weibo, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shareSina();
                        }
                    }).setItemClickListener(R.id.ll_share_qq, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shareQQ();
                        }
                    }).setItemClickListener(R.id.ll_share_wechat, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shareWXFriend();
                        }

                    }).setItemClickListener(R.id.ll_share_friend, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shareWXCircle();
                        }
                    })
                    .setItemClickListener(R.id.tv_share_cancel, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismissPopWin();
                        }
                    })
                    .setItemClickListener(R.id.view_share_out, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismissPopWin();
                        }
                    })
                    .setDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            sharePop.backgroundAlpha(mActivity, 1.0f);
                        }
                    })
                    .create();
        }
    }

    /**
     * Show popWindow
     */
    public void showPopWin() {

        if (null != mActivity) {
            TranslateAnimation trans = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                    0, Animation.RELATIVE_TO_SELF, 1,
                    Animation.RELATIVE_TO_SELF, 0);

            sharePop.show(view);
            sharePop.backgroundAlpha(mActivity, 0.7f);
            trans.setDuration(400);
            trans.setInterpolator(new AccelerateDecelerateInterpolator());

            if (null != popContainer)
                popContainer.startAnimation(trans);
        }
    }

    /**
     * Dismiss popWindow
     */
    public void dismissPopWin() {

        TranslateAnimation trans = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);

        trans.setDuration(400);
        trans.setInterpolator(new AccelerateInterpolator());
        trans.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                sharePop.close();
            }
        });
        if (null != popContainer)
            popContainer.startAnimation(trans);
    }


    /**
     * 分享微信好友
     */
    private void shareWXFriend() {
        SuperToast.showDefaultMessage(mActivity, "分享微信好友");
    }

    /**
     * 分享微信朋友圈
     */
    private void shareWXCircle() {
        SuperToast.showDefaultMessage(mActivity, "分享微信朋友圈");
    }

    /**
     * 分享QQ好友
     */
    private void shareQQ() {
        SuperToast.showDefaultMessage(mActivity, "分享QQ好友");
    }

    /**
     * 分享新浪微博
     */
    private void shareSina() {
        SuperToast.showDefaultMessage(mActivity, "分享新浪微博");
    }


}
