package com.zenchn.picbrowserlib.util;

import java.util.regex.Pattern;

/**
 * 作    者：wangr on 2017/5/24 16:57
 * 描    述：常用正则表达式大全
 * 修订记录：
 */
public class URIUtil {

    /**
     * 正则：URL
     */
    private static final String REGEX_URL = "[a-zA-z]+://[^\\s]*";

    /**
     * 判断图片是否是网络图片
     * @param source
     * @return
     */
    public static boolean isOnlinePicture(String source){
        Pattern pattern = Pattern.compile(REGEX_URL);
        return pattern.matcher(source).find();
    }
}
