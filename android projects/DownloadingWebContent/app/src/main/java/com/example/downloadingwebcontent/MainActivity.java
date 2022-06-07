package com.example.downloadingwebcontent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadTask task = new DownloadTask();
        String result = null;
        try {
            result = task.execute("https://www.ecowebhosting.co.uk/").get();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }catch (ExecutionException e)
        {
            e.printStackTrace();
        }

        Log.d(TAG, "onCreate: "+result);
        //"https://www.ecowebhosting.co.uk/"
    }
    public class DownloadTask extends AsyncTask<String , Void , String>
    {
        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader  = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1)
                {
                    char current = (char)data;
                    result += current;
                    data = reader.read();
                }
                return result;
            }catch (Exception e)
            {
                e.printStackTrace();
                return "Failed";
            }

        }
    }
}