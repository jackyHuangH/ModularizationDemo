package cn.jacky.bundle_main.bean;

/**
 * Created by Hzj on 2017/8/17.
 * 参与记录数据模型
 */

public class JoinRecordBean {
    private int type;//0 日期 1 内容
    private String name;
    private String date;

    public JoinRecordBean() {
    }

    public JoinRecordBean(int type, String name, String date) {
        this.type = type;
        this.name = name;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
