package com.abdosalm.playvideoexo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media2.exoplayer.external.ExoPlayerFactory;
import androidx.media2.exoplayer.external.SimpleExoPlayer;
import androidx.media2.exoplayer.external.trackselection.DefaultTrackSelector;

import com.google.android.exoplayer2.ui.PlayerView;

public class MainActivity extends AppCompatActivity {
    private PlayerView playerView;
    private SimpleExoPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView = findViewById(R.id.playerView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //player = new ExoPlayerFactory().newSimpleInstance(this, new DefaultTrackSelector());
    }
}