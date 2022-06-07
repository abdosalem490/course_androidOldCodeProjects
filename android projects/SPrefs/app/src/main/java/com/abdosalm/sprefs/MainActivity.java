package com.abdosalm.sprefs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String MESSAGE_ID = "messages_prefs";
    private Button button;
    private EditText messageEditText;
    private TextView showMessageTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        messageEditText = findViewById(R.id.message_editText);
        showMessageTextview = findViewById(R.id.show_message_textview);

        button.setOnClickListener(v->{
            String message = messageEditText.getText().toString().trim();
            SharedPreferences sharedPreferences = getSharedPreferences(MESSAGE_ID , MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("message",message);
            editor.apply();
        });
        SharedPreferences sharedPreferences = getSharedPreferences(MESSAGE_ID , MODE_PRIVATE);
        String value = sharedPreferences.getString("message" , "Nothing");
        showMessageTextview.setText(value);
    }
}