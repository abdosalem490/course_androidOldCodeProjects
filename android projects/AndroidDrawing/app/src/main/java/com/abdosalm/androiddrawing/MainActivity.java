package com.abdosalm.androiddrawing;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Drawing drawing = new Drawing(this);
        CustomTextView customTextView  =  new CustomTextView(this , null);
        setContentView(customTextView);

    }
}