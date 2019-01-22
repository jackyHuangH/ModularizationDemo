package cn.hzjdemo.bundle_main;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testNumber() {
        Integer boxedA = 60;
        Integer boxedAA = 60;

        System.out.println("比较A：" + (boxedA.equals(boxedAA)));

        Integer boxedB = 229;
        Integer boxedBB = 229;

        System.out.println("比较B：" + (boxedB.equals(boxedBB)));

        if (boxedA < boxedB) {
            System.out.println("boxedA < boxedB：");
        } else if (boxedA < 100) {
            System.out.println("boxedA<100：");
        } else {
            System.out.println("以上都不满足");
        }
    }

    @Test
    public void testInterval() {
        int maxCount = 5;

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .doOnNext(i -> System.out.println("next: " + i))
                .map(aLong -> maxCount - aLong)
//                .takeUntil(count -> count <= 0)
                .subscribe(aLong -> System.out.println("onNext: " + aLong));
    }
}