package com.abdosalm.listviewdemo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myListView = findViewById(R.id.myListView);
        final ArrayList<String> family = new ArrayList<>(Arrays.asList("abdo","basma","leen","rasha","mohamed"));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 , family);
        myListView.setAdapter(arrayAdapter);
        myListView.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(this, family.get(position), Toast.LENGTH_SHORT).show();
        });
    }
}