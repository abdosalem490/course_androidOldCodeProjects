package com.example.flickrbrowser;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetFlickrJsonData  extends AsyncTask<String , Void , List<Photo>> implements GetRawData.OnDownloadComplete
{
    private static final String TAG = "GetFlickrJsonData";
    private List<Photo> mPhotoList = null;
    private String mBaseURL;
    private String mLanguage;
    private boolean mMatchAll;
    private boolean runningOnsameThread = false;
    private final OnDataAvailable mCallBack;
    interface OnDataAvailable {
        void onDataAvailable(List<Photo> data , DownloadStatus status);
    }

    public GetFlickrJsonData( OnDataAvailable callBack, String baseURL, String language, boolean matchAll)
    {
        Log.d(TAG, "GetFlickrJsonData: called");
        mBaseURL = baseURL;
        mLanguage = language;
        mMatchAll = matchAll;
        mCallBack = callBack;
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: starts . Status = "+status);
        if (status == DownloadStatus.OK)
        {
            mPhotoList =  new ArrayList<>();
            try{
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("items");
                for (int i = 0;  i < itemsArray.length() ; i++)
                {
                    JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorId = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");
                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String  photoUrl = jsonMedia.getString("m");
                    String link = photoUrl.replaceFirst("_m.","_b.");
                    Photo photoObject = new Photo(title , author , authorId , link , tags , photoUrl);
                    mPhotoList.add(photoObject);
                    Log.d(TAG, "onDownloadComplete: complete "+photoObject.toString());
                }
            }catch (JSONException json)
            {
                json.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing Json data "+json.getMessage());
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
        if (runningOnsameThread && mCallBack != null)
        {
            mCallBack.onDataAvailable(mPhotoList , status);
        }
        Log.d(TAG, "onDownloadComplete: ends");
    }
    void executeOnSameThread(String searchCriteria)
    {
        Log.d(TAG, "executeOnSameThread: starts");
        runningOnsameThread = true;
        String destinationUri = createUri(searchCriteria , mLanguage , mMatchAll);

        GetRawData getRawData = new GetRawData(this);
        getRawData.equals(destinationUri);
        Log.d(TAG, "executeOnSameThread: ends");
    }
    private String createUri(String searchCiteria , String lang , boolean matchAll)
    {
        Log.d(TAG, "createUri: starts");

        return Uri.parse(mBaseURL).buildUpon().appendQueryParameter("tags" , searchCiteria).appendQueryParameter("tag" , matchAll? "ALL" :"ANY").appendQueryParameter("lang", lang).appendQueryParameter("format","json").appendQueryParameter("nojsoncallback","1").build().toString();

    }

    @Override
    protected List<Photo> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: starts");
        String destinationUri = createUri(params[0] , mLanguage , mMatchAll);
        GetRawData getRawData = new GetRawData(this);
        getRawData.runInTheSameThread(destinationUri);
        Log.d(TAG, "doInBackground: ends");
        return mPhotoList;
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        Log.d(TAG, "onPostExecute: starts");
        if (mCallBack != null)
        {
            mCallBack.onDataAvailable(mPhotoList , DownloadStatus.OK);
            Log.d(TAG, "onPostExecute: ends");
        }
    }
}

