package com.example.noticationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(getApplicationContext() , MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivities(getApplicationContext() , 1 , new Intent[]{intent} , 0);
        Notification notification = new Notification.Builder(getApplicationContext()).setContentTitle("LAnuch is reade")
                .setContentText("it's getting cold").setSmallIcon(android.R.drawable.btn_radio)
                .setContentIntent(pendingIntent).addAction(android.R.drawable.sym_action_chat , "Chat" , pendingIntent).build();
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1 , notification);

    }
}