package cn.jacky.bundle_main.bean;

import java.io.Serializable;

/**
 * Created by Hzj on 2017/8/23.
 * 收货地址数据模型
 */

public class TakeAddressBean implements Serializable{
    private String province;
    private String city;
    private String address;

    public TakeAddressBean() {
    }

    public TakeAddressBean(String province, String city, String address) {
        this.province = province;
        this.city = city;
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
