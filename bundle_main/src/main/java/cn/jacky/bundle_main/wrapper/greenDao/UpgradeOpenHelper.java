package cn.jacky.bundle_main.wrapper.greenDao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

import cn.jacky.bundle_main.gen.DaoMaster;
import cn.jacky.bundle_main.gen.UserDao;


/**
 * 作    者：hzj on 2018/7/25 9:24
 * 描    述：
 * 修订记录：
 */
public class UpgradeOpenHelper extends DaoMaster.OpenHelper {

    public UpgradeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * 数据库升级
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //操作数据库的更新 在onUpgrade方法添加如下代码即可，参数为所有的Dao类
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, UserDao.class);

    }

}