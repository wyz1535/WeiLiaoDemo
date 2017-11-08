package com.leyifu.weiliaodemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyifu.weiliaodemo.R;

import java.util.List;

/**
 * Created by hahaha on 2017/7/11 0011.
 */

public class RecyclerViewCardAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private List<Integer> list;

    public RecyclerViewCardAdapter(Context context, List<Integer> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).iv_avatar.setImageResource(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_avatar;
        public ImageView iv_dislike;
        public ImageView iv_like;

        public ViewHolder(View view) {
            super(view);
            iv_avatar = ((ImageView) view.findViewById(R.id.iv_avatar));
            iv_dislike = ((ImageView) view.findViewById(R.id.iv_dislike));
            iv_like = ((ImageView) view.findViewById(R.id.iv_like));
        }
    }
}
