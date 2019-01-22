package cn.hzjdemo.bundle_main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.hzjdemo.bundle_main.gen.User;
import cn.hzjdemo.bundle_main.model.impl.ContextBehaviorImpl;
import cn.hzjdemo.bundle_main.model.UserDataManager;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:Hzj
 * @date :2018/7/27/027
 * desc  ：
 * record：
 */
@RunWith(JUnit4.class)
public class ReadDBTest extends AndroidTestCase {

    private static final String TAG = "ReadDBTest";

    /**
     * 从Assets目录读取.sql文件并执行内部Sql语句
     */
    public void readSqlFromAssest(SQLiteDatabase db, String dbFilePath) {
        Context applicationContext = ContextBehaviorImpl.getApplicationContext();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(applicationContext.getAssets().open(dbFilePath)));
            System.out.println("路径:" + dbFilePath);
            String line;
            String buffer = "";
            //开启事务
            db.beginTransaction();
            while ((line = in.readLine()) != null) {
                buffer += line;
                if (line.trim().endsWith(";")) {
                    db.execSQL(buffer.replace(";", ""));
                    buffer = "";
                }
            }
            //设置事务标志为成功，当结束事务时就会提交事务
            db.setTransactionSuccessful();
            Log.d(TAG, "readSqlFromAssest: success");
        } catch (Exception e) {
            Log.e("db-error", e.toString());
        } finally {
            //事务结束
            db.endTransaction();
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                Log.e("db-error", e.toString());
            }
        }
    }

    @Test
    public void mergeSqlTest() {
        readSqlFromAssest(UserDataManager.getInstance().getDb(), "sqlupdate1.sql");
    }


    @Test
    public void insertUserToDB() {

        List<User> userList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(Long.valueOf(i));
            user.setName("张三" + i);
            user.setAge(5 + i);
            userList.add(user);
        }

        Observable.fromIterable(userList)
                .map(new Function<User, Long>() {
                    @Override
                    public Long apply(User user) throws Exception {
                        Long aLong = UserDataManager.getInstance().insertUser(user);
                        return aLong;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "accept: " + aLong);
                    }
                });

    }

}
