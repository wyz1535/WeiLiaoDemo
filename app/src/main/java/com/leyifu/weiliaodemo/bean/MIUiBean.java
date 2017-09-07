package com.leyifu.weiliaodemo.bean;

/**
 * Created by hahaha on 2017/7/7 0007.
 */

public class MIUiBean {

    private String name;
    private int imageId;

    public MIUiBean(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
