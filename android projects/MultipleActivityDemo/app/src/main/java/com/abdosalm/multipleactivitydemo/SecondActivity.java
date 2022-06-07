package com.abdosalm.multipleactivitydemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String age = intent.getStringExtra("name");
        Toast.makeText(this, age , Toast.LENGTH_SHORT).show();
    }
    public void goBack(View view){
        finish();
    }
}