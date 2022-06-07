package com.abdosalm.picinpicfun;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void goPiP(View view){
        enterPictureInPictureMode();
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        if(isInPictureInPictureMode){
            // Going into PIP
            button.setVisibility(View.INVISIBLE);
            getSupportActionBar().hide();
            textView.setText("abdo");
        }else{
            // Going out of PIP
            button.setVisibility(View.VISIBLE);
            getSupportActionBar().show();
            textView.setText("Hello world!");
        }
    }
}