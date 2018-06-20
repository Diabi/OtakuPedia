package com.example.feng.otakuspedia.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.feng.otakuspedia.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.Bmob;

public class SplashActivity extends AppCompatActivity {

    private Unbinder unbinder;
    @BindView(R.id.img_splash_image)
    ImageView wallPaper;

    private static final int[] wallpapers = {
            R.drawable.chunwu1,
            R.drawable.chunwu2,
            R.drawable.chunwu3,
            R.drawable.chunwu4,
            R.drawable.chunwu5,
            R.drawable.chunwu6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Bmob.initialize(this, "8b3d64921bc289b3e28e15258d220bde");
        unbinder = ButterKnife.bind(this);

        randomSetWallPaper();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, 1500);
    }

    /**
     * 随机选取并设置壁纸
     */
    private void randomSetWallPaper() {
        int randomIndex = new Random().nextInt(wallpapers.length);
        Glide.with(SplashActivity.this).load(wallpapers[randomIndex]).into(wallPaper);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
