package com.example.tasktimer;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class DurationsRVAdapter extends RecyclerView.Adapter<DurationsRVAdapter.ViewHolder> {
    private Cursor mCursor;

    private final java.text.DateFormat mDateFormat;

    public DurationsRVAdapter(Context context, Cursor cursor) {
        this.mCursor = cursor;
        mDateFormat = DateFormat.getDateFormat(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_durations_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if ((mCursor != null) && (mCursor.getCount() != 0)) {
            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Couldn't move cursor to position " + position);
            }
            String name = mCursor.getString(mCursor.getColumnIndex(DurationsContract.Columns.DURATIONS_NAME));
            String description = mCursor.getString(mCursor.getColumnIndex(DurationsContract.Columns.DURATIONS_DESCRIPTION));
            Long startTime = mCursor.getLong(mCursor.getColumnIndex(DurationsContract.Columns.DURATIONS_START_TIME));
            long totalDuration = mCursor.getLong(mCursor.getColumnIndex(DurationsContract.Columns.DURATIONS_DURATION));

            holder.name.setText(name);
            if (holder.description != null) {    // Description is not present in portrait
                holder.description.setText(description);
            }

            String userDate = mDateFormat.format(startTime * 1000);
            String totalTime = formatDuration(totalDuration);

            holder.startDate.setText(userDate);
            holder.duration.setText(totalTime);
        }
    }

 /*   @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }
    */

    @Override
    public int getItemCount()
    {
        return mCursor != null ? mCursor.getCount() : 0;
        //  Log.d(TAG, "getItemCount: starts");

    }

    private String formatDuration(long duration)
    {
        long hours = duration / 3600;
        long remainder = duration - (hours * 3600);
        long minutes = remainder / 60;
        long seconds = remainder - (minutes * 60);

        return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
    }

    Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        int numItems = getItemCount();
        final Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if(newCursor != null) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeRemoved(0, numItems);
        }
        return oldCursor;

    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        TextView startDate;
        TextView duration;

        public ViewHolder(View itemView) {
            super(itemView);

            this.name = (TextView)itemView.findViewById(R.id.td_name);
            this.description = (TextView)itemView.findViewById(R.id.td_description);
            this.startDate = (TextView)itemView.findViewById(R.id.td_start);
            this.duration = (TextView)itemView.findViewById(R.id.td_duration);
        }
    }

}
