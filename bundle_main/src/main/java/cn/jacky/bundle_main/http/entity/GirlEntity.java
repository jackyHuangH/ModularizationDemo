package cn.jacky.bundle_main.http.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 作   者： by Hzj on 2017/12/13/013.
 * 描   述：   妹子图片数据实体
 * 修订记录：
 */

public class GirlEntity {

    @JSONField(name = "_id")
    private String id;
    @JSONField(name = "url")
    private String imageUrl;
    @JSONField(name = "who")
    private String who;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }


    @Override
    public String toString() {
        return "GirlEntity{" +
                "id='" + id + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", who='" + who + '\'' +
                '}';
    }
}
