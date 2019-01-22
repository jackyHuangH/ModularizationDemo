package cn.jacky.bundle_main.model.impl.local;

import android.support.annotation.NonNull;

import com.zenchn.apilib.util.CodecKit;


/**
 * 作    者：wangr on 2017/9/1 13:28
 * 描    述：
 * 修订记录：
 */

public class EncryptModelImpl {

    private EncryptModelImpl() {
    }


    public static String encryptPwdSHA256(@NonNull String text) {
        return CodecKit.SHA256.encrypt(text);
    }

    public static String encryptPwdMD5(@NonNull String text) {
        return CodecKit.MD5.encrypt(text);
    }
}
