package com.abdosalm.blog.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abdosalm.blog.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button loginButton;
    private Button createActButton;
    private EditText emailField;
    private EditText passwordField;
    private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if (mUser != null){
                    Toast.makeText(getApplicationContext(), "Signed in", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,PostListActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Not Signed in", Toast.LENGTH_SHORT).show();
                }
            }
        };
        loginButton = findViewById(R.id.loginButton);
        createActButton = findViewById(R.id.createActButton);
        emailField = findViewById(R.id.emailEdt);
        passwordField = findViewById(R.id.passwordEdt);

        createActButton.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this,CreateAccountActivity.class));
            finish();
        });

        loginButton.setOnClickListener(v->{
            if (!TextUtils.isEmpty(emailField.getText().toString())&&!TextUtils.isEmpty(passwordField.getText().toString())){
                String email = emailField.getText().toString();
                String pwd = passwordField.getText().toString();
                login(email,pwd);
            }else{

            }
        });

    }

    private void login(String email, String pwd) {
        mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // we are in
                    Toast.makeText(getApplicationContext(), "signed in", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,PostListActivity.class));
                    finish();
                }else{
                    // not in

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_signout){
            mAuth.signOut();
        }else if (item.getItemId() == R.id.action_add){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}