package com.waltonbd.agingtest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR;

public class MainActivity extends AppCompatActivity {
    VideoView videoView1;
    private MediaController mediaController;
    AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        videoView1 = (VideoView)findViewById(R.id.videoView);

        //Video Loop
        videoView1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

       // videoView1.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.video);
        // videoView1.setVideoPath(String.valueOf(R.raw.videoo));
        videoView1.requestFocus();
        mediaController = new MediaController(this);
       // mediaController.setAnchorView(videoView1);
        videoView1.setMediaController(mediaController);

        audioManager = (AudioManager)getSystemService(this.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);

        videoView1.start();


    }


    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();
        videoView1.stopPlayback();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }



}
