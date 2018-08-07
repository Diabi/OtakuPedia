package com.example.feng.otakuspedia.module.home.character;

import com.example.feng.otakuspedia.bean.CharacterItem;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Feng on 2018/8/7.
 */

public class CharacterPresenter implements CharacterModel.CharacterListener {

    private CharacterModel characterModel;
    private ICharacterView characterView;

    CharacterPresenter(ICharacterView view) {
        characterModel = new CharacterModel(this);
        this.characterView = view;
    }

    void loadCharacterData(int loadFactor) {
        characterView.showProgress();
        characterModel.getCharacterData(loadFactor);
    }

    void reloadCharacterData(int loadFactor) {
        characterModel.regainCharacterData(loadFactor);
    }

    @Override
    public void onLoadSuccess(List<CharacterItem> list) {
        characterView.onSuccess(list);
    }

    @Override
    public void onLoadFailed(BmobException e) {
        characterView.onFailure(e);
    }

    @Override
    public void onReloadSuccess(List<CharacterItem> list) {
        characterView.onReloadSuccess(list);
    }

    @Override
    public void onReloadFailed(BmobException e) {
        characterView.onReloadFailed(e);
    }
}
