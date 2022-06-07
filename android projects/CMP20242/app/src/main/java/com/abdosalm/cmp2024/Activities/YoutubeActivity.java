package com.abdosalm.cmp2024.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.abdosalm.cmp2024.R;

import java.util.Objects;

public class YoutubeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.youtube_channels);

        // we will use nested recycler views


    }
}