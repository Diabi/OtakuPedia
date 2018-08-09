package com.example.feng.otakuspedia.module.home.bangumi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.feng.otakuspedia.R;
import com.example.feng.otakuspedia.activity.BangumiInfoActivity;
import com.example.feng.otakuspedia.adpter.BangumiItemAdapter;
import com.example.feng.otakuspedia.bean.BangumiItem;
import com.example.feng.otakuspedia.util.LogUtil;
import com.example.feng.otakuspedia.util.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Feng on 2018/6/26.
 */

public class BangumiFragment extends Fragment implements IBangumiView {

    private View mView;
    private Unbinder unbinder;
    private List<BangumiItem> mList = new ArrayList<>();
    private BangumiItemAdapter bangumiItemAdapter;
    private static int loadFactor = 4;

    private BangumiPresenter bangumiPresenter;
    private boolean isViewInitiated = false;
    private boolean isVisibleToUser = false;
    
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.loading_progress)
    ContentLoadingProgressBar progressBar;

    public static BangumiFragment getInstance() { return new BangumiFragment(); }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.lazilyGetInstance().debug("Bangumi", "onCreate");
        super.onCreate(savedInstanceState);
        bangumiPresenter = new BangumiPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            LogUtil.lazilyGetInstance().debug("Bangumi", "onCreateView");
            mView = inflater.inflate(R.layout.bangumi_fragment, container, false);
            isViewInitiated = true;
            unbinder = ButterKnife.bind(this, mView);
            setOnPullRefresh();
        }
        return mView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            this.isVisibleToUser = true;
            lazyLoad();
        } else {
            this.isVisibleToUser = false;
        }
    }

    /**
     * 懒加载
     */
    private void lazyLoad() {
        if (isVisibleToUser && isViewInitiated) {
            bangumiPresenter.getBangumiData(loadFactor);
            // 防止重复加载
            isVisibleToUser = false;
            isViewInitiated = false;
        }
    }

    @Override
    public void showProgress() {
        progressBar.show();
    }

    @Override
    public void hideProgress() {
        progressBar.hide();
    }

    @Override
    public void refreshComplete() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onReloadFailed(BmobException e) {
        ToastUtil.toast(getContext(), "加载失败, 请重试");
        Log.e("读取失败", e.toString());
        bangumiItemAdapter.loadMoreFail();
    }

    @Override
    public void loadBangumiData(List<BangumiItem> list) {
        mList = list;
        Collections.shuffle(list);
        createList();
    }

    @Override
    public void reloadBangumiData(List<BangumiItem> list) {
        if (mList.size() >= list.size()) {
            bangumiItemAdapter.loadMoreEnd();
            return;
        }
        int size = mList.size();
        for (int i = size; i < list.size(); i++)
            mList.add(list.get(i));
        bangumiItemAdapter.notifyDataSetChanged();
        bangumiItemAdapter.loadMoreComplete();
    }

    /**
     * 生成列表
     */
    private void createList() {
        bangumiItemAdapter = new BangumiItemAdapter(R.layout.bangumi_item, mList, getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(bangumiItemAdapter);
        bangumiItemAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT); // 开启动画
        setOnPullLoadMore();
        setOnItemClickedListener();
    }

    /**
     * 下拉刷新
     */
    private void setOnPullRefresh() {
        refreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bangumiPresenter.getBangumiData(loadFactor);
            }
        });
    }

    /**
     * 上拉加载更多
     */
    private void setOnPullLoadMore() {
        bangumiItemAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadFactor += 4;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bangumiPresenter.regainBangumiData(loadFactor);
                    }
                }, 1500);
            }
        }, mRecyclerView);
    }

    /**
     * 设置item点击监听
     */
    private void setOnItemClickedListener() {
        bangumiItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showBangumiItemInfo(position);
            }
        });
    }

    /**
     * 跳转到详情界面
     */
    private void showBangumiItemInfo(int pos) {
        BangumiItem item = bangumiItemAdapter.getItem(pos);
        Bundle bundle = new Bundle();
        if (item != null) {
            bundle.putString("title", item.getTitle());
            bundle.putString("content", item.getContent());
            if (item.getExpandImage() != null)
                bundle.putString("url", item.getExpandImage().getFileUrl());
        }
        Intent intent = new Intent(getContext(), BangumiInfoActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    @Override
    public void onDestroy() {
        LogUtil.lazilyGetInstance().debug("Bangumi", "onDestroy");
        super.onDestroy();
        unbinder.unbind();
    }
}
