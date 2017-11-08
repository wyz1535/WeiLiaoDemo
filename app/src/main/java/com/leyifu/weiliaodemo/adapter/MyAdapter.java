package com.leyifu.weiliaodemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyifu.weiliaodemo.R;
import com.leyifu.weiliaodemo.bean.MIUiBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hahaha on 2017/7/7 0007.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context mContext;
    private List<MIUiBean> list;

    public MyAdapter(List<MIUiBean> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext=parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MIUiBean miUiBean = list.get(position);
        holder.textview.setText(miUiBean.getName());
        holder.circleImageView.setImageResource(miUiBean.getImageId());
    }



    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView circleImageView;
        private final TextView textview;

        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView = ((CircleImageView) itemView.findViewById(R.id.circleImageView));
            textview = ((TextView) itemView.findViewById(R.id.textview));
        }
    }
}
