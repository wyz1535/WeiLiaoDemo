package com.leyifu.weiliaodemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.leyifu.weiliaodemo.R;
import com.leyifu.weiliaodemo.adpter.MyAdapter;
import com.leyifu.weiliaodemo.bean.MIUiBean;
import com.leyifu.weiliaodemo.view.RecycleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecycleGridViewActivity extends AppCompatActivity {

    private RecycleView my_recycler;

    MIUiBean[] imgs = new MIUiBean[]{new MIUiBean("赵丽颖", R.drawable.girl),
            new MIUiBean("zhaoliying", R.drawable.girl_1)};

    List<MIUiBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_grid_view);

        initImg();

        my_recycler = ((RecycleView) findViewById(R.id.my_recycler));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        my_recycler.setLayoutManager(gridLayoutManager);
        my_recycler.setAdapter(new MyAdapter(list));
    }

    private void initImg() {
        list.clear();
        for (int i = 0; i < 52; i++) {
            Random random = new Random();
            int nextInt = random.nextInt(imgs.length);
            list.add(imgs[nextInt]);
        }

    }


}
