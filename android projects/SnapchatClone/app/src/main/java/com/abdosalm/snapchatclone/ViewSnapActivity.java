package com.abdosalm.snapchatclone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ViewSnapActivity extends AppCompatActivity {
    TextView messageTextView;
    ImageView snapImageView;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_snap);

        messageTextView = findViewById(R.id.messageTextView);
        snapImageView = findViewById(R.id.snapImageView);

        messageTextView.setText(getIntent().getStringExtra("message"));
        DownloadTask task = new DownloadTask();
        Bitmap myBitmap;
        try {
            myBitmap = task.execute(getIntent().getStringExtra("imageURL")).get();
            snapImageView.setImageBitmap(myBitmap);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    class DownloadTask extends AsyncTask<String,Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                return BitmapFactory.decodeStream(in);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("snaps").child(getIntent().getStringExtra("snapKey")).removeValue();
        FirebaseStorage.getInstance().getReference().child("images").child(getIntent().getStringExtra("imageName")).delete();
    }
}