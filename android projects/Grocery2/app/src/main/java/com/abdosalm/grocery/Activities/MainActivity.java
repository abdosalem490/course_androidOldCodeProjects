package com.abdosalm.grocery.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.abdosalm.grocery.Data.DatabaseHandler;
import com.abdosalm.grocery.Model.Grocery;
import com.abdosalm.grocery.R;
import com.abdosalm.grocery.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText quantity;
    private EditText groceryItem;
    private Button saveButton;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private DatabaseHandler db;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DatabaseHandler(this);
        byPassActivity();


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
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

    private void   createPopupDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);
        groceryItem = view.findViewById(R.id.groceryItem);
        quantity = view.findViewById(R.id.groceryQty);
        saveButton = view.findViewById(R.id.saveButton);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        //gives error
        saveButton.setOnClickListener(v->{
            if (!groceryItem.getText().toString().isEmpty() && !quantity.getText().toString().isEmpty())
                saveGroceryToDB(v);
        });



    }

    private void saveGroceryToDB(View view) {
        Grocery grocery = new Grocery();
        String newGrocery = groceryItem.getText().toString();
        String newGroceryQuantity = quantity.getText().toString();
        grocery.setName(newGrocery);
        grocery.setQuantity(newGroceryQuantity);
        //Save to db
        db.addGrocery(grocery);
        Snackbar.make(view,"item saved",Snackbar.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                //start new activity
                startActivity(new Intent(MainActivity.this,ListActivity.class));
            }
        },1200);
    }
    public void byPassActivity(){
        //checks if database is empty; if not then we just
        //go to ListActivity and show all added items
        if (db.getGroceriesCount() > 0){
            startActivity(new Intent(MainActivity.this , ListActivity.class));
            finish();
        }
    }
}