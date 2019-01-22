package cn.jacky.bundle_main.ui.activity;

import android.accessibilityservice.AccessibilityService;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zenchn.support.router.Router;
import com.zenchn.support.widget.TitleBar;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jacky.bundle_main.Constants;
import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.R2;
import cn.jacky.bundle_main.di.component.AppComponent;
import cn.jacky.bundle_main.ui.baseview.BaseActivity;
import cn.jacky.bundle_main.widgets.test.SquareImageView;
import cn.jacky.bundle_main.wrapper.glide.GlideApp;
import cn.jacky.bundle_main.wrapper.glide.glideprogress.ProgressInterceptor;
import cn.jacky.bundle_main.wrapper.glide.glideprogress.ProgressListener;

/**
 * 登录界面
 */
@Route(path = "/test/loginactivity",name = "测试过滤URL跳转")
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    @BindView(R2.id.et_phone)
    EditText etPhone;
    @BindView(R2.id.et_secure_code)
    EditText etSecureCode;
    @BindView(R2.id.btn_get_code)
    Button btnGetCode;
    @BindView(R2.id.bt_login_now)
    Button btLoginNow;
    @BindView(R2.id.bt_login_out)
    Button btLoginOut;
    @BindView(R2.id.iv_wb_login)
    ImageView ivWbLogin;
    @BindView(R2.id.iv_wx_login)
    ImageView ivWxLogin;
    @BindView(R2.id.iv_qq_login)
    ImageView ivQqLogin;
    @BindView(R2.id.ll_root)
    LinearLayout llRoot;
    @BindView(R2.id.tvLayout_phone)
    TextInputLayout tvlayout_phone;
    @BindView(R2.id.tvLayout_code)
    TextInputLayout tvlayout_code;
    @BindView(R2.id.ll_share)
    LinearLayout mLlShare;
    @BindView(R2.id.sqiv)
    SquareImageView mSquareImageView;
    @BindColor(R2.color.pastelOrange)
    int orangeColor;
    @BindColor(R2.color.greyishFour)
    int greyColor;
    @BindView(R2.id.title_bar)
    TitleBar mTitleBar;

    private MyCountDownTimer myCountDownTimer;
    private ObjectAnimator mRotateAnimator;
    private ViewPropertyAnimator mViewPropertyAnimator;
    private String mImgUrl;
    private ProgressDialog progressDialog;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    public void initWidget() {
        mTitleBar.titleText(getString(R.string.login_str))
                .setOnLeftClickListener(this);

        //view补间动画
        // false代表里面的子animation不共用一个插值器
        AnimationSet animationSet = new AnimationSet(false);
        // 从alpha 完全透明变为完全不透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setFillAfter(true);
        // 运行完成后是否回到开始时的状态
        alphaAnimation.setFillBefore(false);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        // 重复的次数，infinite代表永久循环
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        // 重复的模式， restart代表重新开始，reverse代表反转
        alphaAnimation.setRepeatMode(Animation.RESTART);
        // 给动画设置对应的监听，可以在动画开始、结束或重复执行时做对应操作
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                //开始时候回调
                Log.d(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //重复执行时回调
                Log.d(TAG, "onAnimationRepeat: ");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //一轮结束时回调
                Log.d(TAG, "onAnimationEnd: ");
            }
        });
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360);
        rotateAnimation.setDuration(500);

        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
//        btLoginNow.startAnimation(animationSet);

        /**
         * ---------------------------------------------------------
         */

        //property animation属性动画，activity结束时要及时移除动画监听，释放资源
        /**
         * viewPropertyAnimator会自动执行
         * 不支持重复
         */
        btLoginNow.setScaleX(0);
        btLoginNow.setScaleY(0);
        btLoginNow.setAlpha(0);
        mViewPropertyAnimator = btLoginNow.animate();
        mViewPropertyAnimator
                .scaleY(1F)
                .scaleX(1F)
                .alpha(1)
                .rotationX(360)
                .setInterpolator(new BounceInterpolator())
                .setDuration(2000);

        /**
         * 用PropertyValuesHolder 来批量添加动画，
         * 类似于上面viewPropertyAnimator的用法
         */
//        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0, 1f);
//        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0, 1F);
//        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0, 1F);
//        PropertyValuesHolder rotationX = PropertyValuesHolder.ofFloat("rotationX", 0, 360);
//        ObjectAnimator propertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(btLoginOut, scaleX, scaleY, alpha, rotationX);
//        propertyValuesHolder.setDuration(2000);
//        propertyValuesHolder.setInterpolator(new BounceInterpolator());
//        propertyValuesHolder.start();


        /**
         * ObjectAnimator 自定义属性动画
         * 如果是自定义控件，需要添加 setter / getter 方法；
         *用 ObjectAnimator.ofXXX() 创建 ObjectAnimator 对象；
         *用 start() 方法执行动画
         *
         */
        mRotateAnimator = ObjectAnimator.ofFloat(btLoginOut, "rotationX", 0, 360);
        mRotateAnimator.setDuration(2000);
        mRotateAnimator.setInterpolator(new AnticipateOvershootInterpolator());
