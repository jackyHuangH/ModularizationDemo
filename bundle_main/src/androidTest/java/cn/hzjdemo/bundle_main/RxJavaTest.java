package cn.hzjdemo.bundle_main;

import android.test.AndroidTestCase;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author:Hzj
 * @date :2018/8/1/001
 * desc  ：
 * record：
 */
@RunWith(JUnit4.class)
public class RxJavaTest extends AndroidTestCase {

    private static final String TAG = "RxJavaTest";

    @Test
    public void rxjava2Review() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "subscribe: ");
                emitter.onNext(1);
                Log.d(TAG, "next: 1");
                emitter.onNext(2);
                Log.d(TAG, "next: 2");
                emitter.onNext(3);
                Log.d(TAG, "next: 3");
                emitter.onNext(4);
                emitter.onNext(5);
                emitter.onNext(6);
            }
        })
//                .map(new Function<Integer, String>() {
//                    @Override
//                    public String apply(Integer integer) throws Exception {
//                        return String.valueOf(integer);
//                    }
//                })
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        ArrayList<String> list = new ArrayList<>();
                        list.add("我变成字符串了：" + integer);

                        return Observable.fromIterable(list).delay(2, TimeUnit.SECONDS);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "accept: " + s);
                    }
                });
    }

    @Test
    public void test() {
        //sample 采样，背压策略
        //take和takeList方法可以将上游事件中的前N项或者最后N项发送到下游,其他事件则进行过滤
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 200; i++) {
                    emitter.onNext(i);
                }
            }
        })
//                .sample(1, TimeUnit.SECONDS)
//                .take(5)//take count 拿指定数量
//                .take(10, TimeUnit.SECONDS) //take time 拿指定时间内的数据
//                .take(2)
                .repeat(5)//生成重复事件
                .distinct()//去除重复
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: " + integer);
                    }
                });

    }

    @Test
    public void test2() {
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
////                for (int i = 0; i < 200; i++) {
////                    emitter.onNext(i);
////                }
//
//                Log.d(TAG, "subscribe: " + emitter.isDisposed());
//                if (emitter.isDisposed()) {
//                    return;
//                }
//
//                while (true) {
//                    emitter.onNext("当前时间：" + System.currentTimeMillis());
//                    Log.d(TAG, "onnext: " + System.currentTimeMillis());
//                    SystemClock.sleep(200);
//                }
//            }
//        })
////                .buffer(5)//一次缓存5个，返回list
//                .buffer(5, TimeUnit.SECONDS)//每隔5秒 取出消息，返回list
//                .subscribe(new Consumer<List<String>>() {
//                    @Override
//                    public void accept(List<String> strings) throws Exception {
//                        Log.d(TAG, "accept: " + strings);
//                    }
//                });

        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emmit: 1");
                emitter.onNext(1);
                Thread.sleep(1000);
                Log.d(TAG, "emmit: 2");
                emitter.onNext(2);
                Thread.sleep(1000);
                Log.d(TAG, "emmit: 3");
                emitter.onNext(3);
                Thread.sleep(1000);
                Log.d(TAG, "emmit: 4");
                emitter.onNext(4);
                Thread.sleep(1000);
            }
        });

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "emmit: A");
                emitter.onNext("A");
                Thread.sleep(1000);
                Log.d(TAG, "emmit: B");
                emitter.onNext("B");
                Thread.sleep(1000);
                Log.d(TAG, "emmit: C");
                emitter.onNext("C");
                Thread.sleep(1000);
            }
        });

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + "===" + s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: " + s);
            }
        });
    }

    @Test
    public void scanTest() {
        //累加效果
        //Scan操作符对原始Observable发射的第一项数据应用一个函数，然后将这个函数的结果作为自己的第一项数据发射。
        // 将函数的结果同第二项数据一起填充给这个函数来产生自己的第二项数据。持续进行这个过程来产生剩余的数据序列
        Observable.just(1, 2, 3, 4, 5)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "accept: " + integer);
            }
        });

    }

    @Test
    public void windowTest() {
        Observable.range(1, 10)
                .window(new Observable<Integer>() {
                    @Override
                    protected void subscribeActual(Observer<? super Integer> observer) {
                        observer.onNext(1);
                        observer.onNext(2);
                        observer.onNext(3);
                    }
                })
                .subscribe(new Consumer<Observable<Integer>>() {
                    @Override
                    public void accept(Observable<Integer> integerObservable) throws Exception {
                        integerObservable.subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.d(TAG, "accept: " + integer);
                            }
                        });
                    }
                });

    }

    @Test
    public void test3() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "subscribe: thread=" + Thread.currentThread().getName());
                Log.d(TAG, "subscribe: ");
                for (int i = 0; i < 200; i++) {
                    Log.d(TAG, "emmit: " + i);
                    emitter.onNext(i);
                }
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: thread=]" + Thread.currentThread().getName());
                Log.d(TAG, "onNext: " + integer);
                if (integer == 10) {
                    mDisposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }
}
