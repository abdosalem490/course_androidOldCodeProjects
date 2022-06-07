package com.abdosalm.alertdemo;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        sharedPreferences = this.getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "");
        if (language.equals("")){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_btn_speak_now)
                    .setTitle("Choose a language")
                    .setMessage("Which language would you like to use ?")
                    .setPositiveButton("English", (dialog, which) -> {
                        //setEnglish
                        setLanguage("English");
                    }).setNegativeButton("Spanish", (dialog, which) -> {
                //Set Spanish
                setLanguage("Spanish");
            }).show();
        }else{
            textView.setText(language);

        }
    }
    private void setLanguage(String language){
        sharedPreferences.edit().putString("language" , language).apply();

        textView.setText(sharedPreferences.getString("language",""));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.english:
                setLanguage("English");
                return true;
            case R.id.spanish:
                setLanguage("Spanish");
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}