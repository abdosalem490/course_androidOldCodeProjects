package com.abdosalm.cmp2024.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.abdosalm.cmp2024.Activities.Constants.Constant;
import com.abdosalm.cmp2024.Activities.LoginActivity;
import com.abdosalm.cmp2024.R;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences(Constant.SHARED_PREFERENCE_NAME,MODE_PRIVATE);

        if (sharedPreferences.getBoolean(Constant.IS_LOGGED_IN,false)){
            startActivity(new Intent(getApplicationContext(),MainView.class));
        }else{
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }
}