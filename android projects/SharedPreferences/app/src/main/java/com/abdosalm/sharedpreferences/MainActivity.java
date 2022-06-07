package com.abdosalm.sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences(getPackageName() , MODE_PRIVATE);
        ArrayList<String> friends = new ArrayList<>();
        friends.add("abdo");
        friends.add("basma");
        friends.add("leen");
        friends.add("mama");
        friends.add("papa");
        try {
            sharedPreferences.edit().putString("friends",ObjectSerializer.serialize(friends)).apply();
            Log.i(TAG, "onCreate: " + ObjectSerializer.serialize(friends));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> newFriends = new ArrayList<>();
        try {
            newFriends = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("friends" , ObjectSerializer.serialize(new ArrayList<String>())));
            for (String s : newFriends){
                Log.i(TAG, "onCreate: " + s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*sharedPreferences.edit().putString("username","abdo").apply();
        String name = sharedPreferences.getString("username","");
        Log.i(TAG, "onCreate: " + name);*/
    }
}