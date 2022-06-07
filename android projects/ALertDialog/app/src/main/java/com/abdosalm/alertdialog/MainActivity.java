package com.abdosalm.alertdialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button showDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showDialog = findViewById(R.id.showDialog);
        showDialog.setOnClickListener(v->{
            // show the actual alert dialog
           final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());

            //set things up
            alertDialog.setTitle(R.string.title);
            alertDialog.setMessage(getResources().getString(R.string.message));
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Exit
                    MainActivity.this.finish();
                }
            });
            alertDialog.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog dialog = alertDialog.create();
            dialog.show();
        });
    }
}