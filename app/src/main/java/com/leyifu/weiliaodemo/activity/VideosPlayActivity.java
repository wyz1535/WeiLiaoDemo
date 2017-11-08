package com.leyifu.weiliaodemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.leyifu.weiliaodemo.R;
import com.leyifu.weiliaodemo.adapter.VideosPlayAdapter;

public class VideosPlayActivity extends AppCompatActivity {

    private RecyclerView recycer_view_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_play);

        recycer_view_video = ((RecyclerView) findViewById(R.id.recycer_view_video));

        LinearLayoutManager manager = new LinearLayoutManager(this);

        recycer_view_video.setLayoutManager(manager);

        recycer_view_video.setAdapter(new VideosPlayAdapter(this));
    }
}
