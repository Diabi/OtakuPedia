package com.example.feng.otakuspedia.adpter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.feng.otakuspedia.R;
import com.example.feng.otakuspedia.bean.BangumiItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng on 2018/6/27.
 */

public class BangumiItemAdapter extends BaseQuickAdapter<BangumiItem, BaseViewHolder> {

    private Context mContext;
    private List<BangumiItem> mList;

    public BangumiItemAdapter(int layoutResId, @Nullable List<BangumiItem> data, Context context) {
        super(layoutResId, data);
        mContext = context;
        mList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, BangumiItem item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_intro, item.getContent());
        if (item.getImage() != null) {
            Glide.with(mContext).load(item.getImage().getFileUrl())
                    .into((ImageView) helper.getView(R.id.iv_image));
        }
    }

    // 定义过滤器, 满足搜索功能需求
    public void setFilter(List<BangumiItem> list) {
        mList = new ArrayList<>();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    // 匹配搜索文本的动态变化
    public void animateTo(List<BangumiItem> items) {
        applyAndAnimateRemovals(items);
        applyAndAnimateAdditions(items);
        applyAndAnimateMovedItems(items);
    }

    // 动态删除搜索结果条目
    private void applyAndAnimateRemovals(List<BangumiItem> items) {
        for (int i = mList.size() - 1; i >= 0; i--) {
            final BangumiItem item = mList.get(i);
            if (!items.contains(item)) {
                removeItem(i);
            }
        }
    }

    private void removeItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    // 动态增加搜索结果条目
    private void applyAndAnimateAdditions(List<BangumiItem> items) {
        for (int i = 0, count = items.size(); i < count; i++) {
            final BangumiItem item = mList.get(i);
            if (!mList.contains(item)) {
                addItem(i, item);
            }
        }
    }

    private void addItem(int position, BangumiItem item) {
        mList.add(position, item);
        notifyItemInserted(position);
    }

    // 动态移动搜索结果条目
    private void applyAndAnimateMovedItems(List<BangumiItem> items) {
        for (int toPosition = items.size() - 1; toPosition >= 0; toPosition--) {
            final BangumiItem item = items.get(toPosition);
            final int fromPosition = mList.indexOf(item);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    private void moveItem(int fromPosition, int toPosition) {
        final BangumiItem item = mList.remove(fromPosition);
        mList.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }
}
