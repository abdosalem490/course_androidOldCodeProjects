package com.abdosalm.cmp2024.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abdosalm.cmp2024.Activities.Constants.Constant;
import com.abdosalm.cmp2024.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText adminNameEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private Button loginInButton;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();

        adminNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
        loginInButton = findViewById(R.id.loginInButton);

        signInButton.setOnClickListener(this);
        loginInButton.setOnClickListener(this);

        sharedPreferences = this.getSharedPreferences(Constant.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signInButton){
            //sign in as randomized user
            signIn(false);
        }else if (v.getId() == R.id.loginInButton){
            //login in as admin
            String name = adminNameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (name.equals(Constant.ADMIN_USER_NAME) && password.equals(Constant.ADMIN_PASSWORD)){
                //sign in
                signIn(true);
            }else{
                Toast.makeText(getApplicationContext(), "wrong", Toast.LENGTH_SHORT).show();
                adminNameEditText.setText("");
                passwordEditText.setText("");
            }
        }
    }
    private void signIn(boolean isAdmin){
        sharedPreferences.edit().putBoolean(Constant.IS_LOGGED_IN,true).apply();
        Intent intent = new Intent(getApplicationContext(),MainView.class);
        if (isAdmin)
            sharedPreferences.edit().putBoolean(Constant.IS_ADMIN,true).apply();
        else
            sharedPreferences.edit().putBoolean(Constant.IS_ADMIN,false).apply();
        startActivity(intent);
    }
}