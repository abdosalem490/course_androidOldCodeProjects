package com.abdosalm.checkbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private CheckBox momCheckBox;
    private CheckBox dadCheckBox;
    private CheckBox grandPaCheckBox;

    private Button showButton;
    private TextView showTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        momCheckBox = findViewById(R.id.momCheckBox);
        dadCheckBox = findViewById(R.id.dadCheckBox);
        grandPaCheckBox = findViewById(R.id.grandPaCheckBox);

        showButton = findViewById(R.id.showButtonId);
        showTextView = findViewById(R.id.resultId);

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(momCheckBox.getText().toString() +" status is : "+momCheckBox.isChecked()+"\n");
                stringBuilder.append(dadCheckBox.getText().toString() +" status is : "+dadCheckBox.isChecked()+"\n");
                stringBuilder.append(grandPaCheckBox.getText().toString() +" status is : "+grandPaCheckBox.isChecked()+"\n");

                showTextView.setText(stringBuilder);
            }
        });
    }
}