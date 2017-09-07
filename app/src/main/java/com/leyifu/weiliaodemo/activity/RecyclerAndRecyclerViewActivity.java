package com.leyifu.weiliaodemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.leyifu.weiliaodemo.R;
import com.leyifu.weiliaodemo.adpter.LeftAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAndRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView rcv_left;
    private RecyclerView rcv_right;

    List<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_and_recycler_view);

        initUI();
        init();
    }


    private void initUI() {
        rcv_left = ((RecyclerView) findViewById(R.id.rcv_left));
        rcv_right = ((RecyclerView) findViewById(R.id.rcv_right));

        for (int i = 0; i < 5000; i++) {
            list.add(i);
        }

    }

    private void init() {
        GridLayoutManager gridLayoutManagerLeft = new GridLayoutManager(this, 1);
        GridLayoutManager gridLayoutManagerRight = new GridLayoutManager(this, 10);
        rcv_left.setLayoutManager(gridLayoutManagerLeft);
        rcv_right.setLayoutManager(gridLayoutManagerRight);

        rcv_left.setAdapter(new LeftAdapter(list));
        rcv_right.setAdapter(new LeftAdapter(list));

    }

}
