package com.example.feng.otakuspedia.module.home.hotword;

import com.example.feng.otakuspedia.bean.OtakuItem;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Feng on 2018/8/7.
 */

public class HotWordPresenter implements HotWordModel.HotWordListener {

    private HotWordModel hotWordModel;
    private IHotWordView hotWordView;

    HotWordPresenter(IHotWordView hotWordView) {
        hotWordModel = new HotWordModel(this);
        this.hotWordView = hotWordView;
    }

    void getHotWordData(int loadFactor) {
        hotWordView.showProgress();
        hotWordModel.getHotWordData(loadFactor);
    }

    void regainHotWordData(int loadFacotr) {
        hotWordModel.changeLoadFactor(loadFacotr);
    }

    @Override
    public void onLoadSuccess(List<OtakuItem> list) {
        hotWordView.setHotWord(list);
        hotWordView.hideProgress();
        hotWordView.refreshComplete();
    }

    @Override
    public void onLoadFailure() {
        hotWordView.hideProgress();
    }

    @Override
    public void onReloadSuccess(List<OtakuItem> list) {
        hotWordView.resetHotWord(list);
    }

    @Override
    public void onReloadFailure(BmobException e) {
        hotWordView.loadMoreFailed(e);
    }
}
