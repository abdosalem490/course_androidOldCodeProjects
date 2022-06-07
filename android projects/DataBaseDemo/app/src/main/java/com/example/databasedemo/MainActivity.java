package com.example.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users",MODE_PRIVATE , null);

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS newUsers(name VARCHAR , age INTEGER(3) , id INTEGER PRIMARY KEY)");
           // myDatabase.execSQL("INSERT INTO newUsers(name , age) VALUES ('abdo salem', 21)");
           // myDatabase.execSQL("INSERT INTO newUsers(name , age) VALUES ('salem',36)");
            //myDatabase.execSQL("DELETE FROM users WHERE name = 'Rob'");
            Cursor cursor = myDatabase.rawQuery("SELECT * FROM newUsers" , null);
            int nameIndex = cursor.getColumnIndex("name");
            int ageIndex = cursor.getColumnIndex("age");
            int idIndex = cursor.getColumnIndex("id");
            cursor.moveToFirst();
            while (cursor != null)
            {
                Log.d(TAG, "onCreate: name = "+cursor.getString(nameIndex)+" age = "+cursor.getInt(ageIndex)+" primary key = "+cursor.getInt(idIndex));
                cursor.moveToNext();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}