package com.leyifu.weiliaodemo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    private boolean isPrev;
    private boolean isNext;

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
    private ImageView iv_controller_full_screen;
    private LinearLayout ll_controller_top;
    private LinearLayout ll_controller_bottom;
    private LinearLayout ll_controller_loading_container;
    private LinearLayout ll_controller_buffering_container;

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
        iv_controller_full_screen = ((ImageView) view.findViewById(R.id.iv_controller_full_screen));
        ll_controller_top = ((LinearLayout) view.findViewById(R.id.ll_controller_top));
        ll_controller_bottom = ((LinearLayout) view.findViewById(R.id.ll_controller_bottom));
        ll_controller_loading_container = ((LinearLayout) view.findViewById(R.id.ll_controller_loading_container));
        ll_controller_buffering_container = ((LinearLayout) view.findViewById(R.id.ll_controller_buffering_container));

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
        iv_controller_full_screen.setOnClickListener(fullScreenListener);
        iv_controller_play.setImageResource(R.drawable.btn_pause_selector);

        iv_controller_prev.setEnabled(false);
        iv_controller_next.setEnabled(false);
    }

    Runnable hideAnimator = new Runnable() {
        @Override
        public void run() {
            ObjectAnimator topAnimator = ObjectAnimator.ofFloat(ll_controller_top, "translationY", 0, -ll_controller_top.getHeight());
            topAnimator.setDuration(2000);
            topAnimator.start();

            ObjectAnimator bottomAnimator = ObjectAnimator.ofFloat(ll_controller_bottom, "translationY", 0, ll_controller_bottom.getHeight());
            bottomAnimator.setDuration(2000);
            bottomAnimator.start();
        }
    };

    Runnable showAnimator = new Runnable() {
        @Override
        public void run() {
            ObjectAnimator topAnimator = ObjectAnimator.ofFloat(ll_controller_top, "translationY", ll_controller_top.getTranslationY(), 0);
            topAnimator.setDuration(1000);
            topAnimator.start();

            ObjectAnimator bottomAnimator = ObjectAnimator.ofFloat(ll_controller_bottom, "translationY", ll_controller_bottom.getTranslationY(), 0);
            bottomAnimator.setDuration(1000);
            bottomAnimator.start();

            topAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    handler.postDelayed(hideAnimator, 1500);
                }
            });
        }
    };

    private void hideOrShowAnimation() {
        handler.removeCallbacks(hideAnimator);
        handler.removeCallbacks(showAnimator);
        handler.post(showAnimator);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_MOVE:

            case MotionEvent.ACTION_UP:
                hideOrShowAnimation();
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    boolean isFullScreen;
    OnClickListener fullScreenListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isFullScreen) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                videoView.setLayoutParams(params);
                isFullScreen = false;
            } else {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                videoView.setLayoutParams(params);
                isFullScreen = true;
            }

        }
    };

    OnClickListener prevAndNextListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_controller_prev:
                    if (mOnExitPlayActListener != null) {
                        Log.e("onclick", "onClick: prev");
                        mOnExitPlayActListener.onPrevListener();
                    }
                    break;
                case R.id.iv_controller_next:
                    Log.e("onclick", "onClick: next");
                    if (mOnExitPlayActListener != null) {
                        mOnExitPlayActListener.onNextListener();
                    }
                    break;
                default:
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


    private OnExitPlayAct mOnExitPlayActListener;

    public void setOnExitPlayActListener(OnExitPlayAct onExitPlayActListener) {
        this.mOnExitPlayActListener = onExitPlayActListener;
    }

    public void setPrevOrNext(boolean isPrev, boolean isNext) {
        this.isPrev = isPrev;
        this.isNext = isNext;
    }


    public interface OnExitPlayAct {
        void onExitListener();

        void onPrevListener();

        void onNextListener();
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

    public void onDestroy() {
        int currentPositionPlay = videoView.getCurrentPosition();
        Log.e("onPrepared", "onDestroy: " + currentPositionPlay);
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

//            int maxBattery = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
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

    Runnable isPrevOrNextRunnable = new Runnable() {
        @Override
        public void run() {
            iv_controller_prev.setEnabled(isPrev);
            iv_controller_next.setEnabled(isNext);
        }
    };


//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setDataAndVideoView(VideoBean videoBean, VideoView videoView) {

        this.videoBean = videoBean;
        this.videoView = videoView;

        tv_controller_title.setText(videoBean.getName());

        String path = videoBean.getPath();
        if (path.startsWith("http")) {
            videoView.setVideoURI(Uri.parse(path));
        } else {
            videoView.setVideoURI(Uri.fromFile(new File(videoBean.getPath())));
        }

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

                handler.postDelayed(isPrevOrNextRunnable, 500);

//                handler.postDelayed(hideAnimator, 2000);
                hideOrShowAnimation();

                ll_controller_loading_container.setVisibility(GONE);
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                iv_controller_play.setImageResource(R.drawable.btn_play_selector);
            }
        });

        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        ll_controller_buffering_container.setVisibility(VISIBLE);
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        ll_controller_buffering_container.setVisibility(GONE);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }

}
