package cn.hzjdemo.bundle_main.javaEight;

/**
 * @author:Hzj
 * @date :2018/10/19/019
 * desc  ：
 * record：
 */
public class Son implements IFather, IAnimal {
    @Override
    public void say(String s) {
        System.out.println("say:" + s);
    }

    @Override
    public void talk(String s) {
        System.out.println("talk:" + s);
    }

    @Override
    public void print() {
        /**
         * 两个接口有同样的默认方法处理：
         * 第一个解决方案是创建自己的默认方法，来覆盖重写接口的默认方法
         * 第二种解决方案可以使用 super 来调用指定接口的默认方法：
         */
        IFather.super.print();
        IFather.tobe();
    }
}
