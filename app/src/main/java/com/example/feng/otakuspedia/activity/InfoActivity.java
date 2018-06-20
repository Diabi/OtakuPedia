package com.example.feng.otakuspedia.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.example.feng.otakuspedia.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class InfoActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.definition)
    TextView definition;
    @BindView(R.id.representation)
    TextView representation;
    @BindView(R.id.tv_link)
    TextView link;

    private String url;
    private String mtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        unbinder = ButterKnife.bind(this);
        getAndShowInfo();
    }

    @OnClick(R.id.tv_link)
    public void onViewClicked() {
        Intent intent = new Intent(InfoActivity.this, MnPediaActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", mtitle);
        startActivity(intent);
    }

    /**
     * 把数据渲染到对应的控件位置
     */
    private void getAndShowInfo() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mtitle = bundle.getString("title", "");
            title.setText(mtitle);

            SpannableString ss = setKeyWordColor(bundle.getString("definition", ""));
            definition.setText(ss);
            ss = setKeyWordColor(bundle.getString("representation", ""));
            representation.setText(ss);
            url = bundle.getString("url", "");
        }
    }

    /**
     * 设置关键字颜色
     * @param text
     * @return
     */
    private SpannableString setKeyWordColor(String text) {
        SpannableString spannableString = new SpannableString(text);
        int color = getResources().getColor(R.color.colorPrimary);
        Pattern pattern = Pattern.compile(mtitle);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            spannableString.setSpan(new ForegroundColorSpan(color), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
