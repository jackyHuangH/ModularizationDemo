package cn.jacky.bundle_main.common;

/**
 * 作   者： by Hzj on 2018/1/3/003.
 * 描   述：枚举使用
 * 修订记录：
 */

public enum Planet {
    MERCURY(1, "水星"),
    VENUS(2, "金星"),
    EARTH(3, "地球"),
    MARS(4, "火星"),
    JUPITER(5, "天王星"),
    SATURN(6, "海王星"),
    URANUS(7, "冥王星"),
    NEPTUNE(8, "木星");

    private int num;
    private String name;

    Planet(int number, String name) {
        this.num = number;
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public Planet getNameByNum(int num){
        for (Planet planet : values()) {
            if (planet.getNum()==num) {
                return planet;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }

}
