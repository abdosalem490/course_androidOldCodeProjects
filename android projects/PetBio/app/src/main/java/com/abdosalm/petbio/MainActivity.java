package com.abdosalm.petbio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView catView;
    private ImageView dogView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        catView = findViewById(R.id.catId);
        dogView = findViewById(R.id.dogId);

        catView.setOnClickListener(this);
        dogView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.catId:
                Intent catIntent = new Intent(getApplicationContext(),BioActivity.class);
                catIntent.putExtra("name","Jarvis");
                catIntent.putExtra("bio","Great Cat love people and meows a lot");
                startActivity(catIntent);
                break;
            case R.id.dogId:
                Intent dogIntent = new Intent(getApplicationContext(),BioActivity.class);
                dogIntent.putExtra("name","Dufus");
                dogIntent.putExtra("bio","Great dog love people and barks nad eats a lot");
                startActivity(dogIntent);
                break;
        }
    }
}