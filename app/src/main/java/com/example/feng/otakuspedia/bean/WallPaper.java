package com.example.feng.otakuspedia.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 动态壁纸
 */
public class WallPaper extends BmobObject {

    private BmobFile wallpaper;

    public BmobFile getWallpaper() {
        return wallpaper;
    }
}