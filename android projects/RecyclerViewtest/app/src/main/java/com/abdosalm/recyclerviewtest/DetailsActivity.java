package com.abdosalm.recyclerviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    private TextView dName;
    private TextView dDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        dName = findViewById(R.id.dNameId);
        dDescription = findViewById(R.id.dDescriptionID);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        String description = extras.getString("description");
        dName.setText(name);
        dDescription.setText(description);
    }
}