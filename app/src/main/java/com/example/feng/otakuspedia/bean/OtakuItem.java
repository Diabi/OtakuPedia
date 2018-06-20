package com.example.feng.otakuspedia.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class OtakuItem extends BmobObject {
    private String title;
    private String definition;
    private String representation;
    private BmobFile image;
    private String url;

    public String getDefinition() {
        return definition;
    }

    public String getRepresentation() {
        return representation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BmobFile getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }
}