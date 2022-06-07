package com.abdosalm.playvideo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this , R.raw.outro_music);
        surfaceView = findViewById(R.id.surfaceView);
        surfaceView.setKeepScreenOn(true);

        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);
        holder.setFixedSize(400 , 300);

        Button play = findViewById(R.id.buttonPlay);
        play.setOnClickListener(v -> mediaPlayer.start());

        Button pause = findViewById(R.id.buttonPause);
        pause.setOnClickListener(v -> mediaPlayer.pause());

        Button skip = findViewById(R.id.buttonSkip);
        skip.setOnClickListener(v -> mediaPlayer.seekTo(mediaPlayer.getDuration()/2));
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mediaPlayer.setDisplay(holder);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null){
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}