package com.abdosalm.activitylifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowGuess extends AppCompatActivity {
    private TextView showGuessTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_guess);

        Bundle extra = getIntent().getExtras();

        showGuessTextview = findViewById(R.id.recieved_textview);

        /*if (getIntent().getStringExtra("guess") != null)
        {
            showGuessTextview.setText(getIntent().getStringExtra("guess"));
        }*/
        if (extra != null)
        {
            showGuessTextview.setText(extra.getString("guess"));
        }
        showGuessTextview.setOnClickListener(v->{
            Intent intent = getIntent();
            intent.putExtra("message_back" , "From Second Activity");
            setResult(RESULT_OK , intent);
            finish();
        });
    }
}