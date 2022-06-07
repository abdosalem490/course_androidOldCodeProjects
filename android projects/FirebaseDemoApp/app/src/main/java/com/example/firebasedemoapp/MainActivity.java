package com.example.firebasedemoapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        Map<String , String> values = new HashMap<>();
        values.put("name" , "rob");
        dbref.push().setValue(values, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable  DatabaseError error, @NonNull  DatabaseReference ref) {
                Log.d(TAG, "onComplete: fd");
                if (error == null)
                {
                    Log.d(TAG, "onComplete: save sccessful");
                }else{
                    Log.d(TAG, "onComplete: save failed");
                }
            }
        });
    }
}