package com.example.feng.otakuspedia.module.home.character;

import com.example.feng.otakuspedia.bean.CharacterItem;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Feng on 2018/8/7.
 */

public interface ICharacterView {
    void onSuccess(List<CharacterItem> list);
    void onFailure(BmobException e);
    void onReloadSuccess(List<CharacterItem> list);
    void onReloadFailed(BmobException e);
    void showProgress();
}
