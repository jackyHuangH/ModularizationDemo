package cn.jacky.bundle_main.model.impl.local;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zenchn.picbrowserlib.annotation.ImageSourceType;
import com.zenchn.picbrowserlib.pojo.ImageSourceInfo;
import com.zenchn.support.utils.UriUtils;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 作    者：wangr on 2017/9/11 16:33
 * 描    述：管理图片model
 * 修订记录：
 */
public class ImageSourceModelImpl {

    @NonNull
    public static Observable<File> getRealFileObservable(@NonNull ImageSourceInfo imageSourceInfo) {
        return Observable
                .just(imageSourceInfo)
                .map(new Function<ImageSourceInfo, File>() {
                    @Override
                    public File apply(ImageSourceInfo imageSourceInfo) {
                        File realFile = getRealFile(imageSourceInfo);
                        if (realFile == null || !realFile.exists() || !realFile.isFile()) {
                            Observable.error(new Throwable("file is not exist or is not a file"));
                        }
                        return realFile;
                    }
                });
    }

    /**
     * 根据图片信息 获取图片文件
     *
     * @param imageSourceInfo
     * @return
     */
    @Nullable
    public static File getRealFile(@NonNull ImageSourceInfo imageSourceInfo) {
        File realFile = null;
        int sourceType = imageSourceInfo.getSourceType();
        Object source = imageSourceInfo.getSource();
        if (source != null) {
            switch (sourceType) {
                case ImageSourceType.PATH:
                    realFile = new File((String) source);
                    break;
                case ImageSourceType.FILE:
                    realFile = (File) source;
                    break;
                case ImageSourceType.URI:
                    Context applicationContext = ContextModelImpl.getApplicationContext();
                    String realFilePath = UriUtils.getRealFilePath(applicationContext, (Uri) source);
                    if (realFilePath != null) {
                        realFile = new File(realFilePath);
                    }
                    break;
            }
        }
        return realFile;
    }
}