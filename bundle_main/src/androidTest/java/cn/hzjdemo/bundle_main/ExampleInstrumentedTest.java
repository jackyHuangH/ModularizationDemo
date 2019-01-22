package cn.hzjdemo.bundle_main;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.util.SparseArray;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.EnumSet;
import java.util.HashMap;

import cn.hzjdemo.bundle_main.common.Operation;
import cn.hzjdemo.bundle_main.common.Planet;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "AndroidTest";
    private static final int MAX = 50000;

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("cc.pachira.onepurchase", appContext.getPackageName());
    }


    @Test
    public void testPlanet() {
        Planet nameByNum = Planet.EARTH.getNameByNum(5);
        for (Planet planet : Planet.values()) {
            Log.d(TAG, "testPlanet: " + planet);
            Log.d(TAG, "lanet.toString(): " + planet.toString());
        }
        Log.d(TAG, "EnumSet: " + EnumSet.of(Planet.valueOf("EARTH"), Planet.JUPITER));
        Log.d(TAG, "useAppContext: " + nameByNum.getName() + "==" + nameByNum.getNum());
    }

    @Test
    public void testOperation() {
        double result = Operation.PLUS.apply(5, 6);
        Log.d(TAG, "testOperation: " + result);
    }


    @Test
    public void testHashMap() {
        //比较hashMap和SparseArray 二者占用内存和插入值占用时间
        HashMap<Integer, String> hashMap = new HashMap<>();
        long start = System.currentTimeMillis();

        for (int i = 0; i < MAX; i++) {
            hashMap.put(i, String.valueOf(i));
        }
        long totalTime = System.currentTimeMillis() - start;
        long totalMemory = Runtime.getRuntime().totalMemory();

        Log.d(TAG, "testHashMap: 占用内存：" + totalMemory + "\n耗时：" + totalTime);
    }

    @Test
    public void testSparseArrary() {
        //比较hashMap和SparseArray 二者占用内存和插入值占用时间
        SparseArray<String> sparseArray = new SparseArray<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            sparseArray.put(i, String.valueOf(i));
        }
        long totalTime = System.currentTimeMillis() - start;
        long totalMemory = Runtime.getRuntime().totalMemory();
        Log.d(TAG, "testSparseArrary: 占用内存：" + totalMemory + "\n耗时：" + totalTime);
    }


    @Test
    public void testMerge() {
//        Observable<Integer> observable1 = Observable.just(1);
//        Observable<Integer> observable2 = Observable.just(2);
//        Observable<Integer> observable3 = Observable.just(3);
//        Observable<Integer> observable4 = Observable.just(4);
//        Observable<Integer> observable5 = Observable.just(5);
//
//        Observable.merge(observable1, observable2, observable3, observable4, observable5)
//                .subscribeOn(Schedulers.io())
//                .flatMap(new Func1<Integer, Observable<List<String>>>() {
//                    @Override
//                    public Observable<List<String>> call(Integer integer) {
//                        List<String> list = new ArrayList<>();
//                        list.add("flatmap:变成String了：" + integer.toString());
//                        return Observable.just(list);
//                    }
//                })
//
////                .map(new Func1<Integer, String>() {
////                    @Override
////                    public String call(Integer integer) {
////                        return "变成String了：" + integer.toString();
////                    }
////                })
//                .subscribe(new Action1<List<String>>() {
//                    @Override
//                    public void call(List<String> list) {
//                        Log.d(TAG, "merge后的结果: " + list);
//                    }
//                });
    }
}
