package com.abdosalm.self;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import util.JournalApi;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private Button createAccButton;
    private ProgressBar progressBar;

    private AutoCompleteTextView emailAddress;
    private EditText password;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        firebaseAuth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.email_sign_in_button);
        createAccButton = findViewById(R.id.create_acc_button_login);
        emailAddress = findViewById(R.id.email);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.login_progress);

        loginButton.setOnClickListener(v->{
            logInEmailPasswordUser(emailAddress.getText().toString().trim() , password.getText().toString().trim());
        });

        createAccButton.setOnClickListener(v->{
            startActivity(new Intent(LoginActivity.this , CreateAccountActivity.class));
        });

    }

    private void logInEmailPasswordUser(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            firebaseAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    assert user != null;
                    String currentUserId = user.getUid();

                    collectionReference.whereEqualTo("userId" , currentUserId).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null){

                            }
                            assert value != null;
                            if (!value.isEmpty()){
                                progressBar.setVisibility(View.INVISIBLE);

                                for (QueryDocumentSnapshot snapshot : value){
                                    JournalApi journalApi = JournalApi.getInstance();
                                    journalApi.setUsername(snapshot.getString("userName"));
                                    journalApi.setUserId(snapshot.getString("userId"));
                                    //Go to ListActivity
                                    startActivity(new Intent(LoginActivity.this , PostJournalActivity.class));
                                }
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }else{
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
        }
    }
}