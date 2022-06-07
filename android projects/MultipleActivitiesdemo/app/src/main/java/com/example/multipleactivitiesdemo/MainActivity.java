package com.example.multipleactivitiesdemo;

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
       final ArrayList<String> friends = new ArrayList<>();
        friends.add("abdo");
        friends.add("mohamed");
        friends.add("salem");
        friends.add("hassan");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item , friends);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("name",friends.get(position));
                startActivity(intent);
            }
        });
        listView.setAdapter(arrayAdapter);
    }

}