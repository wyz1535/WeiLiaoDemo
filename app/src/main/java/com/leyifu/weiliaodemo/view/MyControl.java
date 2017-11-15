package com.leyifu.weiliaodemo.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.leyifu.weiliaodemo.R;
import com.leyifu.weiliaodemo.bean.VideoBean;
import com.leyifu.weiliaodemo.util.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hahaha on 2017/11/13 0013.
 */

public class MyControl extends FrameLayout {


    private TextView tv_controller_title;
    private TextView tv_controller_time;
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
    private SeekBar sb_controller_volume;
    private ImageView iv_controller_mute;
    private AudioManager audioManager;
    private ImageView iv_controller_exit;
    private ImageView iv_controller_play;
    private TextView tv_controller_passed_time;
    private TextView tv_controller_total_time;
    private SeekBar sb_controller_position;
    private ImageView iv_controller_prev;
    private ImageView iv_controller_next;

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
        sb_controller_volume = ((SeekBar) view.findViewById(R.id.sb_controller_volume));
        iv_controller_mute = ((ImageView) view.findViewById(R.id.iv_controller_mute));
        iv_controller_exit = ((ImageView) view.findViewById(R.id.iv_controller_exit));
        iv_controller_play = ((ImageView) view.findViewById(R.id.iv_controller_play));
        tv_controller_passed_time = ((TextView) view.findViewById(R.id.tv_controller_passed_time));
        tv_controller_total_time = ((TextView) view.findViewById(R.id.tv_controller_total_time));
        sb_controller_position = ((SeekBar) view.findViewById(R.id.sb_controller_position));
        iv_controller_prev = ((ImageView) view.findViewById(R.id.iv_controller_prev));
        iv_controller_next = ((ImageView) view.findViewById(R.id.iv_controller_next));


        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        sb_controller_volume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sb_controller_volume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        sb_controller_volume.setOnSeekBarChangeListener(seekBarVolumeListener);
        if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0) {
            iv_controller_mute.setImageResource(R.drawable.volume_off);
        }
        iv_controller_mute.setOnClickListener(muteVolumeListener);
        iv_controller_exit.setOnClickListener(exitClickListener);
        iv_controller_play.setOnClickListener(startAndStopPlayListener);
        iv_controller_prev.setOnClickListener(prevAndNextListener);
        iv_controller_next.setOnClickListener(prevAndNextListener);
        iv_controller_play.setImageResource(R.drawable.btn_pause_selector);

    }
        OnClickListener prevAndNextListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.iv_controller_prev:

                         break;
                    case R.id.iv_controller_next:

                        break;
                }
            }
        };

    boolean currentState;

    SeekBar.OnSeekBarChangeListener seekBarPositionListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                videoView.seekTo(progress);
                tv_controller_passed_time.setText(Utils.formatterTime(progress));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            currentState = videoView.isPlaying();
            if (currentState) {
                videoView.pause();
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (currentState) {
                videoView.start();
                handler.post(updataPlayPosition);
            }
        }
    };

    OnClickListener startAndStopPlayListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (videoView.isPlaying()) {
                videoView.pause();
                iv_controller_play.setImageResource(R.drawable.btn_play_selector);
            } else {
                videoView.start();
                iv_controller_play.setImageResource(R.drawable.btn_pause_selector);
                handler.post(updataPlayPosition);
            }
        }
    };

    OnClickListener exitClickListener = new OnClickListener() {


        @Override
        public void onClick(View v) {
            if (mOnExitPlayActListener != null) {
                mOnExitPlayActListener.onExitListener();

            }
        }
    };



    public void setOnExitPlayActListener(OnExitPlayAct onExitPlayActListener) {
        this.mOnExitPlayActListener = onExitPlayActListener;
    }

    private OnExitPlayAct mOnExitPlayActListener;


    public interface OnExitPlayAct {
        void onExitListener();
    }

    private int currentVolume = 5;
    OnClickListener muteVolumeListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0) {
                iv_controller_mute.setImageResource(R.drawable.volume_up);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
            } else {
                currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                iv_controller_mute.setImageResource(R.drawable.volume_off);
            }
        }
    };

    SeekBar.OnSeekBarChangeListener seekBarVolumeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public void onStart() {
        handler.post(updataTopTime);

        IntentFilter batteryFilter = new IntentFilter();
        batteryFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        getContext().registerReceiver(batteryReceiver, batteryFilter);

        IntentFilter volumeFilter = new IntentFilter();
        volumeFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
        getContext().registerReceiver(volumeReceiver, volumeFilter);

    }

    public void onStop() {
        handler.removeCallbacks(updataTopTime);

        getContext().unregisterReceiver(batteryReceiver);
        getContext().unregisterReceiver(volumeReceiver);
    }

    public void onDestroy(){
        int currentPositionPlay = videoView.getCurrentPosition();
        Log.e("onPrepared", "onDestroy: "+ currentPositionPlay);
        int id = videoBean.getId();
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        editor.putInt("currentPositionPlay", currentPositionPlay);
        editor.putInt("id", id);
        editor.apply();
    }

    BroadcastReceiver volumeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            sb_controller_volume.setProgress(currentVolume);
            if (currentVolume == 0) {
                iv_controller_mute.setImageResource(R.drawable.volume_off);
            } else {
                iv_controller_mute.setImageResource(R.drawable.volume_up);
            }
        }
    };


    BroadcastReceiver batteryReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            int maxBattery = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            int currentBattery = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            iv_controller_battery.setImageResource(batteryIcons[currentBattery / 10]);
        }
    };

    Runnable updataTopTime = new Runnable() {
        @Override
        public void run() {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String time = sdf.format(new Date());
            tv_controller_time.setText(time);

            handler.postDelayed(this, 1000);
        }
    };

    Runnable updataPlayPosition = new Runnable() {
        @Override
        public void run() {
            int currentPosition = videoView.getCurrentPosition();
            sb_controller_position.setProgress(currentPosition);
            tv_controller_passed_time.setText(Utils.formatterTime(currentPosition));

            if (videoView.isPlaying()) {
                handler.postDelayed(this, 50);
            }
        }
    };


    public void setDataAndVideoView(VideoBean videoBean, VideoView videoView) {

        this.videoBean = videoBean;
        this.videoView = videoView;

        tv_controller_title.setText(videoBean.getName());

        videoView.setVideoURI(Uri.fromFile(new File(videoBean.getPath())));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();

//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
//                int currentPositionPlay = sharedPreferences.getInt("currentPositionPlay", 0);
//                int id = sharedPreferences.getInt("id", 0);
//                int currentId = videoBean.getId();
//                Log.e("onPrepared", "currentPositionPlay: " + currentPositionPlay + " &id: " + id + " &currentId: " + currentId);
//                if (currentPositionPlay != 0) {
//                    videoView.seekTo(currentPositionPlay);
//                }

                tv_controller_total_time.setText(Utils.formatterTime(videoView.getDuration()));
                sb_controller_position.setMax(videoView.getDuration());
                sb_controller_position.setOnSeekBarChangeListener(seekBarPositionListener);
                handler.post(updataPlayPosition);
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                iv_controller_play.setImageResource(R.drawable.btn_play_selector);
            }
        });

    }

}
