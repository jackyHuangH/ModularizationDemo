package cn.jacky.bundle_main.common;

/**
 * 作   者： by Hzj on 2018/1/3/003.
 * 描   述：
 * 修订记录：
 */

public enum Operation {
    PLUS() {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS() {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    };

    public abstract double apply(double x, double y);
}
