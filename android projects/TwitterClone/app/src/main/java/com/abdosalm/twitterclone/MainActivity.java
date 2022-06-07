package com.abdosalm.twitterclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Twitter Login");
        redirectUsers();
    }
    public void signupLogin(View view){
        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){
                    redirectUsers();
                }else{
                    ParseUser parseUser = new ParseUser();
                    parseUser.setUsername(usernameEditText.getText().toString());
                    parseUser.setPassword(passwordEditText.getText().toString());
                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                redirectUsers();
                            }else{
                                Toast.makeText(MainActivity.this, e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    private void  redirectUsers(){
        if (ParseUser.getCurrentUser() != null){
            Intent intent = new Intent(MainActivity.this,UsersActivity.class);
            startActivity(intent);
        }
    }

}