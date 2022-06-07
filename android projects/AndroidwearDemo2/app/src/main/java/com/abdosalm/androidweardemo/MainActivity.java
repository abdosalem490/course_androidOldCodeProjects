package com.abdosalm.androidweardemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.abdosalm.androidweardemo.databinding.ActivityMainBinding;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private TextView mTextView;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




    }
    public void determineRoundOrSquare(View view){
        if(getResources().getConfiguration().isScreenRound()){
            Toast.makeText(this, "round", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "square", Toast.LENGTH_SHORT).show();
        }
    }
}