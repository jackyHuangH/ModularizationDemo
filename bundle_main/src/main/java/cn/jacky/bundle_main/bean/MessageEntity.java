package cn.jacky.bundle_main.bean;

/**
 * @author:Hzj
 * @date :2018/10/22/022
 * desc  ：
 * record：
 */
public class MessageEntity {

    public String message;
    public String imgUrl;

    @Override
    public String toString() {
        return "MessageEntity{" +
                "message='" + message + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
