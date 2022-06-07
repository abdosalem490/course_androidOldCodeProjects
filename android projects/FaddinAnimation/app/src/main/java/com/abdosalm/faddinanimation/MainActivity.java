package com.abdosalm.faddinanimation;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView bartImageView = findViewById(R.id.bartImageView);
        bartImageView.setX(-1500);
        bartImageView.animate().translationXBy(1500).rotation(3600).setDuration(2000);
    }

    public void fade(View view){
        ImageView bartImageView = findViewById(R.id.bartImageView);
        ImageView homerImageView = findViewById(R.id.homeImageView);

        bartImageView.animate().scaleX(0.5f).scaleY(0.5f).setDuration(1000);
    }

}