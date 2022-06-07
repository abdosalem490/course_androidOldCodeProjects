package com.abdosalm.seekbar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        resultTextView = findViewById(R.id.resultId);

        resultTextView.setText(String.format(Locale.US,"Pain Level %d/%d", seekBar.getProgress(), seekBar.getMax()));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                resultTextView.setText(String.format(Locale.US,"Pain Level %d/%d", seekBar.getProgress(), seekBar.getMax()));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() >= 7){
                    resultTextView.setTextColor(Color.RED);
                }else{
                    resultTextView.setTextColor(Color.GRAY);
                }
            }
        });
    }
}