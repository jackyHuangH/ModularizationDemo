package cn.jacky.bundle_main.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zenchn.support.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.R2;
import cn.jacky.bundle_main.bean.PCABean;
import cn.jacky.bundle_main.bean.TakeAddressBean;
import cn.jacky.bundle_main.di.component.AppComponent;
import cn.jacky.bundle_main.presenter.contract.MineContract;
import cn.jacky.bundle_main.presenter.impl.MinePresenterImpl;
import cn.jacky.bundle_main.ui.baseview.BaseActivity;
import cn.jacky.bundle_main.widgets.pickerview.popwindow.SinglePickerPopWin;

/**
 * 选择收货地址
 */
public class SetAddressActivity extends BaseActivity implements MineContract.View {

    private static final int COMPLETE_ANALYSIS_CITY = 1;
    private static final int COMPLETE_SELECT_PROVINCE = 2;
    @BindView(R2.id.tv_province)
    TextView tvProvince;
    @BindView(R2.id.ll_province)
    LinearLayout llProvince;
    @BindView(R2.id.tv_city)
    TextView tvCity;
    @BindView(R2.id.ll_city)
    LinearLayout llCity;
    @BindView(R2.id.et_address_detail)
    EditText etAddressDetail;
    @BindView(R2.id.ll_root)
    LinearLayout llRoot;
    @BindView(R2.id.title_bar)
    TitleBar mTitleBar;

    public static final String EXTRA_ADDRESS = "address";
    private List<String> provinces = new ArrayList<>();
    private List<String> cites = new ArrayList<>();
    private List<PCABean> pcaBeanList = new ArrayList<>();
    private String select_province = "";//选择的省份,不能为空
    private MinePresenterImpl minePresenter = new MinePresenterImpl(this);

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COMPLETE_ANALYSIS_CITY://解析城市完成
                {
                    if (null != provinces)
                        provinces.clear();
                    for (int i = 0; i < pcaBeanList.size(); i++) {
                        PCABean pcaBean = pcaBeanList.get(i);
                        String province = pcaBean.getName();
                        provinces.add(province);
                    }
                }
                break;
                case COMPLETE_SELECT_PROVINCE://选择省完成后,找到对应的城市
                {
                    if (null != cites)
                        cites.clear();
                    for (int i = 0; i < pcaBeanList.size(); i++) {
                        PCABean pcaBean = pcaBeanList.get(i);
                        String province = pcaBean.getName();
                        if (select_province.equals(province)) {
                            List<PCABean.CityBean> city = pcaBean.getCity();
                            for (int j = 0; j < city.size(); j++) {
                                String cityName = city.get(j).getName();
                                cites.add(cityName);
                            }
                        }
                    }
                }
                break;
            }
        }
    };

    @Override
    public int getLayoutRes() {
        return R.layout.activity_set_address;
    }

    @Override
    public void initWidget() {
        mTitleBar.titleText(getString(R.string.edit_address_str))
                .rightText(getString(R.string.confirm_str))
                .setOnRightClickListener(new TitleBar.OnRightClickListener() {
                    @Override
                    public void onRightViewClick(View v) {
                        //TODO 确认
                        if (checkDataEmpty()) {
                            String detail_address = etAddressDetail.getText().toString();
                            String province = tvProvince.getText().toString();
                            String city = tvCity.getText().toString();
                            Intent data = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(EXTRA_ADDRESS,
                                    new TakeAddressBean(province, city, detail_address));
                            data.putExtras(bundle);
                            setResult(RESULT_OK, data);
                            finish();
                        } else {
                            showMessage("请填写完整!");
                        }
                    }
                })
                .setOnLeftClickListener(this);

        initData();
    }

    protected void initData() {
        minePresenter.getPCAList();
    }


    private boolean checkDataEmpty() {
        String detail_address = etAddressDetail.getText().toString();
        String province = tvProvince.getText().toString();
        String city = tvCity.getText().toString();
        if (TextUtils.isEmpty(detail_address) || TextUtils.isEmpty(province)
                || TextUtils.isEmpty(city)) {
            return false;
        }
        return true;
    }

    @OnClick(R2.id.ll_province)
    public void onMLlProvinceClicked() {
        //省份
        showProvincePicker();
    }

    @OnClick(R2.id.ll_city)
    public void onMLlCityClicked() {
        //城市
        if (TextUtils.isEmpty(select_province)) {
            showMessage("先选择省份!");
        } else {
            showCityPicker();
        }
    }

    //选择省
    private void showProvincePicker() {
        SinglePickerPopWin province_popWin = new SinglePickerPopWin.Builder(SetAddressActivity.this,
                provinces, new SinglePickerPopWin.OnSinglePickListener() {
            @Override
            public void onSinglePickCompleted(String dataStr) {
                showMessage("选择了" + dataStr);
                select_province = dataStr;
                tvProvince.setText(dataStr);
                handler.sendEmptyMessage(COMPLETE_SELECT_PROVINCE);
            }
        }).build();
        province_popWin.showPopWin(SetAddressActivity.this);
    }

    //选择市
    private void showCityPicker() {
        SinglePickerPopWin province_popWin = new SinglePickerPopWin.Builder(SetAddressActivity.this,
                cites, new SinglePickerPopWin.OnSinglePickListener() {
            @Override
            public void onSinglePickCompleted(String dataStr) {
                showMessage("选择了" + dataStr);
                tvCity.setText(dataStr);
            }
        }).build();
        province_popWin.showPopWin(SetAddressActivity.this);
    }

    @Override
    public void onGetPCASuccess(List<PCABean> pcaList) {
        if (null != pcaBeanList) {
            pcaBeanList.clear();
        }
        pcaBeanList.addAll(pcaList);
        handler.sendEmptyMessage(COMPLETE_ANALYSIS_CITY);
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }
}
