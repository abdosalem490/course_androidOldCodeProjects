package com.example.languagepreferences;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        sharedPreferences = this.getSharedPreferences(this.getPackageName() , Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("Language" , "");
        if (language == "")
        {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_btn_speak_now).setTitle("Choose a language")
                    .setMessage("Which language would you like ? ").setPositiveButton("English", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setLanguage("English");
                }
            }).setNegativeButton("Spanish", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setLanguage("Spanish");
                }
            }).show();
        }else{
            textView.setText(language);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.english)
        {
            setLanguage("English");
            return true;
        }
        else if(item.getItemId() == R.id.spanish)
        {
            setLanguage("Spanish");
            return true;
        }
        return false;
    }

    private void setLanguage(String language)
    {
        sharedPreferences = this.getSharedPreferences(this.getPackageName() , Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("Language", language).apply();

        textView.setText(language);
    }
}