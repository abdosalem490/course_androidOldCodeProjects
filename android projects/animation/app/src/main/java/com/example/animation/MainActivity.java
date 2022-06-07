package com.example.animation;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
   // int x = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView1 = findViewById(R.id.imageView);
        //imageView1.animate().rotation(180).setDuration(2000);
        //imageView1.animate().scaleX(0.5f).scaleY(0.5f).setDuration(2000);
        imageView1.setTranslationX(-1000f);
        imageView1.setTranslationY(-1000f);
        ImageView imageView2 = findViewById(R.id.imageView2);
        View.OnClickListener listener = v -> {
            /*if (x == 0) {
                imageView1.animate().alpha(0f).setDuration(2000);
                imageView2.animate().alpha(1f).setDuration(2000);
                x = 1;
            }else {
                imageView2.animate().alpha(0f).setDuration(2000);
                imageView1.animate().alpha(1f).setDuration(2000);
                x = 0;
            }*/
            //imageView1.animate().translationXBy(-1000f).setDuration(2000);
            imageView1.animate().translationXBy(1000f).translationYBy(1000f).rotationBy(3600).setDuration(2000);
        };
        imageView1.setOnClickListener(listener);
        imageView2.setOnClickListener(listener);
    }
}