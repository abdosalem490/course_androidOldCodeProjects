package com.example.whatistheweather;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText cityName;
    TextView resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityName = findViewById(R.id.cityName);
        resultTextView = findViewById(R.id.resultTextView);

    }
    public void findWeather(View view)
    {
        Log.d(TAG, "findWeather:"+cityName.getText().toString());
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityName.getWindowToken() , 0);
        try {
            String encodedCityName = URLEncoder.encode(cityName.getText().toString(),"UTF-8");
            DownloadTask task = new DownloadTask();
            task.execute("http://api.openweathermap.org/data/2.5/weather?q="+encodedCityName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this , "Couldn't find weather" , Toast.LENGTH_SHORT).show();
        }

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
            } catch (Exception e) {
                Toast.makeText(MainActivity.this , "Couldn't find weather" , Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                String message = "";
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo =  jsonObject.getString("weather");
                Log.d(TAG, "onPostExecute: "+weatherInfo);
                JSONArray arr = new JSONArray(weatherInfo);
                for (int i = 0; i < arr.length() ; i++)
                {
                    JSONObject jsonPart = arr.getJSONObject(i);
                    String main = "";
                    String description = "";
                    main = jsonPart.getString("main");
                    description = jsonObject.getString("description");
                    if (main != "" && description != "")
                    {
                        message += main+" : "+description+"\r\n";
                    }

                }
                if (message != "")
                {
                    resultTextView.setText(message);
                }else{
                    Toast.makeText(MainActivity.this , "Couldn't find weather" , Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(MainActivity.this , "Couldn't find weather" , Toast.LENGTH_SHORT).show();
            }
            Log.i(TAG, "onPostExecute: "+s);
        }
    }
}