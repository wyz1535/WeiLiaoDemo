package com.leyifu.weiliaodemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hahaha on 2017/7/28 0028.
 */

public class SuggestFragment extends Fragment {

    private TextView tv_fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_fragment, container, false);
        tv_fragment = ((TextView) view.findViewById(R.id.tv_fragment));
        tv_fragment.setTextColor(Color.RED);
        tv_fragment.setText("推荐");
        return view;

    }
}
