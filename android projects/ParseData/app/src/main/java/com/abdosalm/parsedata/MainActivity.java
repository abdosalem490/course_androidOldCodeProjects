package com.abdosalm.parsedata;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    RequestQueue queue;
    String url = "https://www.google.com";
    String apiUrl = "https://jsonplaceholder.typicode.com/todos";
    String getApiUrl = "https://jsonplaceholder.typicode.com/todos/1";
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textview);
        queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getApiUrl, null,
                response -> {
                    try {
                        textView.setText(response.getString("title"));
                        Log.d(TAG, "onCreate: "+ response.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Log.d(TAG, "onCreate: failed");
        });
        queue.add(jsonObjectRequest);
      //  getJsonArrayRequest();
      //  getString(queue);
    }

    private void getJsonArrayRequest() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
                response -> {
                    for (int i = 0; i <response.length() ; i++)
                    {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Log.d(TAG, "onCreate: "+jsonObject.get("userId"));
                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, error -> {
                   Log.d(TAG, "onCreate: failed");
                });
        queue.add(jsonArrayRequest);
    }

    private void getString(RequestQueue queue) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {

            Log.d(TAG, "onCreate: "+response.substring(0 , 500));

        }, error -> {
            Log.d(TAG, "onCreate: failed to get");

        });
        queue.add(stringRequest);
    }
}