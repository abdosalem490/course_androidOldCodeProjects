package com.example.basicphrases;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void buttonTapped(View view)
    {
        int id = view.getId();
        String ourId = "";
        ourId = view.getResources().getResourceEntryName(id);
        int resourceId = getResources().getIdentifier(ourId , "raw" , "com.example.basicphrases");
        MediaPlayer mediaPlayer = MediaPlayer.create(this , resourceId);
        mediaPlayer.start();
        Log.d(TAG, "buttonTapped: " + ourId);
    }
}