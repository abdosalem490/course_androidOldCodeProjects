package com.abdosalm.peoplecount;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.abdosalm.peoplecount.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    private TextView mTextView;
    private ActivityMainBinding binding;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mTextView = binding.text;
    }
    public void plusOne(View view){
        count++;
        mTextView.setText(String.valueOf(count));
    }
    public void reset(View view){
        count = 0;
        mTextView.setText(String.valueOf(count));
    }
}