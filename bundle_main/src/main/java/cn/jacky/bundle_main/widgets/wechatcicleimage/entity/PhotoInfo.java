package cn.jacky.bundle_main.widgets.wechatcicleimage.entity;

import java.io.Serializable;

/**
 * Created by suneee on 2016/11/17.
 */
public class PhotoInfo implements Serializable{
    public PhotoInfo() {
    }

    public PhotoInfo(String url, int w, int h) {
        this.url = url;
        this.w = w;
        this.h = h;
    }

    public PhotoInfo(String url) {
        this.url = url;
    }

    //图片URL
    public String url;
    //单张图片的显示宽
    public int w;
    //单张图片显示高
    public int h;
}
