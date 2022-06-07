package com.abdosalm.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import Data.DatabaseHandler;
import model.Contact;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);

        //insert contacts
        Log.d(TAG, "onCreate: inserting...");
        db.addContact(new Contact("abdo","01122786504"));
        db.addContact(new Contact("salm","01122767624"));
        db.addContact(new Contact("rose","04542124545"));
        db.addContact(new Contact("bella","12345678912"));
        //Read them back
        Log.d(TAG, "onCreate: reading all contacts");
        List<Contact> contactList = db.getAllContacts();
        //get one contact
        Contact oneContact = db.getContact(1);

        for (Contact contact : contactList){
            String log = "ID : " + contact.getId()+" , name : "+ contact.getName() +" , phone : "+contact.getPhoneNumber();
            Log.d(TAG, "onCreate: " + log);
        }
    }
}