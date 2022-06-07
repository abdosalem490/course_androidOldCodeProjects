package com.abdosalm.whatsappclone;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {
    TextView toggleLoginModeTextView;
    EditText usernameEditText;
    EditText passwordEditText;
    Button signupLoginButton;

    boolean loginModeActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("whatsapp login");

        toggleLoginModeTextView = findViewById(R.id.toggleLoginModeTextView);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signupLoginButton = findViewById(R.id.signupButton);

        redirectIfLoggedIn();
    }

    public void signUpLogin(View view) {

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            if (loginModeActive) {
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null){
                            redirectIfLoggedIn();
                        }else{
                            String message = e.getMessage();
                            if (message.toLowerCase().contains("java")){
                                message = e.getMessage().substring(e.getMessage().indexOf(" "));
                            }
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(password);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            redirectIfLoggedIn();
                        } else {
                            String message = e.getMessage();
                            if (message.toLowerCase().contains("java")){
                                message = e.getMessage().substring(e.getMessage().indexOf(" "));
                            }
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
    public void togleLoginMode(View view){
        if (loginModeActive){
            loginModeActive = false;
            signupLoginButton.setText("Sign up");
            toggleLoginModeTextView.setText("Or , log In");
        }else{
            loginModeActive = true;
            signupLoginButton.setText("Log in");
            toggleLoginModeTextView.setText("Or , sign up");
        }
    }
    private void redirectIfLoggedIn(){
        if (ParseUser.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
            startActivity(intent);
        }
    }
}