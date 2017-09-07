package com.leyifu.weiliaodemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.leyifu.weiliaodemo.CardItemTouchHelperCallback;
import com.leyifu.weiliaodemo.R;
import com.leyifu.weiliaodemo.Utils.CardConfig;
import com.leyifu.weiliaodemo.adpter.RecyclerViewCardAdapter;
import com.leyifu.weiliaodemo.interf.OnSwipeListener;
import com.leyifu.weiliaodemo.view.CardLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewCard extends AppCompatActivity {

    List<Integer> list = new ArrayList<Integer>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_card);

        initData();
        initView();
    }

    private void initView() {
        recyclerView = ((RecyclerView) findViewById(R.id.recyclerView));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new RecyclerViewCardAdapter(this,list));


        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(recyclerView.getAdapter(), list);
        cardCallback.setOnSwipedListener(new OnSwipeListener<Integer>() {

            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                RecyclerViewCardAdapter.ViewHolder myHolder = (RecyclerViewCardAdapter.ViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
                if (direction == CardConfig.SWIPING_LEFT) {
                    myHolder.iv_dislike.setAlpha(Math.abs(ratio));
                } else if (direction == CardConfig.SWIPING_RIGHT) {
                    myHolder.iv_like.setAlpha(Math.abs(ratio));
                } else {
                    myHolder.iv_dislike.setAlpha(0f);
                    myHolder.iv_like.setAlpha(0f);
                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Integer o, int direction) {
                RecyclerViewCardAdapter.ViewHolder myHolder = (RecyclerViewCardAdapter.ViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1f);
                myHolder.iv_dislike.setAlpha(0f);
                myHolder.iv_like.setAlpha(0f);
                Toast.makeText(RecyclerViewCard.this, direction == CardConfig.SWIPED_LEFT ? "swiped left" : "swiped right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipedClear() {
                Toast.makeText(RecyclerViewCard.this, "data clear", Toast.LENGTH_SHORT).show();
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }, 3000L);
            }

        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView, touchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(recyclerView);

    }

    private void initData() {
        list.add(R.drawable.img_avatar_01);
        list.add(R.drawable.img_avatar_02);
        list.add(R.drawable.img_avatar_03);
        list.add(R.drawable.img_avatar_04);
        list.add(R.drawable.img_avatar_05);
        list.add(R.drawable.img_avatar_06);
        list.add(R.drawable.img_avatar_07);
    }
}
