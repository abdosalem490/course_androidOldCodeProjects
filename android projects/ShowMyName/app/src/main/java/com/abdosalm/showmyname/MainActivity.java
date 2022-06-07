package com.abdosalm.showmyname;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button showButton;
    private TextView nameText;
    private EditText enterName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showButton = findViewById(R.id.button);
        nameText = findViewById(R.id.textView);
        enterName = findViewById(R.id.editTextName);
        showButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name  = enterName.getText().toString();
                if (name.isEmpty())
                {

                }else{
                    nameText.setText("Hello " + name);
                }
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken() , 0);
            }
        });
    }
}