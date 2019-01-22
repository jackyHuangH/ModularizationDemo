package cn.jacky.bundle_main.http.entity;

import java.io.Serializable;

/**
 * @author:Hzj
 * @date :2019/1/18/018
 * desc  ：ARouter传递数据测试
 * record：
 */
public class CarEntity implements Serializable {
    private static final long serialVersionUID = -941552159097844512L;

    private String name;
    private int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CarEntity{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
