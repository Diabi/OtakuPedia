package com.example.feng.otakuspedia.module.home.character;

import com.example.feng.otakuspedia.bean.CharacterItem;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Feng on 2018/8/7.
 */

class CharacterModel {

    private CharacterListener mListener;

    CharacterModel(CharacterListener listener) {
        mListener = listener;
    }

    void getCharacterData(int loadFactor) {
        try {
            BmobQuery<CharacterItem> query = new BmobQuery<>();
            query.setLimit(loadFactor);
            query.order("-createdAt");
            query.findObjects(new FindListener<CharacterItem>() {
                @Override
                public void done(List<CharacterItem> list, BmobException e) {
                    if (e == null) {
                        mListener.onLoadSuccess(list);
                    } else {
                        mListener.onLoadFailed(e);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void regainCharacterData(int loadFactor) {
        try {
            BmobQuery<CharacterItem> query = new BmobQuery<>();
            query.setLimit(loadFactor);
            query.order("-createdAt");
            query.findObjects(new FindListener<CharacterItem>() {
                @Override
                public void done(List<CharacterItem> list, BmobException e) {
                    if (e == null) {
                        mListener.onReloadSuccess(list);
                    } else {
                        mListener.onReloadFailed(e);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface CharacterListener {
        void onLoadSuccess(List<CharacterItem> list);
        void onLoadFailed(BmobException e);
        void onReloadSuccess(List<CharacterItem> list);
        void onReloadFailed(BmobException e);
    }
}
