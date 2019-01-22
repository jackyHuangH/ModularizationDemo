package cn.jacky.bundle_main.model.impl.local;

import android.support.annotation.Nullable;

import com.zenchn.apilib.entity.LoginInfoEntity;
import com.zenchn.support.cache.ACache;

/**
 * 作    者：wangr on 2017/9/1 14:15
 * 描    述：
 * 修订记录：
 */

public class ACacheModelImpl {

    private static ACache mACache;
    private static String ID = "";//缓存资源的标识符

    private ACacheModelImpl() {
    }

    public static void init(ACache aCache) {
        ACacheModelImpl.mACache = aCache;
        clearTemp();
    }

    public static void init(ACache aCache, String ID) {
        ACacheModelImpl.mACache = aCache;
        ACacheModelImpl.ID = ID;
    }

    public static void addMarking(String ID) {
        ACacheModelImpl.ID = ID;
    }

    public static void clearTemp() {
        if (mACache != null) {
            mACache.remove(ID + CacheKey.TEMP_USER_INFO);
        }
    }

    //    ================登录账号密码，存一条===================
    public static void clearLoginTemp() {
        if (mACache != null) {
            mACache.remove(CacheKey.TEMP_LOGIN_INFO);
        }
    }


    @Nullable
    public static LoginInfoEntity getUserLoginInfo() {
        if (mACache != null) {
            Object temp = mACache.getAsObject(CacheKey.TEMP_LOGIN_INFO);
            if (temp != null) {
                return (LoginInfoEntity) temp;
            }
        }
        return null;
    }

    public static void tempUserLoginInfo(LoginInfoEntity loginInfo) {
        if (mACache != null) {
            mACache.put(CacheKey.TEMP_LOGIN_INFO, loginInfo);
        }
    }


    //================定位缓存=================================================
    /*public static LatLng getFirstLocationLatLng() {
        LatLng latLng = null;
        if (null != mACache) {
            double lat = mACache.getAsDouble(CacheKey.SAVE_LOCATION_LAT, 0);
            double lng = mACache.getAsDouble(CacheKey.SAVE_LOCATION_LNG, 0);
            latLng = new LatLng(lat, lng);
        }
        return latLng;
    }*/

    public static void saveFirstLocationLatLng(double lat, double lng) {
        if (mACache != null) {
            mACache.putEverything(CacheKey.SAVE_LOCATION_LAT, lat);
            mACache.putEverything(CacheKey.SAVE_LOCATION_LNG, lng);
        }
    }

    //======================缓存地区数据库的md5=========================
    public static void saveAreaDbMD5Code(String encrypt) {
        if (mACache != null) {
            mACache.putEverything(CacheKey.SAVE_AREA_DB_MD5_CODE, encrypt);
        }
    }

    public static String getAreaDbMD5Code() {
        return mACache != null ? mACache.getAsString(CacheKey.SAVE_AREA_DB_MD5_CODE) : null;
    }

    //======================缓存app版本号=========================
    public static void saveAppVersionCode(int versionCode) {
        if (mACache != null) {
            mACache.putEverything(CacheKey.SAVE_APP_VERSIONCODE, versionCode);
        }
    }

    public static int getAppVersionCode() {
        return mACache != null ? mACache.getAsInt(CacheKey.SAVE_APP_VERSIONCODE, 0) : 0;
    }

    /**
     * ACache 工具类缓存专用key
     * --------------------------------------------------------------------
     */
    private interface CacheKey {
        String SAVE_USER_LOGIN_NAME_HISTORY = "SAVE_USER_LOGIN_NAME_HISTORY";// 用户名历史信息
        String SAVE_LOCATION_LAT = "SAVE_LOCATION_LAT";//保存定位坐标维度
        String SAVE_LOCATION_LNG = "SAVE_LOCATION_LNG";//保存定位坐标经度
        String SAVE_AREA_DB_MD5_CODE = "SAVE_AREA_DB_MD5_CODE";//保存数据库文件MD5值
        String SAVE_APP_VERSIONCODE = "SAVE_APP_VERSIONCODE";//保存app的版本号VersionCode


        //缓存数据部分（退出app则清除）
        String TEMP_USER_INFO = "TEMP_USER_INFO";//用户登录信息
        String TEMP_LOGIN_INFO = "TEMP_LOGIN_INFO";// 用户登录账户密码信息
        String TEMP_RESIDENCE_BUILDING_SUBMIT_INFO = "TEMP_RESIDENCE_BUILDING_SUBMIT_INFO";//住宅房屋信息
        String TEMP_NON_RESIDENCE_BUILDING_SUBMIT_INFO = "TEMP_NON_RESIDENCE_BUILDING_SUBMIT_INFO";//非住宅房屋信息
        String TEMP_CURTAINWALL_BUILDING_SUBMIT_INFO = "TEMP_CURTAINWALL_BUILDING_SUBMIT_INFO";//幕墙房屋信息


    }
}
