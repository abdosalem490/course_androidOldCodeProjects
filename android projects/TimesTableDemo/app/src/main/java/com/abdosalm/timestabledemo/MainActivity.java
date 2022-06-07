package com.abdosalm.timestabledemo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView timesTablesListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar timesTablesSeekBar =  findViewById(R.id.timesTableSeekBar);
        timesTablesListView = findViewById(R.id.timesTablesListView);

        int max = 20;
        int startingPosition = 10;
        
        timesTablesSeekBar.setMax(max);
        timesTablesSeekBar.setProgress(startingPosition);
        generateTimesTable(startingPosition);
        timesTablesSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min = 1;
                int timesTableNumber;
                if (progress < min) {
                    timesTableNumber = min;
                    seekBar.setProgress(min);
                }
                else
                    timesTableNumber = progress;

                generateTimesTable(timesTableNumber);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void generateTimesTable(int timesTableNumber){
        ArrayList<String>timesTableContent = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            timesTableContent.add(Integer.toString(i*timesTableNumber));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplication() , android.R.layout.simple_list_item_1 , timesTableContent);
        timesTablesListView.setAdapter(arrayAdapter);
    }
}