package com.example.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> places = new ArrayList<>();
    static ArrayList<LatLng> locations = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView)findViewById(R.id.listView);
        SharedPreferences sharedPreferences =this.getSharedPreferences("com.example.memorableplaces" , Context.MODE_PRIVATE);
        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();
        places.clear();
        latitudes.clear();
        longitudes.clear();
        locations.clear();
        try {
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places" , ObjectSerializer.serialize(new ArrayList<>())));
            latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latitudes" , ObjectSerializer.serialize(new ArrayList<>())));
            longitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longitudes" , ObjectSerializer.serialize(new ArrayList<>())));

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (places.size() > 0 && latitudes.size() > 0 && longitudes.size() > 0)
        {
            if (places.size() == latitudes.size() && latitudes.size() == longitudes.size())
            {
                for (int i = 0 ; i < latitudes.size() ; i++)
                {
                    locations.add(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i))));
                }
            }
        }else {
            locations.add(new LatLng(0, 0));
            places.add("Add a new place...");
        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 , places);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(MainActivity.this, position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext() , MapsActivity.class);
                intent.putExtra("placeNumber", position);
                startActivity(intent);
            }
        });
    }
}