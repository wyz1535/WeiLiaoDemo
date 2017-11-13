package com.leyifu.weiliaodemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.leyifu.weiliaodemo.R;
import com.leyifu.weiliaodemo.adapter.VideosPlayAdapter;
import com.leyifu.weiliaodemo.bean.VideoBean;

public class VideosPlayActivity extends AppCompatActivity implements VideosPlayAdapter.OnItemClickLListener {

    private final String TAG = getClass().getSimpleName();
    private RecyclerView recycer_view_video;
    private VideosPlayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_play);

        recycer_view_video = ((RecyclerView) findViewById(R.id.recycer_view_video));

        LinearLayoutManager manager = new LinearLayoutManager(this);

        recycer_view_video.setLayoutManager(manager);

        adapter = new VideosPlayAdapter(this);
        recycer_view_video.setAdapter(adapter);

        adapter.setmOnItemClickLisntener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.recycle();
    }

    @Override
    public void onItemClick(View view, VideoBean videoBean) {
        Intent intent = new Intent(this,PlayActivity.class);
        intent.putExtra("videoBean", videoBean);
        startActivity(intent);

    }
}
