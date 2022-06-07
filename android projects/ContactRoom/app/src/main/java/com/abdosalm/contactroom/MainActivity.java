package com.abdosalm.contactroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdosalm.contactroom.adapter.RecyclerViewAdapter;
import com.abdosalm.contactroom.model.Contact;
import com.abdosalm.contactroom.model.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnContactClickListener{
    private static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;
    public static final String CONTACT_ID = "contact_id";
    private ContactViewModel contactViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private LiveData<List<Contact>>contactList;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication()).create(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, contacts -> {
            //set up adapter
            recyclerViewAdapter =  new RecyclerViewAdapter(contacts , MainActivity.this , this);
            recyclerView.setAdapter(recyclerViewAdapter);
        });




        FloatingActionButton fab = findViewById(R.id.add_contact_fab);
        fab.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this , NewContact.class);
            startActivityForResult(intent , NEW_CONTACT_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            assert data != null;
            Contact contact = new Contact(data.getStringExtra(NewContact.NAME_REPLY) , data.getStringExtra(NewContact.OCCUPATION));
            ContactViewModel.insert(contact);
        }
    }

    @Override
    public void onContact(int position) {
        Contact contact = Objects.requireNonNull(contactViewModel.getAllContacts().getValue()).get(position);
        Log.d(TAG, "onContact: "+contact.getId());
        Intent intent = new Intent(MainActivity.this , NewContact.class);
        intent.putExtra(CONTACT_ID , contact.getId());
        startActivity(intent);
    }
}