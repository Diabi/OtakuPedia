package com.example.feng.otakuspedia.module.home.bangumi;

import com.example.feng.otakuspedia.bean.BangumiItem;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Feng on 2018/8/7.
 */

class BangumiModel {

    private BangumiListener mListener;

    BangumiModel(BangumiListener listener) {
        mListener = listener;
    }

    /**
     * 加载番剧数据
     * @param loadFactor
     */
    void getBangumiData(int loadFactor) {
        try {
            BmobQuery<BangumiItem> query = new BmobQuery<>();
            query.setLimit(loadFactor);
            query.order("-createdAt");
            query.findObjects(new FindListener<BangumiItem>() {
                @Override
                public void done(List<BangumiItem> list, BmobException e) {
                    if (e == null) {
                        mListener.onSuccess(list);
                    } else {
                        mListener.onFailure();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重新加载番剧数据, 处理下拉加载更多
     * @param loadFactor
     */
    void regainBangumiData(int loadFactor) {
        try {
            BmobQuery<BangumiItem> query = new BmobQuery<>();
            query.setLimit(loadFactor);
            query.order("-createdAt");
            query.findObjects(new FindListener<BangumiItem>() {
                @Override
                public void done(List<BangumiItem> list, BmobException e) {
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

    /**
     * 请求番剧数据监听接口
     */
    public interface BangumiListener {
        void onSuccess(List<BangumiItem> list);
        void onFailure();
        void onReloadSuccess(List<BangumiItem> list);
        void onReloadFailed(BmobException e);
    }
}
