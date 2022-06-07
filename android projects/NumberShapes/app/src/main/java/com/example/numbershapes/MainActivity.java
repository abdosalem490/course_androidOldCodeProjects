package com.example.numbershapes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    class Number
    {
        int number;
        public boolean isTriangular()
        {
            int x = 1 ;
            int triangularNumber = 1;
            while (triangularNumber < number)
            {
                x++;
                triangularNumber+= x;
            }
            return triangularNumber == number;
        }
        public boolean isSquare()
        {
            double temp = Math.sqrt(number);
            return temp == Math.floor(temp);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = findViewById(R.id.button);
        EditText edit = findViewById(R.id.userNumber);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";
                if (edit.getText().toString().isEmpty()) {
                    message = "please enter a number";
                } else {
                    Number myNumber = new Number();
                    myNumber.number = Integer.parseInt(edit.getText().toString());

                    if (myNumber.isSquare()) {
                        if (myNumber.isTriangular()) {
                            message = myNumber.number + " is both triangular and square";
                        } else {
                            message = myNumber.number + " is only square";
                        }
                    } else if (myNumber.isTriangular()) {
                        message = myNumber.number + " is only triangular";
                    } else {
                        message = myNumber.number + " is not triangular nor square";

                    }

                }
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        };
        b.setOnClickListener(listener);
    }
    public void testNumber()
    {

    }
}