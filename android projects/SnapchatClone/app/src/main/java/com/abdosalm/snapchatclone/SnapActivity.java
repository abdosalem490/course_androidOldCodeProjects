package com.abdosalm.snapchatclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SnapActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ListView snapListView;
    ArrayList<String> emails = new ArrayList<>();
    ArrayList<DataSnapshot> snaps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap);

        snapListView = findViewById(R.id.snapListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,emails);
        snapListView.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("snaps").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {
                emails.add(snapshot.child("from").getValue().toString());
                snaps.add(snapshot);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                int index = 0;
                for (DataSnapshot snapshot1 : snaps){
                    if (snapshot1.getKey().equals(snapshot.getKey())){
                        snaps.remove(index);
                        emails.remove(index);
                    }
                    index++;
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        snapListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataSnapshot snapshot = snaps.get(position);
                Intent intent = new Intent(getApplicationContext(),ViewSnapActivity.class);
                intent.putExtra("imageName",snapshot.child("imageName").getValue().toString());
                intent.putExtra("imageURL",snapshot.child("imageURL").getValue().toString());
                intent.putExtra("message",snapshot.child("message").getValue().toString());
                intent.putExtra("snapKey",snapshot.getKey());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.createSnap){
            Intent intent = new Intent(this,CreateSnapActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.logout){
            mAuth.signOut();
            finish();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.snaps,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mAuth.signOut();
    }

}