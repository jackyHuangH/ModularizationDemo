package cn.jacky.bundle_main.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.jacky.bundle_main.gen.DaoMaster;
import cn.jacky.bundle_main.gen.DaoSession;
import cn.jacky.bundle_main.gen.User;
import cn.jacky.bundle_main.model.impl.ContextBehaviorImpl;
import cn.jacky.bundle_main.wrapper.greenDao.UpgradeOpenHelper;

/**
 * @author:Hzj
 * @date :2018/7/27/027
 * desc  ：user db info manager
 * record：
 */
public class UserDataManager {
    private static final String TAG = "UserDataManager";

    private UpgradeOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private UserDataManager() {
        setDatabase();
    }

    private static class Singleton {
        private static UserDataManager SINGLEINSTANCE = new UserDataManager();
    }

    public static UserDataManager getInstance() {
        return Singleton.SINGLEINSTANCE;
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        Context applicationContext = ContextBehaviorImpl.getApplicationContext();
        mHelper = new UpgradeOpenHelper(applicationContext, "user.db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }


    public Long insertUser(User user){
        long insert = mDaoSession.getUserDao()
                .insertOrReplace(user);
        return insert;
    }
}
