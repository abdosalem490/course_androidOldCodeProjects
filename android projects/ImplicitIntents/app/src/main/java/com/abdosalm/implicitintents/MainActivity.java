package com.abdosalm.implicitintents;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT , "this is a baodasf sdgfsdfsdaf"+ KeyEvent.KEYCODE_ENTER +"asfasdfsdfs\nrygsg\nergyergf\n");
            intent.putExtra(Intent.EXTRA_SUBJECT, "is a baodasf sdgfsdfsdaf\nasfasdfsdfs\nrygsg\nergyergf\n");
            startActivity(intent);
        });
    }
}