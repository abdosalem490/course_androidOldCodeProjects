package com.example.newreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ArrayList<String>titles = new ArrayList<>();
    ArrayList<String>content = new ArrayList<>();
    SQLiteDatabase articlesDB;
    ArrayAdapter mArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);
        mArrayAdapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1, titles);
        articlesDB = this.openOrCreateDatabase("Article",MODE_PRIVATE, null);
        articlesDB.execSQL("CREATE TABLE IF NOT EXISTS articles(id INTEGER PRIMARY KEY,articleId INTEGER,title VARCHAR,content  VARCHAR)");

        listView.setAdapter(mArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext() , ArticleActivity.class);
                intent.putExtra("content",content.get(position));
                startActivity(intent);
            }
        });
        updateListView();
        DownloadTask task = new DownloadTask();
        try {
            task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    private void updateListView()
    {
        Cursor cursor = articlesDB.rawQuery("SELECT * FROM articles",null);
        int contentIndex = cursor.getColumnIndex("content");
        int titleIndex = cursor.getColumnIndex("title");
        if (cursor.moveToFirst())
        {
            titles.clear();
            content.clear();
            do {
                titles.add(cursor.getString(titleIndex));
                content.add(cursor.getString(contentIndex));
            }while (cursor.moveToNext());
            mArrayAdapter.notifyDataSetChanged();
        }
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
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1)
                {
                    char current = (char)data;
                    result+= current;
                    data = reader.read();
                }
               // Log.d(TAG, "doInBackground: "+result);
                JSONArray jsonArray = new JSONArray(result);
                articlesDB.execSQL("DELETE FROM articles");
                int numOfArticles = 20;
                if (numOfArticles > jsonArray.length())
                {
                    numOfArticles = jsonArray.length();
                }
                for (int i = 0 ; i < numOfArticles ; i++)
                {
                   // Log.d(TAG, "doInBackground: "+jsonArray.getString(i));
                    String articleId = jsonArray.getString(i);
                    url = new URL("https://hacker-news.firebaseio.com/v0/item/"+articleId+".json?print=pretty");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    in = urlConnection.getInputStream();
                    reader = new InputStreamReader(in);
                    data = reader.read();
                    String articleInfo = "";
                    while (data != -1)
                    {
                        char current = (char)data;
                        articleInfo += current;
                        data = reader.read();
                    }
                  //  Log.d(TAG, "doInBackground: "+articleInfo);
                    JSONObject jsonObject = new JSONObject(articleInfo);
                    if (!jsonObject.isNull("title") && !jsonObject.isNull("url"))
                    {
                        String articleTitle = jsonObject.getString("title");
                        String articleURL = jsonObject.getString("url");
                       // Log.d(TAG, "doInBackground: "+articleURL);
                        url = new URL(articleURL);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        in = urlConnection.getInputStream();
                        reader = new InputStreamReader(in);
                        data = reader.read();
                        String articleContent = "";
                        while (data != -1)
                        {
                            char current = (char)data;
                            articleContent += current;
                            data = reader.read();
                        }
                       // Log.d(TAG, "doInBackground: "+articleContent);
                        String sql = "INSERT INTO articles(articleId , title , content) VALUES (? , ? , ?)";
                        SQLiteStatement statement = articlesDB.compileStatement(sql);
                        statement.bindString(1 , articleId);
                        statement.bindString(2 , articleTitle);
                        statement.bindString(3 , articleContent);
                        statement.execute();
                    }


                }
            }catch (MalformedURLException e)
            {
                e.printStackTrace();
            }catch (IOException e)
            {
                e.printStackTrace();
            }catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateListView();
        }

    }

}