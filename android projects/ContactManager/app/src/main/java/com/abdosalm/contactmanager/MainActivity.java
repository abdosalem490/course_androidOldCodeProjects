package com.abdosalm.contactmanager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.abdosalm.contactmanager.data.DatabaseHandler;
import com.abdosalm.contactmanager.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView listView;
    private ArrayList<String> contactArrayList;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);
        listView = findViewById(R.id.listView);
        contactArrayList = new ArrayList<>();

        /*db.addContact(new Contact(1 ,"abdo" , "salm"));
        db.addContact(new Contact(1 ,"basma" , "salm"));
        db.addContact(new Contact(1 ,"leen" , "mohamed"));
        db.addContact(new Contact(1 ,"moahmed" , "hassan"));
        db.addContact(new Contact(1 ,"rasha" , "naphady"));
        db.addContact(new Contact(1 ,"xxxxxtension" , "011122504750"));
        db.addContact(new Contact(1 ,"sdf" , "7825"));
        db.addContact(new Contact(1 ,"etisalaat" , "011"));*/

        List<Contact> contactList = db.getAllContacts();
        for (Contact contact : contactList){
            if (contact.getName() != null)
            {
                contactArrayList.add(contact.getName());
            }
        }

        arrayAdapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 , contactArrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Log.d(TAG, "onCreate: " + contactArrayList.get(position));
        });
    }
}