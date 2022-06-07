package com.example.concureencyconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = findViewById(R.id.button);
        EditText amount = findViewById(R.id.amount);
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                double amountInDollar = Double.parseDouble(amount.getText().toString());
                Toast.makeText(MainActivity.this , String.format("%.2f" ,amountInDollar*0.71 ) , Toast.LENGTH_LONG).show();
            }
        };
        b.setOnClickListener(listener);
    }

}