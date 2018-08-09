package com.example.feng.otakuspedia.module.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {
    /**
     * 视图是否已加载
     */
    private boolean isViewInitiated;

    /**
     * 数据是否加载过
     */
    private boolean isDataLoaded;

    /**
     * Fragment是否已对用户可见
     */
    private boolean isVisibleToUser;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            prepareLoadData();
        }
    }

    /**
     * 懒加载
     */
    public abstract void lazyLoadData();

    /**
     * 判断懒加载条件
     */
    public void prepareLoadData() {
        if (isViewInitiated && isVisibleToUser && !isDataLoaded) {
            lazyLoadData();
            isDataLoaded = true;
        }
    }
}
