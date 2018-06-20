package com.example.feng.otakuspedia.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.feng.otakuspedia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MnPediaActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private String url;

    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.edit_title)
    TextView editTitle;
    @BindView(R.id.loading_progress)
    ContentLoadingProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mn_pedia);

        unbinder = ButterKnife.bind(this);
        getUrlAndJump();
    }

    /**
     * 获取url并跳转到网页
     */
    private void getUrlAndJump() {
        Intent intent = getIntent();
        setEditTitle(intent.getStringExtra("title"));
        url = intent.getStringExtra("url");
        if (url != null && !TextUtils.isEmpty(url))
            setClientAction();
    }

    /**
     * 设置title
     */
    private void setEditTitle(String title) {
        editTitle.setText(title);
    }

    /**
     * 设置WebView的客户端行为
     */
    private void setClientAction() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                try {
                    progressBar.hide();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        configWebView();
        mWebView.loadUrl(url);
    }

    /**
     * WebView属性配置
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void configWebView() {
        WebSettings settings = mWebView.getSettings();

        // 允许JS交互
        settings.setJavaScriptEnabled(true);

        // 设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); // 将图片调整到适合的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        // 缩放操作
        settings.setSupportZoom(true); // 支持缩放，默认为true
        settings.setBuiltInZoomControls(true); // 设置内置的缩放控件
        settings.setDisplayZoomControls(false); // 隐藏原生的缩放控件

        // 不使用缓存，仅从网络获取数据
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
