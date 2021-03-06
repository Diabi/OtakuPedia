package com.example.feng.otakuspedia.adpter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.feng.otakuspedia.R;
import com.example.feng.otakuspedia.bean.OtakuItem;
import com.example.feng.otakuspedia.bean.OtakuItem;
import com.example.feng.otakuspedia.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng on 2018/6/17.
 */

public class OtakuItemAdapter extends BaseQuickAdapter<OtakuItem, BaseViewHolder> {
    private Context mContext;
    private List<OtakuItem> mList;

    public OtakuItemAdapter(int layoutResId, @Nullable List<OtakuItem> data, Context context) {
        super(layoutResId, data);
        mContext = context;
        mList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, OtakuItem item) {
        helper.setText(R.id.tv_content, item.getTitle());
        if (item.getImage() != null) {
            Glide.with(mContext).load(item.getImage().getUrl()).into((ImageView)helper.getView(R.id.iv_image));
        }
    }

    // 定义过滤器, 满足搜索功能需求
    public void setFilter(List<OtakuItem> list) {
        mList = new ArrayList<>();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    // 匹配搜索文本的动态变化
    public void animateTo(List<OtakuItem> items) {
        applyAndAnimateRemovals(items);
        applyAndAnimateAdditions(items);
        applyAndAnimateMovedItems(items);
    }

    // 动态删除搜索结果条目
    private void applyAndAnimateRemovals(List<OtakuItem> items) {
        for (int i = mList.size() - 1; i >= 0; i--) {
            final OtakuItem item = mList.get(i);
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
    private void applyAndAnimateAdditions(List<OtakuItem> items) {
        for (int i = 0, count = items.size(); i < count; i++) {
            final OtakuItem item = mList.get(i);
            if (!mList.contains(item)) {
                addItem(i, item);
            }
        }
    }

    private void addItem(int position, OtakuItem item) {
        mList.add(position, item);
        notifyItemInserted(position);
    }

    // 动态移动搜索结果条目
    private void applyAndAnimateMovedItems(List<OtakuItem> items) {
        for (int toPosition = items.size() - 1; toPosition >= 0; toPosition--) {
            final OtakuItem item = items.get(toPosition);
            final int fromPosition = mList.indexOf(item);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    private void moveItem(int fromPosition, int toPosition) {
        final OtakuItem item = mList.remove(fromPosition);
        mList.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }
}
