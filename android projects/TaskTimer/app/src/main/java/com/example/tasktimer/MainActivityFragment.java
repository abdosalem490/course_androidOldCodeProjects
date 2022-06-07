package com.example.tasktimer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, CursorRecyclerViewAdapter.OnTaskClickListener {
    private static final String TAG = "MainActivityFragment";

    public static final int LOADER_ID = 0;

    private CursorRecyclerViewAdapter mAdapter;
    private Timing mCurrentTiming = null;

    public MainActivityFragment() {
        Log.d(TAG, "MainActivityFragment: starts");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: starts");
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();

        if (!(activity instanceof CursorRecyclerViewAdapter.OnTaskClickListener)) {
            throw new ClassCastException(activity.getClass().getSimpleName() + " must implement aCursorRecyclerViewAdapter.OnTaskClickListener interface");

        }
        getLoaderManager().initLoader(LOADER_ID, null, this);
        setTimingText(mCurrentTiming);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.task_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (mAdapter == null) {
            mAdapter = new CursorRecyclerViewAdapter(null, this);
        }


        recyclerView.setAdapter(mAdapter);
        Log.d(TAG, "onCreateView: returning");
        return view;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader: starts with id " + id);
        String[] projection = {TasksContract.Columns.STRING_ID, TasksContract.Columns.TASKS_NAME,
                TasksContract.Columns.TASKS_DESCRIPTION, TasksContract.Columns.TASKS_SORTORDER};
        String sortOrder = TasksContract.Columns.TASKS_SORTORDER + "," + TasksContract.Columns.TASKS_NAME + " COLLATE NOCASE";

        switch (id) {
            case LOADER_ID:
                return new CursorLoader(getActivity(),
                        TasksContract.CONTENT_URI,
                        projection,
                        null,
                        null,
                        sortOrder);
            default:
                throw new InvalidParameterException(TAG + ".onCreateLoader called with invalid loader id" + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "Entering onLoadFinished");
        mAdapter.swapCursor(data);
        int count = mAdapter.getItemCount();

        Log.d(TAG, "onLoadFinished: count is " + count);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: starts");
        mAdapter.swapCursor(null);
    }

    @Override
    public void onEditClick(@NotNull Task task) {
        Log.d(TAG, "onEditClick: called");
        CursorRecyclerViewAdapter.OnTaskClickListener listener = (CursorRecyclerViewAdapter.OnTaskClickListener) getActivity();
        if (listener != null) {
            listener.onEditClick(task);
        }
    }

    @Override
    public void onDeleteClick(@NotNull Task task) {
        Log.d(TAG, "onDeleteClick: called");
        CursorRecyclerViewAdapter.OnTaskClickListener listener = (CursorRecyclerViewAdapter.OnTaskClickListener) getActivity();
        if (listener != null) {
            listener.onDeleteClick(task);
        }
    }

    @Override
    public void onTaskLongClick(@NotNull Task task) {
        Log.d(TAG, "onTaskLongClick: called");

        if (mCurrentTiming != null) {
            if (task.getId() == mCurrentTiming.getTask().getId()) {
                saveTiming(mCurrentTiming);
                mCurrentTiming = null;
                setTimingText(null);
            } else {
                saveTiming(mCurrentTiming);
                mCurrentTiming = new Timing(task);
                setTimingText(mCurrentTiming);
            }
        } else {
            mCurrentTiming = new Timing(task);
            setTimingText(mCurrentTiming);
        }
    }

    private void saveTiming(@NonNull Timing currentTiming) {
        Log.d(TAG, "entering saveTiming");
        mCurrentTiming.setDuration();
        ContentResolver contentResolver = getActivity().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(TimingsContract.Columns.TIMINGS_TASK_ID, currentTiming.getTask().getId());
        values.put(TimingsContract.Columns.TIMINGS_START_TIME, currentTiming.getStartTime());
        values.put(TimingsContract.Columns.TIMINGS_DURATION, mCurrentTiming.getDuration());

        contentResolver.insert(TimingsContract.CONTENT_URI, values);

        Log.d(TAG, "exiting saveTiming");
    }

    private void setTimingText(Timing timing) {
        TextView taskName = getActivity().findViewById(R.id.current_task);
        if (timing != null) {
            taskName.setText(getString(R.string.current_timing_text , timing.getTask().getName()));
        } else {
            taskName.setText(R.string.no_task_message);
        }
    }

}