package com.abdosalm.tryme;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private View windowView;
    private Button tryMeButton;
    private int[] colors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random random = new Random();

        colors = new int[]{Color.RED,Color.CYAN,Color.GREEN,Color.BLUE,Color.BLACK,Color.DKGRAY,Color.LTGRAY,Color.MAGENTA,Color.YELLOW};

        windowView = findViewById(R.id.windowViewId);
        tryMeButton = findViewById(R.id.tryMeButton);

        tryMeButton.setOnClickListener(v->{
            int position = random.nextInt(9);
            windowView.setBackgroundColor(colors[position]);
        });

    }
}