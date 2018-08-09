package com.example.feng.otakuspedia.module.home;

import android.content.Context;
import android.content.Intent;
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
import com.example.feng.otakuspedia.activity.SearchActivity;
import com.example.feng.otakuspedia.adpter.PartitionPagerAdapter;
import com.example.feng.otakuspedia.bean.MessageEvent;
import com.example.feng.otakuspedia.module.home.bangumi.BangumiFragment;
import com.example.feng.otakuspedia.module.home.character.CharacterFragment;
import com.example.feng.otakuspedia.module.home.hotword.OtakuFragment;
import com.example.feng.otakuspedia.util.LogUtil;
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

    /**
     * 生成HomePageFragment实例
     * @return
     */
    public static HomePageFragment getInstance() {
        return new HomePageFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
            createInnerPager();

        }
        return mView;
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
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