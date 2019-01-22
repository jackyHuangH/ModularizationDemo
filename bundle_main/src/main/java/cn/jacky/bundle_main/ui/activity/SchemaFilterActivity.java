package cn.jacky.bundle_main.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.jacky.bundle_main.R;


/**
 * @author HZJ
 * ARouter:新建一个Activity用于监听Schame事件,之后直接把url传递给ARouter即可
 * URL Schema使用场景大致分以下几种：
 * •服务器下发跳转路径，客户端根据服务器下发跳转路径跳转相应的页面
 * •H5页面点击锚点，根据锚点具体跳转路径APP端跳转具体的页面
 * •APP端收到服务器端下发的PUSH通知栏消息，根据消息的点击跳转路径跳转相关页面
 * •APP根据URL跳转到另外一个APP指定页面
 */
public class SchemaFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //通过URL跳转
        Uri uri = getIntent().getData();
        ARouter.getInstance().build(uri).navigation(this, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {
                finish();
            }
        });
    }
}
