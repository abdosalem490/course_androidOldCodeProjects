package com.abdosalm.databasedemo;

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
            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Users",MODE_PRIVATE,null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS newUsers(name VARCHAR,age INT(4) , id INTEGER PRIMARY KEY);");
            //sqLiteDatabase.execSQL("INSERT INTO newUsers (name,age) VALUES ('Nick',28);");
            //sqLiteDatabase.execSQL("INSERT INTO newUsers (name,age) VALUES ('Dave',14);");
            //sqLiteDatabase.execSQL("INSERT INTO newUsers (name,age) VALUES ('basma',18);");

           // sqLiteDatabase.execSQL("DELETE FROM users WHERE age < 18");
            Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM newUsers",null);
            int eventIndex = c.getColumnIndex("name");
            int yearIndex = c.getColumnIndex("age");
            int idIndex = c.getColumnIndex("id");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Log.d(TAG, "onCreate: id: " + c.getString(idIndex)+" name :"+c.getString(eventIndex) + " , age : " + c.getString(yearIndex));
                c.moveToNext();
            }
            sqLiteDatabase.close();;
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}