package com.abdosalm.bio;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.abdosalm.bio.data.Bio;
import com.abdosalm.bio.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final Bio bio = new Bio();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main);
        binding.doneButton.setOnClickListener(this::addHobbies);
        bio.setName("abdo salm");
        binding.setBio(bio);

    }

    public void addHobbies(View view) {
        bio.setHobbies(String.format("Hobbies %s", binding.enterHobbies.getText().toString().trim()));

        binding.invalidateAll();
        binding.hobbiesText.setVisibility(View.VISIBLE);

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}