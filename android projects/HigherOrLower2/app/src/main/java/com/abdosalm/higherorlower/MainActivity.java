package com.abdosalm.higherorlower;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Random random;
    int randomNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random =  new Random();
        randomNumber = random.nextInt(20)+1;
    }
    public void Guess(View view){
        EditText editText = findViewById(R.id.editTextNumber);
        String num = editText.getText().toString().trim();
        if (!TextUtils.isEmpty(num)){
            int number = Integer.parseInt(num);

            String message = "";
            if (number > randomNumber){
                message = "Lower!";
            }else if (number < randomNumber){
                message = "Higher";
            }else{
                message = "You got it!";
                randomNumber = random.nextInt(20)+1;
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

}