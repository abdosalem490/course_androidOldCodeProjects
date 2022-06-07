package com.abdosalm.memorableplaces;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> places = new ArrayList<>();
    static ArrayList<LatLng> locations = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences(getPackageName() , MODE_PRIVATE);
        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();
        places.clear();
        latitudes.clear();
        longitudes.clear();
        locations.clear();
        try {
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.serialize(new ArrayList<String>())));
            latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lats",ObjectSerializer.serialize(new ArrayList<String>())));
            longitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lons",ObjectSerializer.serialize(new ArrayList<String>())));


        }catch (Exception e){
            e.printStackTrace();
        }

        if (places.size() > 0 && latitudes.size() > 0 && longitudes.size() > 0){
            if (places.size() == latitudes.size() && places.size() == longitudes.size()){
                for (int i = 0 ; i < latitudes.size() ; i++){
                    locations.add(new LatLng(Double.parseDouble(latitudes.get(i)) , Double.parseDouble(longitudes.get(i))));
                }
            }
        }else{
            locations.add(new LatLng(0,0));

            places.add("Add a new place...");
        }

        ListView listView = findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , places);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this , MapsActivity.class);
                intent.putExtra("placeNumber" , position);
                startActivity(intent);
            }
        });

    }
}