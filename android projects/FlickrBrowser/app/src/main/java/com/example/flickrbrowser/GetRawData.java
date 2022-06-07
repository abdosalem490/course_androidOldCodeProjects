package com.example.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus { IDLE , PROCESSING , NOT_INITIALISED , FAILED_OR_EMPTY , OK}

public class GetRawData extends AsyncTask<String , Void , String>
{
    private final OnDownloadComplete mCallback;
    private static final String TAG = "GetRawData";

    private DownloadStatus mDownloadStatus;

    interface  OnDownloadComplete{
        void onDownloadComplete(String  data , DownloadStatus status);
    }
    public GetRawData(OnDownloadComplete Callback)
    {
       this.mDownloadStatus = DownloadStatus.IDLE;
       mCallback = Callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        if (strings == null)
        {
            mDownloadStatus = DownloadStatus.NOT_INITIALISED;
            return null;
        }

        try
        {
            mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            Log.d(TAG, "doInBackground: response code was "+responseCode);
            StringBuilder result = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while (null != (line = reader.readLine()))
            {
                result.append(line).append("\n");
            }
            mDownloadStatus = DownloadStatus.OK;
            return result.toString();
        }
        catch (MalformedURLException e)
        {
            Log.e(TAG, "doInBackground: Invalid URL " +e.getMessage() );
        }
        catch (IOException e)
        {
            Log.e(TAG, "doInBackground: IO Exception reading data "+e.getMessage() );
        }
        catch (SecurityException e)
        {
            Log.e(TAG, "doInBackground: Security Exception : Needs permission "+e.getMessage() );
        }finally {
            if (connection != null)
            {
                connection.disconnect();
            }
            if (reader != null)
            {
                try {
                    reader.close();
                }catch (IOException e)
                {
                    Log.e(TAG, "doInBackground: error closing stream" + e.getMessage() );
                }
            }
        }
        mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }

    @Override
    protected void onPostExecute(String s) {

        //Log.d(TAG, "onPreExecute: parameter = "+ s);
        if (mCallback != null)
        {
            mCallback.onDownloadComplete(s , mDownloadStatus);
        }
        Log.d(TAG, "onPostExecute: ends");

    }
    void  runInTheSameThread(String s)
    {
        Log.d(TAG, "runInTheSameThread: starts");
        if (mCallback != null)
        {
            //onPostExecute(doInBackground(s));
           // String results = doInBackground(s);
           // mCallback.onDownloadComplete(results , mDownloadStatus);
            mCallback.onDownloadComplete(doInBackground(s) , mDownloadStatus);
        }

        Log.d(TAG, "runInTheSameThread: ends");
    }
}
