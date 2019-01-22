package cn.jacky.bundle_main.bean;

/**
 * @author:Hzj
 * @date :2019/1/15/015
 * desc  ：表格 分数数据实体
 * record：
 */
//@SmartTable(name = "期末考试分数")
public class TableScoreEntity {

//    @SmartColumn(id = 0, name = "姓名")
    private String name;

//    @SmartColumn(id = 1, name = "语文")
    private int mandarin;
//    @SmartColumn(id = 2, name = "数学")
    private int math;
//    @SmartColumn(id = 3, name = "英语")
    private int english;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMandarin() {
        return mandarin;
    }

    public void setMandarin(int mandarin) {
        this.mandarin = mandarin;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    @Override
    public String toString() {
        return "TableScoreEntity{" +
                "name='" + name + '\'' +
                ", mandarin=" + mandarin +
                ", math=" + math +
                ", english=" + english +
                '}';
    }
}
