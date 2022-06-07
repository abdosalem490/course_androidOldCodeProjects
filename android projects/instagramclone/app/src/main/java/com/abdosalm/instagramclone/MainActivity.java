package com.abdosalm.instagramclone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener{
    private static final String TAG = "MainActivity";
    Boolean signUpModeActive = true;
    TextView loginTextView;
    EditText usernameEditText;
    EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginTextView = findViewById(R.id.loginTextView);
        loginTextView.setOnClickListener(this);

        usernameEditText = findViewById(R.id.userNameeditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        ImageView logoImageView = findViewById(R.id.logoImageView);
        ConstraintLayout backgroundLayout = findViewById(R.id.backgroundLayout);
        logoImageView.setOnClickListener(this);
        backgroundLayout.setOnClickListener(this);
        passwordEditText.setOnKeyListener(this);

        if (ParseUser.getCurrentUser() != null){
            showUserList();
        }
        setTitle("Instagram");

    }
    public void signUpClicked(View view){
        if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")){
            Toast.makeText(this, "A username and a password are required", Toast.LENGTH_SHORT).show();
        }else{
            if (signUpModeActive) {
                ParseUser user = new ParseUser();
                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i(TAG, "done: Success");
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                //Login
                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null){
                            Log.i(TAG, "done: ok !");
                            showUserList();
                        }else{
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginTextView){
            Button signUpButton = findViewById(R.id.signUpButton);
            if (signUpModeActive){
                signUpModeActive = false;
                signUpButton.setText("Login");
                loginTextView.setText("or, Sign Up");
            }else{
                signUpModeActive = true;
                signUpButton.setText("SignUp");
                loginTextView.setText("or, Login");
            }
        }else if (v.getId() == R.id.logoImageView || v.getId() == R.id.backgroundLayout){
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            signUpClicked(v);
        }
        return false;
    }
    private void showUserList(){
        Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
        startActivity(intent);
    }

}