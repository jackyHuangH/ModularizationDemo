package cn.jacky.bundle_main.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.zenchn.support.kit.AndroidKit;
import com.zenchn.support.widget.TitleBar;
import com.zenchn.support.widget.dialog.PopupMaster;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.R2;
import cn.jacky.bundle_main.bean.TakeAddressBean;
import cn.jacky.bundle_main.di.component.AppComponent;
import cn.jacky.bundle_main.ui.baseview.BaseActivity;
import cn.jacky.bundle_main.wrapper.glide.CircleTransform;
import cn.jacky.bundle_main.wrapper.glide.GlideApp;

/**
 * 我的资料
 */
public class UserInfoActivity extends BaseActivity {

    @BindView(R2.id.iv_user_head)
    ImageView ivUserHead;
    @BindView(R2.id.ll_change_head)
    LinearLayout llChangeHead;
    @BindView(R2.id.tv_nickname)
    TextView tvNickname;
    @BindView(R2.id.ll_edit_nickname)
    LinearLayout llEditNickname;
    @BindView(R2.id.tv_info_address)
    TextView tvInfoAddress;
    @BindView(R2.id.ll_edit_address)
    LinearLayout llEditAddress;
    @BindView(R2.id.ll_root)
    LinearLayout llRoot;
    @BindView(R2.id.title_bar)
    TitleBar mTitleBar;

    public static final int IMAGE_PICKER = 0x01;
    public static final int SET_ADDRESS = 0x02;
    private String user_head_path = "";
    private EditText et_Nickname;
    private PopupMaster nickPop;


    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    public int getLayoutRes() {
        return R.layout.activity_user_info;
    }


    @Override
    public void initWidget() {
        mTitleBar.titleText(getString(R.string.my_info_str))
                .setOnLeftClickListener(this);

        RequestBuilder<Bitmap> requestBuilder = GlideApp.with(UserInfoActivity.this)
                .asBitmap()
                .load(R.drawable.default_no_pic)
                .placeholder(R.drawable.default_no_pic)
                .centerCrop();
        requestBuilder.apply(new RequestOptions()
                .transform(new CircleTransform()))
                .into(ivUserHead);

    }

    @OnClick({R2.id.ll_change_head})
    public void onHeadClicked(View view) {
        //更换头像
    }

    @OnClick({R2.id.ll_edit_nickname})
    public void onNickNameClicked(View view) {
        //编辑昵称
        showNicknameDialog();
    }

    @OnClick(R2.id.ll_edit_address)
    public void onAddressClicked(View view) {
        //编辑收货地址
        Intent intent = new Intent(UserInfoActivity.this, SetAddressActivity.class);
        startActivityForResult(intent, SET_ADDRESS);
    }

    /**
     * 编辑昵称
     */
    private void showNicknameDialog() {
        if (null != nickPop) {
            nickPop.show(llRoot);
            nickPop.backgroundAlpha(UserInfoActivity.this, 0.6f);
            AndroidKit.Keyboard.showSoftInput(UserInfoActivity.this);
        } else {
            nickPop = new PopupMaster.Builder()
                    .setContext(UserInfoActivity.this)
                    .setHandler(handler)
                    .setLayout(R.layout.pop_nickname)
                    .setGravity(Gravity.CENTER)
                    .setFocusable(true)
                    .setLayoutInit(new PopupMaster.Builder.WindowLayoutInit() {
                        @Override
                        public void OnWindowLayoutInit(View rootView) {
                            et_Nickname = (EditText) rootView.findViewById(R.id.et_input_nick);

                            //获取焦点
                            et_Nickname.setFocusable(true);
                            et_Nickname.setFocusableInTouchMode(true);
                            et_Nickname.requestFocus();
                        }
                    })
                    .setItemClickListener(R.id.btn_confirm, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String inputNick = et_Nickname.getText().toString();
                            if (TextUtils.isEmpty(inputNick)) {
                                showMessage("昵称不能为空");
                            } else {
                                tvNickname.setText(inputNick);
                                nickPop.close();
                            }
                        }
                    })
                    .setItemClickListener(R.id.btn_cancel, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nickPop.close();
                        }
                    })
                    .setDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            nickPop.backgroundAlpha(UserInfoActivity.this, 1.0f);
                            AndroidKit.Keyboard.hideSoftInput(UserInfoActivity.this);
                        }
                    })
                    .create();
            nickPop.show(llRoot);
            nickPop.backgroundAlpha(UserInfoActivity.this, 0.6f);
            AndroidKit.Keyboard.showSoftInput(UserInfoActivity.this);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICKER) {

        } else if (requestCode == SET_ADDRESS) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    TakeAddressBean takeAddressBean = (TakeAddressBean) data.getSerializableExtra(SetAddressActivity.EXTRA_ADDRESS);
                    if (null != takeAddressBean) {
                        String address = takeAddressBean.getProvince() + takeAddressBean.getCity() +
                                takeAddressBean.getAddress();
                        tvInfoAddress.setText(address);
                    }
                }
            }
        }
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }
}
