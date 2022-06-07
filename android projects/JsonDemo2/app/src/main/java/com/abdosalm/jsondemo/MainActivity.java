package com.abdosalm.jsondemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public class DownloadTask extends AsyncTask<String , Void , String>{
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1){
                    result.append((char) data);
                    data = reader.read();
                }
                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo  = jsonObject.getString("weather");
             //   Log.i(TAG, "onPostExecute: wether is "+weatherInfo);
                JSONArray jsonArray = new JSONArray(weatherInfo);
                for (int i =0 ;i < jsonArray.length() ; i++){
                    JSONObject jsonPart = jsonArray.getJSONObject(i);
                    Log.d(TAG, "onPostExecute: "+jsonPart.getString("description"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        try {
               task.execute("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=27160a7ed58a39a283978fd6310f6066").get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}