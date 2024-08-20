package com.example.travelassistant.submit;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.travelassistant.R;

public class PlayVideoActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private Button play, pause, stop;
    private MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;
    private boolean noPlay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        play = (Button) findViewById(R.id.play1);
        pause = (Button) findViewById(R.id.pause1);
        stop = (Button) findViewById(R.id.stop1);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        pause.setEnabled(false);
        stop.setEnabled(false);
        /**
         * 实现播放功能
         */
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noPlay) {
                    play();
                    noPlay = false;
                } else {
                    mediaPlayer.start();
                }
            }
        });
        /**
         * 实现取消功能
         */
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                //返回主页面
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
        });
        /**
         * 实现上传功能
         */
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    noPlay = true;
                    pause.setEnabled(false);
                    stop.setEnabled(false);
                }
            }
        });

    }
    /**
     * 创建play()方法，在该方法中实现视频的播放功能
     */
    public void play() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDisplay(surfaceHolder);
        try {
            mediaPlayer.setDataSource(getExternalCacheDir()+"/demo.mp4");
            // mediaPlayer.setDataSource(Environment.getExternalStorageDirectory() + "/DCIM/Camera/video.mp4");
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        pause.setEnabled(true);
        stop.setEnabled(true);
        // 为MediaPlayer对象添加完成事件监听器
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(PlayVideoActivity.this, "视频播放完毕！", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 当前Activity销毁时，停止正在播放的视频，并释放MediaPlayer所占用的资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            // Activity销毁时停止播放，释放资源。不做这个操作，即使退出还是能听到视频播放的声音
            mediaPlayer.release();
        }
    }

}