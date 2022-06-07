package com.example.tasktimer;

import android.util.Log;

import java.io.Serializable;
import java.util.Date;

class Timing implements Serializable
{
    private static final long serialVersionUID = 20161120L;
    private static final String TAG = "Timing";

    private long m_Id;
    private Task mTask;
    private long mStartTime;
    private long mDuration;

    public Timing(Task task) {
        mTask = task;
        Date currentTime = new Date();
        mStartTime = currentTime.getTime()/1000;
        mDuration = 0;
    }

    long getId() {
        return m_Id;
    }

    void setId(long id) {
        m_Id = id;
    }

    Task getTask() {
        return mTask;
    }

    void setTask(Task task) {
        mTask = task;
    }

    long getStartTime() {
        return mStartTime;
    }

    void setStartTime(long startTime) {
        mStartTime = startTime;
    }

    long getDuration() {
        return mDuration;
    }

    void setDuration() {
        Date currentTime = new Date();
        mDuration = (currentTime.getTime() /1000) - mStartTime;//working in seconds
        Log.d(TAG, mTask.getId() +" - start time : "+mStartTime +" | Duration "+mDuration);
    }
}
