package com.leyifu.weiliaodemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.VideoView;

import com.leyifu.weiliaodemo.R;
import com.leyifu.weiliaodemo.bean.VideoBean;
import com.leyifu.weiliaodemo.view.MyControl;

public class PlayActivity extends Activity {

    private final String TAG = getClass().getSimpleName();
    private VideoView videoView;
    private MyControl my_control;
    private VideoBean videoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        videoView = (VideoView) findViewById(R.id.video_view);
        my_control = ((MyControl) findViewById(R.id.my_control));

        init();
    }

    private void init() {
        Intent intent = getIntent();
        videoBean = intent.getParcelableExtra("videoBean");

//        videoView.setMediaController(new MediaController(this));
        my_control.setDataAndVideoView(videoBean,videoView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        my_control.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        my_control.onStop();
    }
}
