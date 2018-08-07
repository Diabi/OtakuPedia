package com.example.feng.otakuspedia.module.home.bangumi;

import com.example.feng.otakuspedia.bean.BangumiItem;

import java.util.List;

/**
 * Created by Feng on 2018/8/7.
 */

public interface IBangumiView {
    void showProgress();
    void hideProgress();
    void refreshComplete();
    void loadBangumiData(List<BangumiItem> list);
}
