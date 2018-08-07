package com.example.feng.otakuspedia.adpter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.feng.otakuspedia.R;
import com.example.feng.otakuspedia.bean.CharacterItem;

import java.util.List;

/**
 * Created by Feng on 2018/6/29.
 */

public class CharacterItemAdapter extends BaseQuickAdapter<CharacterItem, BaseViewHolder> {

    private Context mContext;

    public CharacterItemAdapter(int layoutResId, @Nullable List<CharacterItem> data, Context context) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CharacterItem item) {
        helper.setText(R.id.cv_name, item.getName());
        helper.setText(R.id.cv_belong, item.getBelong());
        if (item.getImage() != null) {
            Glide.with(mContext).load(item.getImage().getFileUrl()).
                    into((ImageView) helper.getView(R.id.cv_image));
        }
        if (item.getGender().equals("female")) {
            Glide.with(mContext).load(R.mipmap.female)
                    .into((ImageView) helper.getView(R.id.cv_gender));
        } else {
            Glide.with(mContext).load(R.mipmap.male)
                    .into((ImageView) helper.getView(R.id.cv_gender));
        }
    }
}
