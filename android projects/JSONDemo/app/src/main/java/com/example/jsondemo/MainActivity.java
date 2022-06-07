package com.example.jsondemo;

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
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadTask task = new DownloadTask();
        task.execute("http://api.openweathermap.org/data/2.5/weather?q=London,uk");
    }
    public class DownloadTask extends AsyncTask<String , Void , String>
    {
        @Override
        protected String doInBackground(String... strings) {
            String result ="";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1)
                {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo =  jsonObject.getString("weather");
                Log.d(TAG, "onPostExecute: "+weatherInfo);
                JSONArray arr = new JSONArray(weatherInfo);
                for (int i = 0; i < arr.length() ; i++)
                {
                    JSONObject jsonPart = arr.getJSONObject(i);
                    Log.d(TAG, "onPostExecute: " + jsonPart.getString("main"));
                    Log.d(TAG, "onPostExecute: "+jsonObject.getString("description"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "onPostExecute: "+s);
        }
    }
}