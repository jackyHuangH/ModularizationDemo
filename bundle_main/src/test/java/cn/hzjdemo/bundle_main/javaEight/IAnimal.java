package cn.hzjdemo.bundle_main.javaEight;

/**
 * @author:Hzj
 * @date :2018/10/19/019
 * desc  ：函数式接口@FunctionalInterface：有且仅有一个抽象方法，可以有多个非抽象方法的接口
 * record：
 */
@FunctionalInterface
public interface IAnimal {
    void say(String s);

    /**
     * java8开始接口里可以定义默认方法
     */
    default void print() {
        System.out.println("我是你祖宗！");
    }
}
