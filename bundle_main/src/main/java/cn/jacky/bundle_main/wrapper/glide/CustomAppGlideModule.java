package cn.jacky.bundle_main.wrapper.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import me.jessyan.progressmanager.ProgressManager;
import okhttp3.OkHttpClient;

/**
 *
 * @author Hzj
 * @date 2017/8/15
 * 为运用程序定义一个带有@GlideModule注解的AppGlideModule，运用程序会使用和AppGlideMoudle同一个包下的GlideApp类。
 * 通过GlideApp.with()方式使用Glide的Generated API。
 */

@GlideModule
public class CustomAppGlideModule extends AppGlideModule{

    /**
     * 为App注册一个自定义的String类型的BaseGlideUrlLoader
     *
     * @param context
     * @param registry
     */
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
//        //Glide 底层默认使用 HttpConnection 进行网络请求,这里替换为 Okhttp 后才能使用本框架,进行 Glide 的加载进度监听
//        //添加拦截器到Glide
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(new ProgressInterceptor());
//        OkHttpClient okHttpClient = builder.build();
//        //原来的是  new OkHttpUrlLoader.Factory()；
//        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));

        OkHttpClient okHttpClient2 = ProgressManager.getInstance().with(new OkHttpClient.Builder())
                .build();
        //原来的是  new OkHttpUrlLoader.Factory()；
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient2));
    }

    /**
     * 清单解析的开启
     *
     * 这里不开启，避免添加相同的modules两次
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
