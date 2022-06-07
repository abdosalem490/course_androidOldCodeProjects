package com.example.top10downloaded;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ListView listApps;
    private static final String TAG = "MainActivity";
    private String feedUrl ="\"http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml\"";
    private int feedLimit = 10;
    private String feedCachedUrl = "INVALIDATED";
    public static final String STATE_URL = "feedUrl";
    public static final String STATE_LIMIT = "feedLimit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listApps =(ListView) findViewById(R.id.xmlListView);
        if (savedInstanceState != null)
        {
            feedUrl = savedInstanceState.getString(STATE_URL);
            feedLimit = savedInstanceState.getInt(STATE_LIMIT);
        }
        downloadUrl(String.format(feedUrl , feedLimit));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       getMenuInflater().inflate(R.menu.feeds_manu , menu);
       if (feedLimit == 10)
       {
           menu.findItem(R.id.mnu10).setChecked(true);
       }else
       {
           menu.findItem(R.id.mnu25).setChecked(true);
       }
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.mnuFree:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
                break;
            case R.id.mnuPaid:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml";
                break;
            case R.id.mnuSongs:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml";
                break;
            case R.id.mnu10:
            case R.id.mnu25 :
                if (!item.isChecked())
                {
                    item.setChecked(true);
                    feedLimit = 35-feedLimit;
                    Log.d(TAG, "onOptionsItemSelected: "+item.getTitle() +" setting feedLimit to "+feedLimit);
                }
                else
                {
                    Log.d(TAG, "onOptionsItemSelected: "+item.getTitle() + " feedLimit unchanged");
                }
                break;
            case R.id.mnuRefresh :
                feedCachedUrl = "INVALIDATED";
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        downloadUrl(String.format(feedUrl , feedLimit));

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putString(STATE_URL, feedUrl);
        outState.putInt(STATE_LIMIT, feedLimit);
        super.onSaveInstanceState(outState);
    }

    private void downloadUrl(String feedUrl)
    {
        if (!feedUrl.equalsIgnoreCase(feedCachedUrl)) {
            Log.d(TAG, "menu: starting AsyncTask");
            DownloadData downloadData = new DownloadData();
            downloadData.execute(feedUrl);
            feedCachedUrl = feedUrl;
            Log.d(TAG, "menu: done");
        }else
        {
            Log.d(TAG, "downloadUrl: URL not changed");
        }
    }
    private class DownloadData extends AsyncTask<String , Void , String>
    {
        private static final String TAG = "DownloadData";
        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: starts with "+ strings);
            String rssFeed = downloadXML(strings[0]);
            if (rssFeed== null)
            {
                Log.e(TAG, "doInBackground: error downloading" );
            }
            return rssFeed;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // Log.d(TAG , "onPostExecute : Parameter is "+s);
            ParseApplications parseApplications = new ParseApplications();
            parseApplications.parse(s);
          /*  ArrayAdapter<FeedEntry> arrayAdapter = new ArrayAdapter<>(MainActivity.this , R.layout.list_item , parseApplications.getApplications());
            listApps.setAdapter(arrayAdapter);*/
            FeedAdapter<FeedEntry> feedAdapter = new FeedAdapter<>(MainActivity.this , R.layout.list_record , parseApplications.getApplications());
            listApps.setAdapter(feedAdapter);

        }
        private String downloadXML(String urlPath)
        {
            StringBuilder xmlResult = new StringBuilder();
            try {
               URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: The response code was "+response);
              /*  InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);*/
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                int charsRead;
                char[] inputBuffer = new char[500];
                while (true)
                {
                    charsRead = reader.read(inputBuffer);
                    if (charsRead < 0) break;
                    if (charsRead > 0)
                    {
                        xmlResult.append(String.copyValueOf(inputBuffer , 0  , charsRead));
                    }
                }
                reader.close();
                return xmlResult.toString();
            }catch (MalformedURLException e)
            {
                Log.e(TAG, "downloadXML: Invalid URL "+e.getMessage() );
            }catch (IOException e)
            {
                Log.e(TAG, "downloadXML: IO Exception reading data "+e.getMessage() );
            }catch (SecurityException e)
            {
                Log.e(TAG, "downloadXML: Security Exception need Permission ");
                e.printStackTrace();
            }
            return null;
        }
    }

}