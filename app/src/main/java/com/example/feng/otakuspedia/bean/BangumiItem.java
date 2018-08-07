package com.example.feng.otakuspedia.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 作品item
 */
public class BangumiItem extends BmobObject {
    private String title;
    private String content;
    private BmobFile image;
    private BmobFile expandImage;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public BmobFile getImage() {
        return image;
    }

    public BmobFile getExpandImage() {
        return expandImage;
    }
}
