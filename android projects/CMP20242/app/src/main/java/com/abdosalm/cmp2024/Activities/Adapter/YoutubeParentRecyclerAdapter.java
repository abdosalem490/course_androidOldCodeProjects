package com.abdosalm.cmp2024.Activities.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdosalm.cmp2024.Activities.Model.YoutubeSemester;
import com.abdosalm.cmp2024.R;

import java.util.List;

public class YoutubeParentRecyclerAdapter extends RecyclerView.Adapter<YoutubeParentRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<YoutubeSemester> youtubeSemesterList;

    public YoutubeParentRecyclerAdapter(Context context, List<YoutubeSemester> youtubeSemesterList) {
        this.context = context;
        this.youtubeSemesterList = youtubeSemesterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_actuvuty_parent_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return youtubeSemesterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
