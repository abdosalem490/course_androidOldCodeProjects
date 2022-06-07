package com.example.higherorlower;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = findViewById(R.id.button);
        EditText edit = findViewById(R.id.guessEditText);

        Random r = new Random();
        x =r.nextInt(20) + 1;

        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Integer.parseInt(edit.getText().toString()) > x)
                {
                    makeToast("the number is lower than the number you entered");
                }else if (Integer.parseInt(edit.getText().toString()) < x)
                {
                    makeToast("the number is higher than the number you entered");
                }else {
                     x = r.nextInt(20) + 1;
                    makeToast("success");
                }


            }
        };
        b.setOnClickListener(buttonListener);

    }
    private void makeToast(String s)
    {
        Toast.makeText(MainActivity.this , s, Toast.LENGTH_SHORT).show();
    }

}