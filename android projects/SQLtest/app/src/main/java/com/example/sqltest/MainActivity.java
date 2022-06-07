package com.example.sqltest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLiteDatabase sqLiteDatabase = getBaseContext().openOrCreateDatabase("sqlite-test-1.db" , MODE_PRIVATE , null);

        /*String sql = "DROP TABLE IF EXISTS contacts;";
        sqLiteDatabase.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS contacts(name TEXT, phone INTEGER, email TEXT);";
        Log.d(TAG, "onCreate: sql is "+sql);
        sqLiteDatabase.execSQL(sql);
        sql ="INSERT INTO contacts VALUES(\"abdo\" , 01122786504 , \"abdosalm555@gmail.com\");";
        Log.d(TAG, "onCreate: sql is "+sql);
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO contacts VALUES('leen' , 12345 , 'leen@gmail.com');";
        Log.d(TAG, "onCreate: sql is "+sql);
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO contacts VALUES('basma' , 456789 , 'b@gmail.com');";
        Log.d(TAG, "onCreate: sql is "+sql);
        sqLiteDatabase.execSQL(sql);*/

        Cursor query = sqLiteDatabase.rawQuery("SELECT * FROM contacts;" ,null);

            if (query.moveToFirst())
            {
                do
                {
                String name = query.getString(0);
                int phone = query.getInt(1);
                String email = query.getString(2);
                Toast.makeText(this , "name = "+name +" phone = "+phone+" email = "+ email , Toast.LENGTH_LONG).show();
                }while(query.moveToNext());
            }


        query.close();
        sqLiteDatabase.close();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}