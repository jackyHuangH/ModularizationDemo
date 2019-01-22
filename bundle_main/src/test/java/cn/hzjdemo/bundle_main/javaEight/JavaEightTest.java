package cn.hzjdemo.bundle_main.javaEight;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author:Hzj
 * @date :2018/10/19/019
 * desc  ：
 * record：
 */
public class JavaEightTest {

    @Test
    public void test1() {
        List<String> names1 = new ArrayList<String>();
        names1.add("Google ");
        names1.add("Runoob ");
        names1.add("Taobao ");
        names1.add("Baidu ");
        names1.add("Sina ");

        Collections.sort(names1, (s1, s2) -> s1.compareTo(s2));

        System.out.println(names1);

        List<Integer> numList = Arrays.asList(5, 6, 8, 44, 58, 1, 3, 7);

        Son son = new Son();
        son.say("我说");
        son.talk("我说shuo ");
        son.print();
    }

    /**
     * 方法引用
     */
    @Test
    public void functionReference() {
        //构造器引用：
        Car car = Car.create(Car::new);
        List<Car> carList = Collections.singletonList(car);
        //静态方法引用
        carList.forEach(Car::collide);
        //对象的方法引用
        carList.forEach(Car::repair);
        //特定对象的方法引用
        Car policeCar = Car.create(Car::new);
        carList.forEach(policeCar::follow);
    }

    @Test
    public void streamTest() {
        List<String> list = Arrays.asList("你好", "a", "b", "", "d", "");

        long count = list.stream().filter(string -> string.isEmpty())
                .count();
        System.out.println("count:" + count);

//        Random random = new Random();
//        random.ints().limit(10).forEach(System.out::println);

        List<Integer> numList = Arrays.asList(5, 4, 8, 25, 6, 41);
        numList.stream().map(num -> num * num).forEach(System.out::println);
    }

    @Test
    public void testDate(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
    }

}
