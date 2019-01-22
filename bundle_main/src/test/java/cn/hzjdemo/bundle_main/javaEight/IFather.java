package cn.hzjdemo.bundle_main.javaEight;

/**
 * @author:Hzj
 * @date :2018/10/19/019
 * desc  ：函数式接口：有且仅有一个抽象方法，可以有多个非抽象方法的接口
 * record：
 */
public interface IFather {
    void talk(String s);

    /**
     * java8开始接口里可以定义默认方法
     */
    default void print() {
        System.out.println("我是你爸爸！");
    }

    /**
     * java8 接口中还可以定义静态方法，并且可以提供实现
     */
    static void tobe() {
        System.out.println("人类");
    }
}
