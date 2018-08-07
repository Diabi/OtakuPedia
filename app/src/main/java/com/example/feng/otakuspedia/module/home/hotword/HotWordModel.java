package com.example.feng.otakuspedia.module.home.hotword;

import com.example.feng.otakuspedia.bean.OtakuItem;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Feng on 2018/8/7.
 */

class HotWordModel {

    private HotWordListener mListener;

    HotWordModel(HotWordListener listener) {
        mListener = listener;
    }

    void getHotWordData(int loadFactor) {
        try {
            BmobQuery<OtakuItem> query = new BmobQuery<>();
            query.setLimit(loadFactor);
            query.order("-createdAt");
            query.findObjects(new FindListener<OtakuItem>() {
                @Override
                public void done(List<OtakuItem> list, BmobException e) {
                    if (e == null) {
                        mListener.onLoadSuccess(list);
                    } else {
                        mListener.onLoadFailure();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void regainHotWordData(int loadFactor) {
        try {
            BmobQuery<OtakuItem> query = new BmobQuery<>();
            query.setLimit(loadFactor);
            query.order("-createdAt");
            query.findObjects(new FindListener<OtakuItem>() {
                @Override
                public void done(List<OtakuItem> list, BmobException e) {
                    if (e == null) {
                        mListener.onReloadSuccess(list);
                    } else {
                        mListener.onReloadFailure(e);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface HotWordListener {
        void onLoadSuccess(List<OtakuItem> list);
        void onLoadFailure();
        void onReloadSuccess(List<OtakuItem> list);
        void onReloadFailure(BmobException e);
    }
}