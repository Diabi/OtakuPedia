package com.example.feng.otakuspedia.module.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.feng.otakuspedia.R;
import com.example.feng.otakuspedia.adpter.PartitionPagerAdapter;
import com.example.feng.otakuspedia.bean.MessageEvent;
import com.example.feng.otakuspedia.module.home.bangumi.BangumiFragment;
import com.example.feng.otakuspedia.module.home.character.CharacterFragment;
import com.example.feng.otakuspedia.module.home.hotword.OtakuFragment;
import com.example.feng.otakuspedia.util.ToastUtil;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Feng on 2018/6/17.
 */

public class HomePageFragment extends Fragment {

    private View mView;
    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp_viewpager)
    ViewPager viewPager;
    @BindView(R.id.tl_home)
    TabLayout topTabBar;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    /**
     * 生成HomePageFragment实例
     * @return
     */
    public static HomePageFragment getInstance() {
        return new HomePageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.homepage_fragment, container, false);
            unbinder = ButterKnife.bind(this, mView);
            initSearchView();
            createInnerPager();

        }
        return mView;
    }

    /**
     * 初始化搜索框
     */
    private void initSearchView() {
        searchView.setVoiceSearch(false);
        searchView.setEllipsize(true);
        setListenerForSearchView();
    }

    // 给搜索框添加监听事件
    private void setListenerForSearchView() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (topTabBar.getSelectedTabPosition() == 0)
                    EventBus.getDefault().post(new MessageEvent(query,"OtakuItem"));
                else if (topTabBar.getSelectedTabPosition() == 1)
                    EventBus.getDefault().post(new MessageEvent(query,"BangumiItem"));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {}

            @Override
            public void onSearchViewClosed() {
                if (topTabBar.getSelectedTabPosition() == 0)
                    EventBus.getDefault().post(new MessageEvent("","OtakuItem"));
                else if (topTabBar.getSelectedTabPosition() == 1)
                    EventBus.getDefault().post(new MessageEvent("","BangumiItem"));
            }
        });
    }

    /**
     * 构造首页内部的ViewPager
     */
    private void createInnerPager() {
        List<Fragment> list = new ArrayList<>();
        list.add(OtakuFragment.getInstance());
        list.add(BangumiFragment.getInstance());
        list.add(CharacterFragment.getInstance());
        PartitionPagerAdapter adapter = new PartitionPagerAdapter(getChildFragmentManager(), list);
        viewPager.setAdapter(adapter);
        topTabBar.setupWithViewPager(viewPager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.setTitle("");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my_menu, menu);
        /*MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                ToastUtil.toast(getContext(), "搜索");
                break;
            case R.id.action_sort:
                ToastUtil.toast(getContext(), "排序");
                break;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}