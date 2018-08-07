package com.example.feng.otakuspedia.module.home.bangumi;

import com.example.feng.otakuspedia.bean.BangumiItem;

import java.util.List;

/**
 * Created by Feng on 2018/8/7.
 */

public class BangumiPresenter implements BangumiModel.BangumiListener {
    private BangumiModel bangumiModel;
    private IBangumiView bangumiView;

    BangumiPresenter(IBangumiView bangumiView) {
        bangumiModel = new BangumiModel(this);
        this.bangumiView = bangumiView;
    }

    void getBangumiData(int loadFactor) {
        bangumiView.showProgress();
        bangumiModel.getBangumiData(loadFactor);
    }

    @Override
    public void onSuccess(List<BangumiItem> list) {
        bangumiView.loadBangumiData(list);
        bangumiView.hideProgress();
        bangumiView.refreshComplete();
    }

    @Override
    public void onFailure() {
        bangumiView.hideProgress();
    }
}
