package com.example.feng.otakuspedia.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.feng.otakuspedia.R;
import com.example.feng.otakuspedia.adpter.HomePagerAdapter;
import com.example.feng.otakuspedia.fragment.HomePageFragment;
import com.example.feng.otakuspedia.fragment.MineFragment;
import com.example.feng.otakuspedia.util.ToastUtil;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {

    private Unbinder unbinder;
    @BindView(R.id.main_tab_bar)
    JPTabBar tabBar;
    @BindView(R.id.main_viewpager)
    ViewPager mViewPager;

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
        /*Bmob.initialize(this, "8b3d64921bc289b3e28e15258d220bde");*/
        unbinder = ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {
        List<Fragment> list = new ArrayList<>();
        list.add(HomePageFragment.getInstance());
        list.add(MineFragment.getInstance());
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(adapter);
        tabBar.setContainer(mViewPager);
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