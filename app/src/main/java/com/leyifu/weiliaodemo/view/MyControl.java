package com.leyifu.weiliaodemo.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.leyifu.weiliaodemo.R;
import com.leyifu.weiliaodemo.bean.VideoBean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hahaha on 2017/11/13 0013.
 */

public class MyControl extends FrameLayout {


    private TextView tv_controller_title;
    private TextView tv_controller_time;
    private TextView tv_controller_total_time;
    private ImageView iv_controller_battery;

    private VideoBean videoBean;
    private VideoView videoView;

    Handler handler = new Handler();

    private int[] batteryIcons = new int[]{
            R.drawable.ic_battery_0,
            R.drawable.ic_battery_10,
            R.drawable.ic_battery_20,
            R.drawable.ic_battery_20,
            R.drawable.ic_battery_40,
            R.drawable.ic_battery_40,
            R.drawable.ic_battery_60,
            R.drawable.ic_battery_60,
            R.drawable.ic_battery_80,
            R.drawable.ic_battery_80,
            R.drawable.ic_battery_100,
    };

    public MyControl(@NonNull Context context) {
        this(context, null);
    }

    public MyControl(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyControl(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_controller, this);

        tv_controller_title = ((TextView) view.findViewById(R.id.tv_controller_title));
        tv_controller_time = ((TextView) view.findViewById(R.id.tv_controller_time));
        iv_controller_battery = ((ImageView) view.findViewById(R.id.iv_controller_battery));

        tv_controller_total_time = ((TextView) view.findViewById(R.id.tv_controller_total_time));


    }

    public void onStart() {
        handler.post(updataTime);

        IntentFilter batteryFilter = new IntentFilter();
        batteryFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        getContext().registerReceiver(batteryReceiver, batteryFilter);

    }

    public void onStop() {
        handler.removeCallbacks(updataTime);

        getContext().unregisterReceiver(batteryReceiver);
    }

    BroadcastReceiver batteryReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            int maxBattery = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            int currentBattery = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            Log.e("battery", "maxBattery: " + maxBattery + "&:currentBattery " + currentBattery);
            iv_controller_battery.setImageResource(batteryIcons[currentBattery / 10]);
        }
    };

    Runnable updataTime = new Runnable() {
        @Override
        public void run() {

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String time = sdf.format(new Date());
            tv_controller_time.setText(time);

            handler.postDelayed(this, 1000);
        }
    };

    public void setDataAndVideoView(VideoBean videoBean, VideoView videoView) {

        this.videoBean = videoBean;
        this.videoView = videoView;
        Log.e("setDataAndVideoView", "setDataAndVideoView: " + videoBean);

        tv_controller_title.setText(videoBean.getName());

        videoView.setVideoURI(Uri.fromFile(new File(videoBean.getPath())));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
    }
}
