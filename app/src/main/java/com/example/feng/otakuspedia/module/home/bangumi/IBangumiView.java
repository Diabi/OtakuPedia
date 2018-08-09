package com.example.feng.otakuspedia.module.home.bangumi;

import com.example.feng.otakuspedia.bean.BangumiItem;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Feng on 2018/8/7.
 */

public interface IBangumiView {

    /**
     * 显示进度条
     */
    void showProgress();

    /**
     * 隐藏进度条
     */
    void hideProgress();

    /**
     * 刷新完成, 隐藏下拉刷新进度条
     */
    void refreshComplete();

    /**
     * 加载更多番剧数据失败, 提示错误信息
     * @param e
     */
    void onReloadFailed(BmobException e);

    /**
     * 加载番剧数据成功, 把数据渲染到UI界面中
     * @param list
     */
    void loadBangumiData(List<BangumiItem> list);

    /**
     * 重新加载成功, 更新UI界面
     * @param list
     */
    void reloadBangumiData(List<BangumiItem> list);
}
