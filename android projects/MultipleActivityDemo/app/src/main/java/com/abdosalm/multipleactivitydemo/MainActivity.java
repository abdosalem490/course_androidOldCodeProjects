package com.abdosalm.multipleactivitydemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        ArrayList<String> friends = new ArrayList<>();
        friends.add("salem");
        friends.add("abdo");
        friends.add("mohamed");
        friends.add("khaled");
        friends.add("hi");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 , friends);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this , SecondActivity.class);
                intent.putExtra("name",friends.get(position));
                startActivity(intent);
            }
        });
        listView.setAdapter(arrayAdapter);

    }
}