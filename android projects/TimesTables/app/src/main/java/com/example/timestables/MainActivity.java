package com.example.timestables;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ListView timesTableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SeekBar timesTableSeekBar = findViewById(R.id.timesTableSeekBar);
        timesTableListView = findViewById(R.id.timesTableListView);
        timesTableSeekBar.setMax(20);
        timesTableSeekBar.setProgress(10);
        timesTableSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min = 1;
                int timesTable;
                if (progress < min)
                {
                    timesTable = min;
                    timesTableSeekBar.setProgress(min);
                }else {
                    timesTable = progress;
                }
                Log.d(TAG, "onProgressChanged: "+timesTable);
                generateTimesTable(timesTable);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        generateTimesTable(10);
    }
    public void generateTimesTable(int timesTable)
    {
        ArrayList<String> timesTableContent = new ArrayList<>();
        for (int i = 1 ; i <= 10 ; i++)
        {
            timesTableContent.add(Integer.toString(i*timesTable));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_selectable_list_item , timesTableContent);
        timesTableListView.setAdapter(arrayAdapter);
    }

}