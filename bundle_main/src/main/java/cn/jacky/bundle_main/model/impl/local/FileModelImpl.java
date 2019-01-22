package cn.jacky.bundle_main.model.impl.local;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.support.annotation.Nullable;

import com.zenchn.apilib.util.CodecKit;
import com.zenchn.apilib.util.GZIPUtils;
import com.zenchn.apilib.util.JavaKit;
import com.zenchn.support.kit.AndroidKit;
import com.zenchn.support.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import cn.jacky.bundle_main.Constants;
import cn.jacky.bundle_main.model.FileModel;
import cn.jacky.bundle_main.model.callback.CopyDBCallback;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 作    者： Huangzj on 2017/9/9/009.
 * 描    述：
 * 修订记录：
 */

public class FileModelImpl implements FileModel {

    public FileModelImpl() {
    }

    public static FileModelImpl getInstance() {
        return new FileModelImpl();
    }


    @Override
    public void copyDBToApp(@Nullable final CopyDBCallback callback) {
        ContextModelImpl.getContextObservable()
                .map(new Function<Application, Boolean>() {
                    @Override
                    public Boolean apply(Application application) {
                        String packageName = application.getPackageName();
                        String DB_DIR = "/data/data/" + packageName + "/databases/";
                        File dbFile = new File(DB_DIR, Constants.AREA_DB_NAME);
                        //文件已存在就不用再复制，这里加上版本号判断条件
                        PackageInfo packageInfo = AndroidKit.Package.getPackageInfo(application);
                        int currentVersion = packageInfo.versionCode;
                        int preVersion = ACacheModelImpl.getAppVersionCode();
                        if (currentVersion == preVersion && JavaKit.File.isFileExist(dbFile)) {
                            String encrypt = CodecKit.MD5.encrypt(dbFile);
                            String areaDbMD5Code = ACacheModelImpl.getAreaDbMD5Code();
                            if (StringUtils.equals(encrypt, areaDbMD5Code)) {
                                return true;
                            }
                        }
                        InputStream open = null;
                        try {
                            open = application.getAssets().open(Constants.AREA_ZIP_NAME);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw Exceptions.propagate(e);
                        }
                        boolean isUpZipSuccess = GZIPUtils.unZipDB(open, packageName, Constants.AREA_DB_NAME);
                        if (isUpZipSuccess) {
                            if (JavaKit.File.isFileExist(dbFile)) {
                                String encrypt = CodecKit.MD5.encrypt(dbFile);
                                ACacheModelImpl.saveAreaDbMD5Code(encrypt);
                                ACacheModelImpl.saveAppVersionCode(currentVersion);
                            }
                        }
                        return isUpZipSuccess;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isSuccess) throws Exception {
                        if (callback != null) {
                            callback.onCopyDBToApp(isSuccess);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (callback != null) {
                            callback.onCopyDBToApp(false);
                        }
                    }
                });
    }
}
