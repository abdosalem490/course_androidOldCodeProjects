package com.example.sharedprefrencedemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> friends = new ArrayList<>();
        friends.add("abdo");
        friends.add("basma");
        friends.add("mohamed");
        friends.add("salem");
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.sharedprefrencedemo" , Context.MODE_PRIVATE);
       /* try {
            sharedPreferences.edit().putString("Friends" , ObjectSerializer.serialize(friends)).apply();

        } catch (IOException e) {
            e.printStackTrace();
        }*/
        ArrayList<String> newFriends = new ArrayList<>();
        try {
            newFriends = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("Friends" , ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate: "+newFriends.toString());
        //sharedPreferences.edit().putString("username","rob").apply();
        //String userName = sharedPreferences.getString("username","");
        //Log.d(TAG, "onCreate: "+userName);
    }
}