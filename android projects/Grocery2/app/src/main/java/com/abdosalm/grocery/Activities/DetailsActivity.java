package com.abdosalm.grocery.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.abdosalm.grocery.R;

public class DetailsActivity extends AppCompatActivity {
    private TextView itemName;
    private TextView quantity;
    private TextView dateAdded;
    private int groceryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        itemName = findViewById(R.id.itemNameDet);
        quantity = findViewById(R.id.quantityDet);
        dateAdded = findViewById(R.id.dateAddedDet);

        Bundle bundle = getIntent().getExtras();
        if (null != bundle){
            itemName.setText(bundle.getString("name"));
            quantity.setText(bundle.getString("quantity"));
            dateAdded.setText(bundle.getString("date"));
            groceryId = bundle.getInt("id");
        }
    }
}