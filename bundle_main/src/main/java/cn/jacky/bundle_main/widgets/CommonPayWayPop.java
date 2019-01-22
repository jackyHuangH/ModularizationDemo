package cn.jacky.bundle_main.widgets;

import android.animation.Animator;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zenchn.support.widget.dialog.DialogMaster;

import java.util.Locale;

import cn.jacky.bundle_main.R;

/**
 * Created by Hzj on 2017/8/24.
 * 公共选择支付方式弹窗,方便需要的界面调用
 * popupWIndow 会抢夺焦点,用dialog比较方便
 */

public class CommonPayWayPop {
    private double mPayMoney;//支付的金额
    private Activity mActivity;
    private View view;//依附的view
    private View popContainer;
    public static final int PAY_WAY_WECHAT = 0;
    public static final int PAY_WAY_ALI = 1;

    DialogMaster payWayDialog;
    private TextView tvToPayMoney;
    private CheckBox cb_wechat;
    private CheckBox cb_ali;
    private int pay_way = PAY_WAY_WECHAT;//支付方式,默认微信

    public static class Builder {
        private double payMoney = 0.00;//支付的金额
        private OnConfirmClickListener listener;

        private Activity context;
        private View v;

        public Builder(Activity ctx, View v) {
            this.context = ctx;
            this.v = v;
        }

        public Builder setPayMoney(double pay_money) {
            this.payMoney = pay_money;
            return this;
        }

        public Builder setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
            this.listener = onConfirmClickListener;
            return this;
        }

        public CommonPayWayPop create() {
            return new CommonPayWayPop(this);
        }
    }

    public CommonPayWayPop(Builder builder) {
        this.mPayMoney = builder.payMoney;

        this.mActivity = builder.context;
        this.view = builder.v;
        this.onConfirmClickListener = builder.listener;

        initViewAndShow();
    }

    private void initViewAndShow() {
        if (null == payWayDialog) {
            payWayDialog = new DialogMaster.Builder()
                    .setContext(mActivity)
                    .setCancelabe(false)
                    .setCanceledOnTouchOutside(false)
                    .setLayout(R.layout.pop_pay_way)
                    .setLayoutInit(new DialogMaster.Builder.WindowLayoutInit() {
                        @Override
                        public void OnWindowLayoutInit(View rootView) {
                            //要支付的金额
                            tvToPayMoney = (TextView) rootView.findViewById(R.id.tv_to_pay_money);
                            //微信支付勾选框
                            cb_wechat = (CheckBox) rootView.findViewById(R.id.cb_wechat_pay);
                            //支付宝支付勾选框
                            cb_ali = (CheckBox) rootView.findViewById(R.id.cb_ali_pay);
                            popContainer = rootView.findViewById(R.id.ll_pay_pop_container);

                            tvToPayMoney.setText(String.format(Locale.CHINA, "支付：%1$.2f", mPayMoney));

                        }
                    })
                    .setGravity(Gravity.CENTER)
                    .setItemClickListener(R.id.ll_wechat_pay, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pay_way = PAY_WAY_WECHAT;
                            cb_wechat.setChecked(true);
                            cb_ali.setChecked(false);
                        }
                    }).setItemClickListener(R.id.ll_ali_pay, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pay_way = PAY_WAY_ALI;
                            cb_ali.setChecked(true);
                            cb_wechat.setChecked(false);
                        }
                    })
                    .setItemClickListener(R.id.cb_wechat_pay, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cb_wechat.setChecked(true);
                            pay_way = PAY_WAY_WECHAT;
                            cb_ali.setChecked(false);
                        }
                    })
                    .setItemClickListener(R.id.cb_ali_pay, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cb_wechat.setChecked(false);
                            pay_way = PAY_WAY_ALI;
                            cb_ali.setChecked(true);
                        }
                    })
                    .setItemClickListener(R.id.btn_go_pay, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (null != onConfirmClickListener) {
                                onConfirmClickListener.onPayClick(pay_way);
                            }
                            dismissPopWin();
                        }
                    })
                    .setItemClickListener(R.id.ibt_close, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismissPopWin();
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
            final TranslateAnimation trans = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 1,
                    Animation.RELATIVE_TO_SELF, 0);

            ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 1f);
            scaleAnimation.setDuration(400);
            scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

            trans.setDuration(400);
            trans.setInterpolator(new AccelerateDecelerateInterpolator());

            payWayDialog.show();
            if (null != popContainer) {
//                popContainer.startAnimation(scaleAnimation);

                popContainer.post(new Runnable() {
                    @Override
                    public void run() {
                        Animator circularReveal = ViewAnimationUtils.createCircularReveal(popContainer,
                                (int) (popContainer.getWidth() * 0.5F),
                                popContainer.getHeight(),
                                0,
                                (float) Math.hypot(popContainer.getWidth(), popContainer.getHeight()));
                        circularReveal.setDuration(1000);
                        circularReveal.setInterpolator(new AnticipateOvershootInterpolator());
                        circularReveal.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                TranslateAnimation translateAnimation = new TranslateAnimation(
                                        Animation.RELATIVE_TO_SELF, 0,
                                        Animation.RELATIVE_TO_SELF, 0,
                                        Animation.RELATIVE_TO_SELF, -1,
                                        Animation.RELATIVE_TO_SELF, 0);
                                translateAnimation.setDuration(1000);
                                translateAnimation.setInterpolator(new BounceInterpolator());
                                cb_ali.startAnimation(translateAnimation);
                                cb_wechat.startAnimation(translateAnimation);
                                tvToPayMoney.startAnimation(translateAnimation);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        circularReveal.start();
                    }
                });

            }

        }
    }

    /**
     * Dismiss popWindow
     */
    public void dismissPopWin() {

        TranslateAnimation trans = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1);

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
                payWayDialog.dismiss();
            }
        });


        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation.setDuration(400);
        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                payWayDialog.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (null != popContainer) {
//            popContainer.startAnimation(scaleAnimation);

            popContainer.post(new Runnable() {
                @Override
                public void run() {
                    Animator circularReveal = ViewAnimationUtils.createCircularReveal(popContainer,
                            (int) (popContainer.getWidth() * 0.5F),
                            popContainer.getHeight(),
                            (float) Math.hypot(popContainer.getWidth(), popContainer.getHeight()),
                            0);
                    circularReveal.setDuration(500);
                    circularReveal.setInterpolator(new AccelerateInterpolator());
                    circularReveal.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            payWayDialog.dismiss();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    circularReveal.start();
                }
            });
        }
    }

    /**
     * 对外提供 确认(立即支付) 按钮的点击事件
     */
    public interface OnConfirmClickListener {
        void onPayClick(int pay_way);
    }

    private OnConfirmClickListener onConfirmClickListener;
}
