package com.abdosalm.activitylifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button showGuess;
    private EditText enterGuess;
    private final int  REQUEST_CODE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showGuess = findViewById(R.id.button_guess);
        enterGuess = findViewById(R.id.guess_field);

        showGuess.setOnClickListener(v->{
            if (!enterGuess.getText().toString().trim().isEmpty())
            {
                Intent intent  = new Intent(MainActivity.this , ShowGuess.class);
                intent.putExtra("guess" ,enterGuess.getText().toString().trim());
                //startActivity(intent);
                startActivityForResult(intent , REQUEST_CODE);
            }else{
                Toast.makeText(this, "Enter guess", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)
        {
            assert data != null;
            if (resultCode == RESULT_OK)
            {
                String message = data.getStringExtra("message_back");
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}