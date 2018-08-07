package com.example.feng.otakuspedia.module.home.character;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.feng.otakuspedia.R;
import com.example.feng.otakuspedia.activity.BangumiInfoActivity;
import com.example.feng.otakuspedia.adpter.CharacterItemAdapter;
import com.example.feng.otakuspedia.bean.CharacterItem;
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

public class CharacterFragment extends Fragment {

    private View mView;
    private Unbinder unbinder;
    private List<CharacterItem> mList = new ArrayList<>();
    private CharacterItemAdapter bangumiItemAdapter;
    private static int loadFactor = 9;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.loading_progress)
    ContentLoadingProgressBar progressBar;

    public static CharacterFragment getInstance() { return new CharacterFragment(); }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.bangumi_fragment, container, false);
            unbinder = ButterKnife.bind(this, mView);
            loadCharacterItem();
            setOnPullRefresh();
        }
        return mView;
    }

    /**
     * 从云端读取数据
     */
    private void loadCharacterItem() {
        try {
            BmobQuery<CharacterItem> query = new BmobQuery<>();
            query.setLimit(loadFactor);
            query.order("-createdAt");
            query.findObjects(new FindListener<CharacterItem>() {
                @Override
                public void done(List<CharacterItem> list, BmobException e) {
                    if (e == null) {
                        onDataLoaded(list);
                    } else {
                        ToastUtil.toast(getContext(), "加载失败，请重试");
                        Log.e("读取失败", e.toString());
                    }
                    refreshLayout.setRefreshing(false);
                    progressBar.hide();
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据加载完成
     */
    private void onDataLoaded(List<CharacterItem> list) {
        mList = list;
        Collections.shuffle(list);
        createList();
    }

    /**
     * 生成列表
     */
    private void createList() {
        bangumiItemAdapter = new CharacterItemAdapter(R.layout.character_item, mList, getContext());
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(bangumiItemAdapter);
        bangumiItemAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN); // 开启动画
        setOnPullLoadMore();
        /*setOnItemClickedListener();*/
    }

    /**
     * 下拉刷新
     */
    private void setOnPullRefresh() {
        refreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadCharacterItem();
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
                        reloadCharacterItem();
                    }
                }, 1500);
            }
        }, mRecyclerView);
    }

    /**
     * 以新的加载因子从服务器读取数据
     */
    private void reloadCharacterItem() {
        BmobQuery<CharacterItem> query = new BmobQuery<>();
        query.setLimit(loadFactor);
        query.order("-createdAt");
        query.findObjects(new FindListener<CharacterItem>() {
            @Override
            public void done(List<CharacterItem> list, BmobException e) {
                if (e == null) {
                    onReloadSuccess(list);
                } else {
                    ToastUtil.toast(getContext(), "加载失败, 请重试");
                    Log.e("读取失败", e.toString());
                    bangumiItemAdapter.loadMoreFail();
                }
            }
        });
    }

    /**
     * 重新加载完成
     * @param list
     */
    private void onReloadSuccess(List<CharacterItem> list) {
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
     * 设置item点击监听
     */
    private void setOnItemClickedListener() {
        bangumiItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showCharacterItemInfo(position);
            }
        });
    }

    /**
     * 跳转到详情界面
     */
    private void showCharacterItemInfo(int pos) {
        CharacterItem item = bangumiItemAdapter.getItem(pos);
        Bundle bundle = new Bundle();
        if (item != null) {

        }
        Intent intent = new Intent(getContext(), BangumiInfoActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
