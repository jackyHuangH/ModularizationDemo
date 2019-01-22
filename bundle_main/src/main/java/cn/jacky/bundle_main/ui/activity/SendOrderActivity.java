package cn.jacky.bundle_main.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zenchn.support.kit.AndroidKit;
import com.zenchn.support.router.Router;
import com.zenchn.support.widget.TitleBar;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jacky.bundle_main.Constants;
import cn.jacky.bundle_main.R;
import cn.jacky.bundle_main.R2;
import cn.jacky.bundle_main.di.component.AppComponent;
import cn.jacky.bundle_main.ui.adapter.AddPicAdapter;
import cn.jacky.bundle_main.ui.adapter.itemDecoration.GridSpaceItemDecoration;
import cn.jacky.bundle_main.ui.baseview.BaseActivity;
import cn.jacky.bundle_main.ui.fragment.PromptDialogFragment;
import cn.jacky.bundle_main.widgets.wechatcicleimage.entity.PhotoInfo;

/**
 * 晒一晒
 */

public class SendOrderActivity extends BaseActivity {

    @BindView(R2.id.et_send_content)
    EditText etSendContent;
    @BindView(R2.id.rlv_add_pic)
    RecyclerView rlvAddPic;
    @BindView(R2.id.bt_send_now)
    Button btSendNow;
    @BindView(R2.id.ll_root)
    LinearLayout llRoot;
    @BindView(R2.id.title_bar)
    TitleBar mTitleBar;

    public static final int IMAGE_PICKER = 0x100;
    public static final int IMAGE_PREVIEW = 0x101;
    private List<PhotoInfo> addPicList = new ArrayList<>();//选择的要上传的图片集合
    private AddPicAdapter addPicAdapter;
    private int selected_img_num = 0;//当前已选图片的数量

    @Override
    public int getLayoutRes() {
        return R.layout.activity_show_order;
    }

    @Override
    public void initWidget() {
        mTitleBar.titleText(getString(R.string.show_something))
                .setOnLeftClickListener(this);

        //添加图片列表
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rlvAddPic.setLayoutManager(gridLayoutManager);
        int space = AndroidKit.Dimens.dp2px(7);
        rlvAddPic.addItemDecoration(new GridSpaceItemDecoration(3, addPicList.size(), space));

        int windowWidth = AndroidKit.Dimens.getScreenWidth();
        addPicAdapter = new AddPicAdapter(this, R.layout.item_plus_img, windowWidth, addPicList);
        rlvAddPic.setAdapter(addPicAdapter);
        //添加默认的加号至末尾
        addPicList.add(new PhotoInfo(Constants.NO_PIC));
        addPicAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String s = addPicList.get(position).url;
                if (s.equals(Constants.NO_PIC)) {
                    //选取图片

                } else {
                    // 预览图片
                    Intent intent = new Intent(SendOrderActivity.this, ShowPictureActivity.class);
                    ArrayList<String> imgInfo = new ArrayList<>();
                    for (int i = 0; i < addPicList.size(); i++) {
                        String bigImageUrl = addPicList.get(i).url;
                        if (!bigImageUrl.equals(Constants.NO_PIC)) {
                            imgInfo.add(addPicList.get(i).url);
                        }
                    }
                    intent.putStringArrayListExtra(ShowPictureActivity.IMAGE_URLS, imgInfo);
                    intent.putExtra(ShowPictureActivity.CURRENT_POSITION, position);
                    startActivityForResult(intent, IMAGE_PREVIEW);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


    }

    @OnClick(R2.id.bt_send_now)
    public void onSubmitClicked(View view) {
        showPromptDialog("加载中。。。");
    }


    private void showPromptDialog(final String text) {
        PromptDialogFragment promptDialog = new PromptDialogFragment()
                .setContent(text);
        promptDialog.show(getSupportFragmentManager(), text);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICKER) {//选择图片

        } else if (requestCode == IMAGE_PREVIEW) {//预览图片删除后的结果

        }
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    public static void launch(Activity from) {
        Router
                .newInstance()
                .from(from)
                .to(SendOrderActivity.class)
                .launch();
    }
}
