package com.example.feng.otakuspedia.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.feng.otakuspedia.R;
import com.example.feng.otakuspedia.adpter.HomePagerAdapter;
import com.example.feng.otakuspedia.module.home.HomePageFragment;
import com.example.feng.otakuspedia.module.mine.MineFragment;
import com.example.feng.otakuspedia.util.ToastUtil;
import com.example.feng.otakuspedia.view.NoScrollViewPager;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    private Unbinder unbinder;
    @BindView(R.id.main_tab_bar)
    JPTabBar bottomTabBar;
    @BindView(R.id.main_viewpager)
    NoScrollViewPager mViewPager;

    @Titles
    public static final String[] titles = {"首页", "我的"};
    @SeleIcons
    public static final int[] selIcons = {R.mipmap.ic_home_sel, R.mipmap.ic_mine_sel};
    @NorIcons
    public static final int[] icons = {R.mipmap.ic_home, R.mipmap.ic_mine};

    private static boolean exitTag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {
        List<Fragment> list = new ArrayList<>();
        list.add(HomePageFragment.getInstance());
        list.add(MineFragment.getInstance());
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(adapter);
        bottomTabBar.setContainer(mViewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        if (!exitTag) {
            exitTag = true;
            ToastUtil.toast(MainActivity.this, "再按一次退出（ฅ´ω`ฅ）");
        } else {
            super.onBackPressed();
        }
    }
}