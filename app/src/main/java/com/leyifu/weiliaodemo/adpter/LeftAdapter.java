package com.leyifu.weiliaodemo.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyifu.weiliaodemo.R;

import java.util.List;

/**
 * Created by hahaha on 2017/7/18 0018.
 */

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.ViewHoler> {

    private List<Integer> list;

    public LeftAdapter(List<Integer> list) {
        this.list = list;
    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left,null);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        holder.text_view.setText(list.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHoler extends RecyclerView.ViewHolder {

        TextView text_view;

        public ViewHoler(View view) {
            super(view);
            text_view= (TextView) view.findViewById(R.id.text_view);

        }
    }
}
