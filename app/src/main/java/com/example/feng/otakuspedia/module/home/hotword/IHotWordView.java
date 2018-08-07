package com.example.feng.otakuspedia.module.home.hotword;

import com.example.feng.otakuspedia.bean.OtakuItem;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Feng on 2018/8/7.
 */

public interface IHotWordView {
    void showProgress();
    void hideProgress();
    void refreshComplete();
    void loadMoreFailed(BmobException e);
    void setHotWord(List<OtakuItem> list);
    void resetHotWord(List<OtakuItem> list);
}
