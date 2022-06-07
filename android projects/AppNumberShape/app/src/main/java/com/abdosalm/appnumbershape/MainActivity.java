package com.abdosalm.appnumbershape;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    class Number{
        int number;
        public boolean isTriangular(){
            int x = 1;
            int triangularNumber = 1;
            while (triangularNumber < number){
                x++;
                triangularNumber += x;
            }
            if (triangularNumber == number)
                return true;
            return false;
        }
        public boolean isSquare(){
            double squareRoot = Math.sqrt(number);
            return Math.floor(squareRoot) == squareRoot;
        }

    }
    public void testNumber(View view){
        EditText editText = findViewById(R.id.editTextNumber);
        String num = editText.getText().toString().trim();
        if (!TextUtils.isEmpty(num)){
            Number number = new Number();
            number.number = Integer.parseInt(num);
            String message = num;

            if (number.isSquare() && number.isTriangular())
                message += " is square & triangular";
            else if (number.isSquare())
                message += " is square";
            else if (number.isTriangular())
                message += " is triangular";
            else
                message += " is neither square nor triangular";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "please enter a number", Toast.LENGTH_SHORT).show();
    }
}