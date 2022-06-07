package com.abdosalm.notesapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences(getPackageName(),MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);
        if (set == null){
            notes.add("Example");
        }else{
            notes = new ArrayList<>(set);
        }

        listView = findViewById(R.id.listView);


        arrayAdapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this , NoteEditorActivity.class);
            intent.putExtra("noteId",position);
            startActivity(intent);
        });
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are You Sure ?")
                    .setMessage("Do You Want to delete this note ?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        notes.remove(position);
                        arrayAdapter.notifyDataSetChanged();

                        HashSet<String> setNew = new HashSet<>(MainActivity.notes);
                        sharedPreferences.edit().putStringSet("notes",setNew).apply();
                    }).setNegativeButton("No" , null)
                    .show();
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_note:
                Intent intent = new Intent(getApplicationContext(),NoteEditorActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}