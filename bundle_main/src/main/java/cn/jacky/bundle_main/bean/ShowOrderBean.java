package cn.jacky.bundle_main.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hzj on 2017/8/18.
 * 晒单数据模型
 */

public class ShowOrderBean {
    private boolean hasImg;
    private String content;
    private String imgUrl;

    public List<String> urlList = new ArrayList<>();

    public boolean isShowAll = false;

    public ShowOrderBean() {
    }

    public ShowOrderBean(boolean hasImg, String imgUrl) {
        this.hasImg = hasImg;
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isHasImg() {
        return hasImg;
    }

    public void setHasImg(boolean hasImg) {
        this.hasImg = hasImg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
