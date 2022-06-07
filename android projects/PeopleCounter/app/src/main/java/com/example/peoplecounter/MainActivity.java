package com.example.peoplecounter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.peoplecounter.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    private TextView mTextView;
    private ActivityMainBinding binding;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mTextView = findViewById(R.id.textView);
    }
    public void plusOne(View view)
    {
        counter++;
        mTextView.setText(Integer.toString(counter));
    }
    public void reset(View view)
    {
        counter = 0;
        mTextView.setText(Integer.toString(counter));
    }
}