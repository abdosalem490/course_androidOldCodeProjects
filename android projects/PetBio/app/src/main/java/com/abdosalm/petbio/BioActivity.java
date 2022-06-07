package com.abdosalm.petbio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class BioActivity extends AppCompatActivity {
    private ImageView image;
    private TextView nameText;
    private TextView bioText;
    private Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);

        image = findViewById(R.id.petBioImageView);
        nameText = findViewById(R.id.nameIdTextView);;
        bioText = findViewById(R.id.bioTextTextView);

        extras = getIntent().getExtras();
        if (extras != null){
            String name = extras.getString("name");
            String bio = extras.getString("bio");

            setUp(name,bio);
        }
    }
    public void setUp(String name,String bio){
        if (name.equals("Jarvis")){
            image.setImageDrawable(getResources().getDrawable(R.drawable.icon_lg_cat));
            bioText.setText(bio);
            nameText.setText(name);
        }else if (name.equals("Dufus")){
            image.setImageDrawable(getResources().getDrawable(R.drawable.icon_lg_dog));
            bioText.setText(bio);
            nameText.setText(name);
        }
    }
}