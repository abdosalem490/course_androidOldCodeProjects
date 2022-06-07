package com.abdosalm.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button saveButton;
    private TextView result;
    private EditText enterMessage;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "myPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterMessage = findViewById(R.id.enterName);
        result = findViewById(R.id.resultTExtView);
        saveButton = findViewById(R.id.saveButton);
        sharedPreferences = this.getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        saveButton.setOnClickListener(v->{
            String name = enterMessage.getText().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("message",name);
            editor.apply();
        });

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        if (prefs.contains("message")){
            String message = prefs.getString("message","");
            result.setText(message);
        }
    }
}