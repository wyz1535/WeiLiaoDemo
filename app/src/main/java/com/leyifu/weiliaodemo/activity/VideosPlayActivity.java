package com.leyifu.weiliaodemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import com.leyifu.weiliaodemo.R;
import com.leyifu.weiliaodemo.adapter.VideosPlayAdapter;
import com.leyifu.weiliaodemo.bean.VideoBean;

public class VideosPlayActivity extends AppCompatActivity implements VideosPlayAdapter.OnItemClickLListener {

    private final String TAG = getClass().getSimpleName();
    private RecyclerView recycer_view_video;
    private VideosPlayAdapter adapter;
    private int beforePositin;

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
    public void onItemClick(View view, VideoBean videoBean, int position) {
        startNextOrPrevActivity(videoBean, position);
    }

    private void startNextOrPrevActivity(VideoBean videoBean, int position) {
        beforePositin = position;
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("videoBean", videoBean);
        intent.putExtra("next", position < adapter.getItemCount() - 1);
        intent.putExtra("prev", position > 0);
        startActivityForResult(intent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
            String action = data.getAction();
            int targetPosition = -1;
            if (action.equals("prev")) {
                targetPosition = beforePositin - 1;
            } else if (action.equals("next")) {
                targetPosition = beforePositin + 1;
            }
            VideoBean videoBean = adapter.getItem(targetPosition);
            startNextOrPrevActivity(videoBean, targetPosition);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            String url = "http://v.cntv.cloudcdn.net/flash/mp4video52/TMS/2016/05/26/ab29c19ff8cc443b9211693171fa4cfe_h264418000nero_aac32.mp4";
            VideoBean videoBean = new VideoBean();
            videoBean.setPath(url);
            videoBean.setName("强哥引领大数据");

            Intent intent = new Intent(this, PlayActivity.class);
            intent.putExtra("videoBean", videoBean);
            startActivityForResult(intent,200);
        }
        return super.onKeyDown(keyCode, event);
    }
}
