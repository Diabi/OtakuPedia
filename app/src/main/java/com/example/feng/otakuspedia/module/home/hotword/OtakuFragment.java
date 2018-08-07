package com.example.feng.otakuspedia.module.home.hotword;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.feng.otakuspedia.R;
import com.example.feng.otakuspedia.activity.OtakuInfoActivity;
import com.example.feng.otakuspedia.adpter.OtakuItemAdapter;
import com.example.feng.otakuspedia.bean.OtakuItem;
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
 * Created by Feng on 2018/6/17.
 */

public class OtakuFragment extends Fragment implements IHotWordView {

    private View mView;
    private Unbinder unbinder;
    private OtakuItemAdapter otakuItemAdapter;
    private List<OtakuItem> mList = new ArrayList<>();
    private static int loadFactor = 10;

    private HotWordPresenter hotWordPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.loading_progress)
    ContentLoadingProgressBar progressBar;

    /**
     * 生成HomePageFragment实例
     * @return
     */
    public static OtakuFragment getInstance() {
        return new OtakuFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hotWordPresenter = new HotWordPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.otaku_fragment, container, false);
            unbinder = ButterKnife.bind(this, mView);
            hotWordPresenter.getHotWordData(loadFactor);
            setOnPullRefresh();
        }
        return mView;
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
    public void loadMoreFailed(BmobException e) {
        ToastUtil.toast(getContext(), "加载失败, 请重试");
        Log.e("读取失败", e.toString());
        otakuItemAdapter.loadMoreFail();
    }

    @Override
    public void setHotWord(List<OtakuItem> list) {
        mList = list;
        Collections.shuffle(mList);
        createList();
    }

    @Override
    public void resetHotWord(List<OtakuItem> list) {
        if (mList.size() >= list.size()) {
            otakuItemAdapter.loadMoreEnd();
            return;
        }
        int size = mList.size();
        for (int i = size; i < list.size(); i++)
            mList.add(list.get(i));
        otakuItemAdapter.notifyDataSetChanged();
        otakuItemAdapter.loadMoreComplete();
    }

    /**
     * 生成列表
     */
    private void createList() {
        otakuItemAdapter = new OtakuItemAdapter(R.layout.otaku_item, mList, getContext());
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(otakuItemAdapter);
        otakuItemAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT); // 开启动画
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
                hotWordPresenter.getHotWordData(loadFactor);
            }
        });
    }

    /**
     * 上拉加载更多
     */
    private void setOnPullLoadMore() {
        otakuItemAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadFactor += 4;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hotWordPresenter.regainHotWordData(loadFactor);
                    }
                }, 1500);
            }
        }, mRecyclerView);
    }

    /**
     * 设置item点击监听
     */
    private void setOnItemClickedListener() {
        otakuItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showOtakuItemInfo(position);
            }
        });
    }

    /**
     * 跳转到详情界面
     */
    private void showOtakuItemInfo(int pos) {
        OtakuItem item = otakuItemAdapter.getItem(pos);
        Bundle bundle = new Bundle();
        if (item != null) {
            bundle.putString("title", item.getTitle());
            bundle.putString("definition", item.getDefinition());
            bundle.putString("representation", item.getRepresentation());
            bundle.putString("url", item.getUrl());
        }
        Intent intent = new Intent(getContext(), OtakuInfoActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
