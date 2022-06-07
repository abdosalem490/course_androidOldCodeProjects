package com.abdosalm.playmedia;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Button playButton;
    private SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = findViewById(R.id.playButton);
        seekBar = findViewById(R.id.seekBar);
        mediaPlayer = new MediaPlayer();

        //mediaPlayer = MediaPlayer.create(this , R.raw.complete);

        try {
            mediaPlayer.setDataSource("http://buildappswithpaulo.com/music/watch_me.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnCompletionListener(mp -> {
            int duration = mp.getDuration();
            Toast.makeText(this, (duration/1000)/60, Toast.LENGTH_SHORT).show();
        });
        MediaPlayer.OnPreparedListener preparedListener =  new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mediaPlayer.getDuration());
                playButton.setOnClickListener(v->{
                    if (mediaPlayer.isPlaying()){
                        if (mp != null){
                            mp.pause();
                            playButton.setText(R.string.play_text);
                        }
                    }else{
                        mp.start();
                        playButton.setText(R.string.pause_text);
                    }
                });
            }
        };
        mediaPlayer.setOnPreparedListener(preparedListener);
        mediaPlayer.prepareAsync();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    /*public void pauseMusic(){
        if (mediaPlayer != null){
            mediaPlayer.pause();
            playButton.setText(R.string.play_text);
        }
    }
    public void playMusic(){
        if (mediaPlayer != null){
            mediaPlayer.start();
            playButton.setText(R.string.pause_text);
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.pause();
            mediaPlayer.release();
        }
    }
}