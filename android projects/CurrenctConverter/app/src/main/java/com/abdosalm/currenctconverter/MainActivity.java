package com.abdosalm.currenctconverter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void clickFunction(View view){
        EditText amountEditText = findViewById(R.id.amountEditText);
        double amount = Double.parseDouble(amountEditText.getText().toString().trim());
        double dollars = amount * 1.3;

        Toast.makeText(this, String.format(Locale.US ,"%.02f",dollars), Toast.LENGTH_SHORT).show();
        InputMethodManager im = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(amountEditText.getWindowToken() , 0);
    }
}