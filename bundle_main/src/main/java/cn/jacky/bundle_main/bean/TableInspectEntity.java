package cn.jacky.bundle_main.bean;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

/**
 * @author:Hzj
 * @date :2019/1/14/014
 * desc  ：表格 数据实体
 * record：
 */
@SmartTable(name = "测试表格")
public class TableInspectEntity {
    @SmartColumn(id = 0,name = "数据时间")
    private String date;
    @SmartColumn(id = 1,name = "数值1")
    private Double num1;
    @SmartColumn(id = 2,name = "数值2")
    private Double num2;
    @SmartColumn(id = 3,name = "数值3")
    private Double num3;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getNum1() {
        return num1;
    }

    public void setNum1(Double num1) {
        this.num1 = num1;
    }

    public Double getNum2() {
        return num2;
    }

    public void setNum2(Double num2) {
        this.num2 = num2;
    }

    public Double getNum3() {
        return num3;
    }

    public void setNum3(Double num3) {
        this.num3 = num3;
    }

    @Override
    public String toString() {
        return "TableInspectEntity{" +
                "date='" + date + '\'' +
                ", num1=" + num1 +
                ", num2=" + num2 +
                ", num3=" + num3 +
                '}';
    }
}