//        mRotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        mRotateAnimator.setRepeatMode(ValueAnimator.REVERSE);

        /**
         * 有了 AnimatorSet ，你就可以对多个 Animator 进行统一规划和管理，
         * 让它们按照要求的顺序来工作。它的使用比较简单
         */
        ObjectAnimator rotation = ObjectAnimator.ofFloat(btLoginOut, "rotation", 0, 360);
        rotation.setDuration(1000)
                .setInterpolator(new BounceInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(mRotateAnimator, rotation);

        animatorSet.start();

        initData();
    }

    protected void initData() {

        mImgUrl = Constants.imgUrls[7];

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        ProgressInterceptor.addListener(mImgUrl, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                progressDialog.setProgress(progress);
                progressDialog.show();
                Log.d(TAG, "onProgress: 当前进度：" + progress + "%");
            }
        });
    }

    /**
     * 监听glide加载进度
     */
    private void loadImage() {

        GlideApp.with(this)
                .load(mImgUrl)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(mSquareImageView);
    }


    @OnClick({R2.id.btn_get_code, R2.id.bt_login_now, R2.id.iv_wb_login, R2.id.iv_wx_login, R2.id.iv_qq_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R2.id.btn_get_code: {//获取验证码
                String inputPhone = etPhone.getText().toString();
                if (TextUtils.isEmpty(inputPhone)) {
                    showError(tvlayout_phone, "请输入手机号");
                } else {
                    myCountDownTimer = new MyCountDownTimer(60_000, 1_000);
                    myCountDownTimer.start();
                    tvlayout_phone.setErrorEnabled(false);
                }
            }
            break;
            case R2.id.bt_login_now: {//登录
                String inputCode = etSecureCode.getText().toString();
                if (TextUtils.isEmpty(inputCode)) {
                    showError(tvlayout_code, "请输入验证码");
                } else {
                    tvlayout_code.setErrorEnabled(false);
                    //todo  登录
                }
                doLogin();
                loadImage();
            }
            break;
            case R2.id.iv_wb_login: {//微博登录
            }
            break;
            case R2.id.iv_wx_login: {//微信登录
            }
            break;
            case R2.id.iv_qq_login: {//QQ登录
            }
            break;
            default:
                break;
        }
    }


    /**
     * 退出登录
     * 揭露效果
     */
    @OnClick(R2.id.bt_login_out)
    public void onLogOutClicked() {
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(btLoginOut, 0, 0, 0,
                (float) Math.hypot(btLoginOut.getWidth(), btLoginOut.getHeight()));
        circularReveal.start();
    }

    /**
     * ObjectAnimator 设置Evaluator 估值器
     */
    private void doLogin() {
        ObjectAnimator objectAnimator = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            objectAnimator = ObjectAnimator.ofArgb(mLlShare, "backgroundColor", 0xffff0000, 0xff00ff00);
        } else {
            objectAnimator = ObjectAnimator.ofInt(mLlShare, "backgroundColor", 0xffff0000, 0xff00ff00);
            objectAnimator.setEvaluator(new ArgbEvaluator());
        }
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }

    /**
     * 显示错误提示，并获取焦点
     *
     * @param textInputLayout
     * @param error
     */
    private void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

    /**
     * 获取验证码倒计时,避免重复点击
     */

    private class MyCountDownTimer extends CountDownTimer {

        /**
         * @param millisInFuture    倒计时总时长
         * @param countDownInterval 倒计时单位/s
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            btnGetCode.setClickable(false);
            btnGetCode.setText(l / 1000 + " s");
            btnGetCode.setTextColor(greyColor);
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            btnGetCode.setText("获取验证码");
            btnGetCode.setTextColor(orangeColor);
            //设置可点击
            btnGetCode.setClickable(true);
        }
    }


    class MyAccessibilityService extends AccessibilityService {

        @Override
        public void onAccessibilityEvent(AccessibilityEvent event) {

        }

        @Override
        public void onInterrupt() {

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewPropertyAnimator != null) {
            mViewPropertyAnimator.cancel();
        }
        if (mRotateAnimator != null) {
            mRotateAnimator.cancel();
        }

        if (null != myCountDownTimer) {
            myCountDownTimer.cancel();
        }
    }

    public static void launch(@NonNull Activity from) {
        Router
                .newInstance()
                .from(from)
                .to(LoginActivity.class)
                .launch();
    }

}
