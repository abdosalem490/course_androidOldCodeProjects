package com.example.downloadingimages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageDownloader task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void downloadImage(View view)
    {
        ImageView downloadedImage = findViewById(R.id.imageView);
        task =new ImageDownloader();
        try {
            Bitmap myImage = task.execute("https://static.straitstimes.com.sg/s3fs-public/styles/article_pictrure_780x520_/public/articles/2018/04/30/ST_20180430_LIFSPONGE_3945241.jpg?itok=KfaPMgUc&timestamp=1525016874").get();
            downloadedImage.setImageBitmap(myImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public class ImageDownloader extends AsyncTask<String , Void , Bitmap>
    {
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url= new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;
            }catch (MalformedURLException e)
            {
                e.printStackTrace();

            }catch (IOException e)
            {
                e.printStackTrace();
            }
            return  null;

        }
    }

}