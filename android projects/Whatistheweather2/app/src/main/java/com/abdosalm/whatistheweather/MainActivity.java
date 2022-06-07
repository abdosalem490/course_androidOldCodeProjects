package com.abdosalm.whatistheweather;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    EditText editText;
    TextView resultTextView;
    public class DownloadTask extends AsyncTask<String , Void , String> {
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
                JSONArray jsonArray = new JSONArray(weatherInfo);
                String message ="";
                for (int i =0 ;i < jsonArray.length() ; i++){
                    JSONObject jsonPart = jsonArray.getJSONObject(i);
                    String main = jsonPart.getString("main");
                    String description = jsonPart.getString("description");
                    if (!main.equals("") && !description.equals("")){
                        message += main +" : "+description+"\r\n";
                    }
                }
                if (!message.equals("")){
                    resultTextView.setText(message);
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

        editText = findViewById(R.id.editText);
        resultTextView = findViewById(R.id.resultTextView);
    }
    public void getWeather(View view){
        String city = editText.getText().toString().trim();
        if (!TextUtils.isEmpty(city)){
            DownloadTask task = new DownloadTask();
            try {
                task.execute("https://samples.openweathermap.org/data/2.5/weather?q="+city+"&appid=27160a7ed58a39a283978fd6310f6066").get();
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(resultTextView.getWindowToken() , 0);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "enter a city", Toast.LENGTH_SHORT).show();
        }
    }
}