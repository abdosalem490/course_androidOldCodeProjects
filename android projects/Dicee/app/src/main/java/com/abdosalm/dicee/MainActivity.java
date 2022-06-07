package com.abdosalm.dicee;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rollButton = findViewById(R.id.rollButton);
        ImageView leftImageView = findViewById(R.id.leftImageView);
        ImageView rightImageView = findViewById(R.id.rightImageView);

        final int[] diceArray = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};
        Random random = new Random();

        rollButton.setOnClickListener(v -> {
            int number = random.nextInt(6);
            leftImageView.setImageResource(diceArray[number]);
            number = random.nextInt(6);
            rightImageView.setImageResource(diceArray[number]);

        });

    }
}