package com.example.listviewdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView myListView = findViewById(R.id.myListView);

        ArrayList<String> myFamily = new ArrayList<String>(asList("abdo" , "basma" , "leen" , "mama" , "papa"));


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_activated_1 , myFamily);
       myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"hello "+ myFamily.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}