package com.example.feng.otakuspedia.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 人物item
 */
public class CharacterItem extends BmobObject {
    private String name;
    private String gender;
    private String introduction;
    private String belong;
    private BmobFile image;

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getBelong() { return belong; }

    public String getIntroduction() {
        return introduction;
    }

    public BmobFile getImage() {
        return image;
    }
}
