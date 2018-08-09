package com.example.feng.otakuspedia.module.home.character;

import com.example.feng.otakuspedia.bean.CharacterItem;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Feng on 2018/8/7.
 */

public interface ICharacterView {

    /**
     * 加载人物数据成功
     * @param list
     */
    void onSuccess(List<CharacterItem> list);

    /**
     * 加载失败
     * @param e
     */
    void onFailure(BmobException e);

    /**
     * 加载更多成功
     * @param list
     */
    void onReloadSuccess(List<CharacterItem> list);

    /**
     * 加载更多失败
     * @param e
     */
    void onReloadFailed(BmobException e);

    /**
     * 显示进度条
     */
    void showProgress();
}
