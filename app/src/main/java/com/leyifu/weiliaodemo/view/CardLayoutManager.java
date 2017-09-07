package com.leyifu.weiliaodemo.view;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hahaha on 2017/7/11 0011.
 */

public class CardLayoutManager extends RecyclerView.LayoutManager {

    //    在屏幕上显示的卡片数
    private static final int DEFAULT_SHOW_ITEM = 3;
    private static final float DEFAULT_SCALE = 0.1f;
    public static final int DEFAULT_TRANSLATE_Y = 14;
    private static final String TAG = "CardLayoutManager";
    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;

    public CardLayoutManager(RecyclerView recyclerView, ItemTouchHelper itemTouchHelper) {
        this.mRecyclerView = recyclerView;
        this.mItemTouchHelper = itemTouchHelper;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 用来实现item布局
     *
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
//      先移除所有的view
        removeAllViews();
//      在添加之前 先将所有的item  detach掉，然后再加入scra缓存
        detachAndScrapAttachedViews(recycler);

        int itemCount = getItemCount();
        if (itemCount > DEFAULT_SHOW_ITEM) {
            for (int position = DEFAULT_SHOW_ITEM; position >= 0; position--) {
                View view = recycler.getViewForPosition(position);
                //讲item添加到recycler中
                addView(view);
//              测量item
                measureChildWithMargins(view, 0, 0);
                //得到view的宽高
                int measuredWidth = getDecoratedMeasuredWidth(view);
                int measuredHeight = getDecoratedMeasuredHeight(view);
//              得到出去view剩余的宽高
                int spaWidth = getWidth() - measuredWidth;
                int spaHeight = getHeight() - measuredHeight;
                //布局  默认布局是放在 RecyclerView 中心
                layoutDecoratedWithMargins(view, spaWidth / 2, spaHeight / 2, spaWidth / 2 + measuredWidth, spaHeight / 2 + measuredHeight);

                if (position == DEFAULT_SHOW_ITEM) {
                    view.setScaleX(1 - (position - 1) * DEFAULT_SCALE);
                    view.setScaleY(1 - (position - 1) * DEFAULT_SCALE);
                    view.setTranslationY((position - 1) * view.getMeasuredHeight() / DEFAULT_TRANSLATE_Y);
                    Log.e(TAG, "measuredWidth: " + measuredWidth + " view.getMeasuredHeight()=" + view.getMeasuredHeight());
                } else if (position > 0) {
                    view.setScaleX(1 - position * DEFAULT_SCALE);
                    view.setScaleY(1 - position * DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / DEFAULT_TRANSLATE_Y);
                } else {
                    // 设置 mTouchListener 的意义就在于我们想让处于顶层的卡片是可以随意滑动的
                    // 而第二层、第三层等等的卡片是禁止滑动的
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        } else {
            // 当数据源个数小于或等于最大显示数时，和上面的代码差不多
            for (int postion = itemCount; postion >= 0; postion--) {
                View view = recycler.getViewForPosition(postion);
                addView(view);
                int measuredWidth = getDecoratedMeasuredWidth(view);
                int measuredHeight = getDecoratedMeasuredHeight(view);
                int spaWidth = getWidth() - measuredWidth;
                int spaHeight = getHeight() - measuredHeight;
                layoutDecoratedWithMargins(view, spaWidth / 2, spaHeight / 2, spaWidth / 2 + measuredWidth, spaHeight / 2 + measuredHeight);
                if (postion > 0) {
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        }
    }

    View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // 把触摸事件交给 mItemTouchHelper，让其处理卡片滑动事件
            RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(v);
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                mItemTouchHelper.startSwipe(childViewHolder);
            }
            return false;
        }
    };
}
