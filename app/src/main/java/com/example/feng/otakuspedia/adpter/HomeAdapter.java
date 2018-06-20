package com.example.feng.otakuspedia.adpter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.feng.otakuspedia.R;
import com.example.feng.otakuspedia.bean.OtakuItem;

import java.util.List;

/**
 * Created by Feng on 2018/6/17.
 */

public class HomeAdapter extends BaseQuickAdapter<OtakuItem, BaseViewHolder> {
    private Context mContext;

    public HomeAdapter(int layoutResId, @Nullable List<OtakuItem> data, Context context) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, OtakuItem item) {
        helper.setText(R.id.tv_content, item.getTitle());
        if (item.getImage() != null) {
            Glide.with(mContext).load(item.getImage().getUrl()).into((ImageView)helper.getView(R.id.iv_image));
        }
    }
}
