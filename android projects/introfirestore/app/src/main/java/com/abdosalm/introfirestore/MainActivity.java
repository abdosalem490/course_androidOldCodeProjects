package com.abdosalm.introfirestore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText enterTitle;
    private EditText enterThought;
    private Button saveButton;
    private TextView recTitle , recThoughts;
    private Button showButton;
    private Button updateTitle;
    private Button deleteThought;

    private static final String TAG = "MainActivity";


    //Keys
    public static final String KEY_TITLE = "title";
    public static final String KEY_THOUGHT = "thought";

    //Connection to FireStore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference journalRef =  db.collection("Journal").document("First Thoughts");
    private CollectionReference collectionReference = db.collection("Journal");
   // private DocumentReference journalRef = db.document("Journal/First Thoughts");

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterTitle = findViewById(R.id.edit_text_title);
        enterThought = findViewById(R.id.edit_text_thoughts);
        saveButton = findViewById(R.id.save_button);
        recTitle = findViewById(R.id.rec_title);
        recThoughts = findViewById(R.id.rec_thoughts);
        showButton = findViewById(R.id.show_data);
        updateTitle = findViewById(R.id.update_data);
        deleteThought = findViewById(R.id.delete_thought);

        updateTitle.setOnClickListener(this);
        deleteThought.setOnClickListener(this);

        saveButton.setOnClickListener(v->{
            addThought();
            /*String title = enterTitle.getText().toString().trim();
            String thought = enterThought.getText().toString().trim();

            Journal journal = new Journal(title , thought);*/

            /*Map<String , Object> data =  new HashMap<>();
            data.put(KEY_TITLE , title);
            data.put(KEY_THOUGHT , thought);*/

           /* journalRef.set(journal).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: "+e.toString());
                }
            });*/
        });

        showButton.setOnClickListener(v->{
           /* journalRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                       *//* String title = documentSnapshot.getString(KEY_TITLE);
                        String thoughts = documentSnapshot.getString(KEY_THOUGHT);*//*
                        Journal journal = documentSnapshot.toObject(Journal.class);
                        assert journal != null;
                        recTitle.setText(journal.getTitle());
                        recThoughts.setText(journal.getThought());
                    }else{
                        Toast.makeText(MainActivity.this, "No data exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: "+e.toString());
                }
            });*/
            getThoughts();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        journalRef.addSnapshotListener(this , new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(MainActivity.this, "Something went wong", Toast.LENGTH_SHORT).show();
                }
                if (value != null && value.exists()){
                    Journal journal = value.toObject(Journal.class);
                    assert journal != null;
                    recTitle.setText(journal.getTitle());
                    recThoughts.setText(journal.getThought());
                }else{
                    recTitle.setText("");
                    recThoughts.setText("");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update_data:
                updateMyTitle();
                break;
            case R.id.delete_thought:
               // deleteMyThought();
                deleteAll();
                break;
        }
    }

    private void deleteMyThought() {
        journalRef.update(KEY_THOUGHT , FieldValue.delete());
    }

    private void updateMyTitle() {
        String title = enterTitle.getText().toString().trim();
        String thought = enterThought.getText().toString().trim();

        Map<String , Object> data  = new HashMap<>();
        data.put(KEY_TITLE , title);
        data.put(KEY_THOUGHT , thought);

        journalRef.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void deleteAll(){
        journalRef.delete();
    }
    private void addThought(){
        String title = enterTitle.getText().toString().trim();
        String thought = enterThought.getText().toString().trim();

        Journal journal = new Journal(title , thought);

        collectionReference.add(journal);
    }
    private void getThoughts(){
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                StringBuilder builder =  new StringBuilder();
                builder.append("");
                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots){
                   // Log.d(TAG, "onSuccess: "+snapshots.getString(KEY_TITLE));
                    Journal journal = snapshots.toObject(Journal.class);
                    builder.append("title : ").append(journal.getTitle()).append(", thought is : ").append(journal.getThought()).append("\n");
                }
                recThoughts.setText(builder);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}