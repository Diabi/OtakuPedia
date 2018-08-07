package com.example.feng.otakuspedia.module.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.feng.otakuspedia.R;
import com.example.feng.otakuspedia.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Feng on 2018/6/17.
 */

public class MineFragment extends Fragment {

    private View mView;
    private Unbinder unbinder;

    @BindView(R.id.rl_my_pace)
    RelativeLayout mPaceLayout;
    @BindView(R.id.rl_wanna_know)
    RelativeLayout mWannaknowLayout;
    @BindView(R.id.iv_circleimage)
    CircleImageView imageView;

    public static MineFragment getInstance() {
        return new MineFragment();
    }

    @OnClick({R.id.rl_my_pace, R.id.rl_wanna_know, R.id.iv_circleimage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_my_pace:
            case R.id.rl_wanna_know:
            case R.id.iv_circleimage:
                ToastUtil.toast(getContext(), "才...才不是希望你触碰我呢！(功能未开放敬请期待)");
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.mine_fragment, container, false);
            unbinder = ButterKnife.bind(this, mView);
        }
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
