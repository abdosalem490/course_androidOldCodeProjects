package com.abdosalm.firebaseintro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText email;
    private EditText password;
    private Button login;
    private Button signOut;
    private Button createAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("message");
        databaseReference.setValue("another");
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailEd);
        password = findViewById(R.id.passwordEd);
        login = findViewById(R.id.loginButton);
        signOut = findViewById(R.id.signOutButton);
        createAccount = findViewById(R.id.createAct);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
          //      String value = snapshot.getValue(Customer.class);
            //    Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    //user is signed in

                }else{
                    //user is signed out
                }
            }
        };
        login.setOnClickListener(v->{
            String emailString = email.getText().toString();
            String pwd = password.getText().toString();

            if (!emailString.equals("")&&!pwd.equals("")){
                mAuth.signInWithEmailAndPassword(emailString,pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            //isn't success
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Customer customer = new Customer("Gina","Machevel",emailString,78);
                            databaseReference.setValue(customer);
                        }
                    }
                });

            }
        });
        signOut.setOnClickListener(v->{
            mAuth.signOut();
            Toast.makeText(MainActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
        });
        createAccount.setOnClickListener(v->{
            String emailString = email.getText().toString();
            String pwd = password.getText().toString();
            if (!emailString.equals("")&&!pwd.equals("")) {
                mAuth.createUserWithEmailAndPassword(emailString,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "failed to create account", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "account created", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
}