package com.leyifu.weiliaodemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.leyifu.weiliaodemo.R;
import com.leyifu.weiliaodemo.bean.VideoBean;
import com.leyifu.weiliaodemo.view.MyControl;

public class PlayActivity extends Activity {

    private final String TAG = PlayActivity.class.getSimpleName();
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

        if (videoBean == null) {

            videoBean = new VideoBean();

            Uri data = intent.getData();
            Cursor cursor = getContentResolver().query(data, null, null, null, null);
            while (cursor.moveToNext()) {
                videoBean.reuse(cursor);
            }
            cursor.close();
        }

        boolean isNext = intent.getBooleanExtra("next", false);
        boolean isPrev = intent.getBooleanExtra("prev", false);


        my_control.setPrevOrNext(isPrev, isNext);


//        videoView.setMediaController(new MediaController(this));
        my_control.setDataAndVideoView(videoBean, videoView);

        my_control.setOnExitPlayActListener(new MyControl.OnExitPlayAct() {
            @Override
            public void onExitListener() {
                finish();
            }

            @Override
            public void onPrevListener() {
                Intent data = new Intent("prev");
                setResult(RESULT_OK, data);
                Log.e(TAG, "onPrevListener: ");
                finish();
            }

            @Override
            public void onNextListener() {
                Intent data = new Intent("next");
                Log.e(TAG, "onNextListener: ");
                setResult(RESULT_OK, data);
                finish();
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        my_control.onDestroy();
    }
}
