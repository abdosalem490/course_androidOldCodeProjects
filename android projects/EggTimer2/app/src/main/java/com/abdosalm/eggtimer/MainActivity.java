package com.abdosalm.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    CountDownTimer countDownTimer;
    Button goButton;
    boolean counterIsActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        goButton = findViewById(R.id.goButton);
        timerTextView = findViewById(R.id.countDownTextView);
        ImageView eggImageView = findViewById(R.id.eggImageView);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void buttonClicked(View view) {
        if (counterIsActive){
            counterIsActive = false;
            timerSeekBar.setEnabled(true);
            goButton.setText("GO!");
            countDownTimer.cancel();
        }else{
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("STOP!");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress()*1000+100,1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) (millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext() , R.raw.airhorn);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }

    }
    public void updateTimer(int secondLeft){
        int minutes = secondLeft/60;
        int seconds = secondLeft-minutes*60;
        timerTextView.setText(String.format(Locale.US , "%02d:%02d", minutes, seconds));
    }
    public void resetTimer(){
        timerSeekBar.setProgress(30);
        timerTextView.setText("00:30");
        counterIsActive = false;
        timerSeekBar.setEnabled(true);
        goButton.setText("GO!");
        countDownTimer.cancel();
    }
}