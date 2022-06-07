package com.abdosalm.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText enteredAmount;
    private SeekBar seekBar;
    private Button calculateButton;
    private TextView totalResultTextView;
    private TextView textViewSeekbar;
    private int seekbarPercentage;
    private float enteredBillFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enteredAmount = findViewById(R.id.amountId);
        seekBar = findViewById(R.id.seekBar);
        calculateButton = findViewById(R.id.calculateButton);
        totalResultTextView = findViewById(R.id.resultId);
        textViewSeekbar = findViewById(R.id.textViewSeekbar);

        calculateButton.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSeekbar.setText(String.valueOf(seekBar.getProgress())+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekbarPercentage = seekBar.getProgress();
            }
        });
    }

    @Override
    public void onClick(View v) {
        calculate();
    }
    public void calculate(){
        float result = 0.0f;
        if (!enteredAmount.getText().toString().equals("")){
            enteredBillFloat = Float.parseFloat(enteredAmount.getText().toString());
            result = (seekbarPercentage/100.0f)*enteredBillFloat;
            totalResultTextView.setText(String.valueOf(result));
        }else{
            Toast.makeText(MainActivity.this, "please enter a bill amount", Toast.LENGTH_SHORT).show();
        }

    }

}