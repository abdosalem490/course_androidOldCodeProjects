package com.abdosalm.spam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText t = findViewById(R.id.editTextTextPersonName);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "This is my text to send." +KeyEvent.KEYCODE_ENTER + KeyEvent.ACTION_DOWN +KeyEvent.ACTION_UP+ "abdo  " + KeyEvent.ACTION_DOWN +"salm" +KeyEvent.ACTION_DOWN);
        intent.setType("text/plain");
        startActivity(intent);
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(t,0);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d(TAG, "dispatchKeyEvent: " + String.valueOf(event));

        return super.dispatchKeyEvent(event);
    }
}