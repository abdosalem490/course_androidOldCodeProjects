package com.abdosalm.newsapp;

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
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> content = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    SQLiteDatabase articlesDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        articlesDB = this.openOrCreateDatabase("Articles",MODE_PRIVATE,null);
        articlesDB.execSQL("CREATE TABLE IF NOT EXISTS articles(id INTEGER PRIMARY KEY,articleId INTEGER,title VARCHAR,content VARCHAR)");

        DownloadTask task = new DownloadTask();
        try {
           // task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty").get();
        }catch (Exception e){
            e.printStackTrace();
        }

        ListView listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,titles);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,ArticleActivity.class);
                intent.putExtra("content",content.get(position));
                startActivity(intent);
            }
        });
        updateListView();
    }
    public class DownloadTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                int data = inputStreamReader.read();
                while (data != -1){
                    result.append((char) data);
                    data = inputStreamReader.read();
                }
                JSONArray jsonArray = new JSONArray(result.toString());
                int numberOfItems = 20;
                if (jsonArray.length() < 20){
                    numberOfItems = jsonArray.length();
                }
                articlesDB.execSQL("DELETE FROM articles");
                for (int i = 0 ; i < numberOfItems ; i++){
                    String articleId = jsonArray.getString(i);
                    url = new URL("https://hacker-news.firebaseio.com/v0/item/"+articleId+".json?print=pretty");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    in = urlConnection.getInputStream();
                    inputStreamReader = new InputStreamReader(in);
                    data = inputStreamReader.read();
                    StringBuilder articleInfo = new StringBuilder();
                    while (data != -1){
                        articleInfo.append((char) data);
                        data = inputStreamReader.read();
                    }
                   // Log.i(TAG, "doInBackground: " + articleInfo.toString());
                    JSONObject jsonObject = new JSONObject(articleInfo.toString());
                    if (!jsonObject.isNull("title")&&!jsonObject.isNull("url")){
                        String articleTitle = jsonObject.getString("title");
                        String articleUrl = jsonObject.getString("url");
                        url = new URL(articleUrl);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        in = urlConnection.getInputStream();
                        inputStreamReader = new InputStreamReader(in);
                        data = inputStreamReader.read();
                        StringBuilder articleContent = new StringBuilder();
                        while (data != -1){
                            articleContent.append((char) data);
                            data = inputStreamReader.read();
                        }
                        String sql = "INSERT INTO articles (articleId,title,content) VALUES (?,?,?)";
                        SQLiteStatement statement = articlesDB.compileStatement(sql);
                        statement.bindString(1,articleId);
                        statement.bindString(2,articleTitle);
                        statement.bindString(3,articleContent.toString());

                        statement.execute();
                    }
                }

                return result.toString();
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateListView();
        }
    }
    public void updateListView(){
        Cursor c = articlesDB.rawQuery("SELECT * FROM articles",null);
        int contentIndex = c.getColumnIndex("content");
        int titleIndex = c.getColumnIndex("title");
        if (c.moveToFirst()){
            titles.clear();
            content.clear();
            do {
                titles.add(c.getString(titleIndex));
                content.add(c.getString(contentIndex));
            }while (c.moveToNext());
            arrayAdapter.notifyDataSetChanged();
        }
    }

}