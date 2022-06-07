package com.abdosalm.makeitrain;

import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private TextView moneyValue;
    private int moneyCounter = 0;
    private static final String TAG = "MainActivity"; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        moneyValue = findViewById(R.id.moneyValue);
        

    }

    public void showMoney(View view) {
        NumberFormat number = NumberFormat.getCurrencyInstance();
        moneyCounter += 1000;
        if(moneyCounter >= 20000)
        {
            moneyValue.setTextColor(ContextCompat.getColor(MainActivity.this , R.color.red));
        }else{
            moneyValue.setTextColor(Color.MAGENTA);

        }
        moneyValue.setText(String.valueOf(number.format(moneyCounter)));
    }

    public void showInfo(View view) {
        Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT).show();
        Snackbar.make(moneyValue , R.string.app_name , Snackbar.LENGTH_SHORT).setAction("More", v -> {

        }).show();
    }
}