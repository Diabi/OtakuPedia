package com.example.feng.otakuspedia.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.feng.otakuspedia.R;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BangumiInfoActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content)
    TextView bContent;
    @BindView(R.id.bangumi_image)
    PhotoView bImage;

    private String title;
    private String content;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangumi_info);
        unbinder = ButterKnife.bind(this);

        readBangumiData();
    }

    /**
     * 取出从BangumiFragment传过来的数据
     */
    private void readBangumiData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            title = bundle.getString("title", "otakupedia");
            content = bundle.getString("content", "");
            url = bundle.getString("url", "");
        }
        setBangumiData();
    }

    /**
     * 设置番剧信息
     */
    private void setBangumiData() {
        toolbar.setTitle(title);
        bContent.setText(content);
        if (url != null && !url.isEmpty()) {
            Glide.with(BangumiInfoActivity.this).load(url).into(bImage);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}