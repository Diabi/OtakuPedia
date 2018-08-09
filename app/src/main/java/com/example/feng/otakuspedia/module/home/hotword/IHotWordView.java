package com.example.feng.otakuspedia.module.home.hotword;

import com.example.feng.otakuspedia.bean.OtakuItem;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Feng on 2018/8/7.
 */

public interface IHotWordView {

    /**
     * 显示进度条
     */
    void showProgress();

    /**
     * 隐藏进度条
     */
    void hideProgress();

    /**
     * 下拉刷新完成, 隐藏进度条
     */
    void refreshComplete();

    /**
     * 加载热词数据成功
     * @param list
     */
    void setHotWord(List<OtakuItem> list);

    /**
     * 加载更多成功
     * @param list
     */
    void resetHotWord(List<OtakuItem> list);

    /**
     * 加载更多失败
     * @param e
     */
    void loadMoreFailed(BmobException e);
}
