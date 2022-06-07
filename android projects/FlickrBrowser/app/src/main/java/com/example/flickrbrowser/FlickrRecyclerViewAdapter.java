package com.example.flickrbrowser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder>
{
    private static final String TAG = "FlickrRecyclerViewAdapt";
    private List<Photo> mPhotoList;
    private Context mContext;

    public FlickrRecyclerViewAdapter(Context context , List<Photo> photoList) {
        mPhotoList = photoList;
        mContext = context;
    }

    @NonNull
    @Override
    public FlickrImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: new view rewquested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse , parent , false);
        return new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrImageViewHolder holder, int position) {
        if (mPhotoList == null || mPhotoList.size() == 0)
        {
            holder.thumbnail.setImageResource(R.drawable.placeholder);
            holder.title.setText(R.string.emptyphoto);
        }
        else
        {
            Photo photoItem = mPhotoList.get(position);
            Log.d(TAG, "onBindViewHolder: " + photoItem.getTitle() +" --> "+position);
            Picasso.with(mContext).load(photoItem.getImage()).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(holder.thumbnail);
            holder.title.setText(photoItem.getTitle());
        }
        
    }
    void loadNewData(List<Photo> newPhoto)
    {
        mPhotoList = newPhoto;
        notifyDataSetChanged();
    }
    public Photo getPhoto(int position)
    {
        return ((mPhotoList != null)&&(mPhotoList.size() !=0)? mPhotoList.get(position) : null);
    }

    @Override
    public int getItemCount() {
        return ((mPhotoList!= null)&&(mPhotoList.size() != 0) ? mPhotoList.size() : 1);
    }

    static class FlickrImageViewHolder extends RecyclerView.ViewHolder
    {
        private static final String TAG = "FlickrImageViewHolder";
        ImageView thumbnail =null;
        TextView title = null;

        public FlickrImageViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "FlickrImageViewHolder: starts");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title_view);
        }
    }
}
