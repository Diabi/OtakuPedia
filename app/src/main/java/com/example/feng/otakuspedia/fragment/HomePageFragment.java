package com.example.feng.otakuspedia.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.feng.otakuspedia.R;
import com.example.feng.otakuspedia.adpter.PartitionPagerAdapter;
import com.example.feng.otakuspedia.fragment.home.OtakuFragment;

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
        list.add(OtakuFragment.getInstance());
        list.add(OtakuFragment.getInstance());
        PartitionPagerAdapter adapter = new PartitionPagerAdapter(getChildFragmentManager(), list);
        viewPager.setAdapter(adapter);
        topTabBar.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
