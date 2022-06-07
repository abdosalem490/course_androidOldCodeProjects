package com.abdosalm.meterstoinches;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /*
         1m = 39.3701 in
     */


    private EditText enterMeters;
    private Button convertButton;
    private TextView resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterMeters = findViewById(R.id.metersEditText);
        convertButton = findViewById(R.id.convertId);
        resultTextView = findViewById(R.id.resultId);

        convertButton.setOnClickListener(v -> {
            // Conversion Logic
            double multiplier = 39.37;
            double result = 0.0;
            if (enterMeters.getText().toString().equals("")){
                resultTextView.setText(R.string.error);
                resultTextView.setTextColor(Color.RED);
            }else{
                double meterValue = Double.parseDouble(enterMeters.getText().toString());
                result = meterValue * meterValue;
                resultTextView.setTextColor(Color.RED);
                resultTextView.setText(String.format("%.2f",result) + " inches");
            }
        });
    }
}