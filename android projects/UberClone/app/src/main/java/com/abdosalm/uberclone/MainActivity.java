package com.abdosalm.uberclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {
    Switch userTypeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        userTypeSwitch = findViewById(R.id.userTypeSwitch);

        if (ParseUser.getCurrentUser() == null){
            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null){

                    }else{
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            if (ParseUser.getCurrentUser().get("riderOrDriver") != null){
                redirectActivity();
            }
        }
    }
    public void getStarted(View view){
        String userType = "rider";
        if (userTypeSwitch.isChecked()){
            userType = "driver";
        }
        ParseUser.getCurrentUser().put("riderOrDriver",userType);
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                redirectActivity();
            }
        });

    }
    private void redirectActivity(){
        if (ParseUser.getCurrentUser().get("riderOrDriver").equals("rider")){
            Intent intent = new Intent(getApplicationContext() , RiderActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getApplicationContext(),ViewRequestsActivity.class);
            startActivity(intent);
        }
    }
}