package com.example.timersdemo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Handler handler = new Handler();
        Runnable run= new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: ");
                handler.postDelayed(this , 1000);
            }
        };
        handler.post(run);*/
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick: seconds left "+ millisUntilFinished/1000) ;
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: done");
            }
        }.start();
    }
}