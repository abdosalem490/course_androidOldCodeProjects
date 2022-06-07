package com.example.tasktimer;

import java.io.Serializable;

public class Task implements Serializable
{
    public static final long serialVersionUID = 20161120L;
    private long m_id;
    private final String mName;
    private final String mDescription;
    private final int mSortOrder;

    public Task(long id, String name, String description, int sortOrder)
    {
        this.m_id = id;
        mName = name;
        mDescription = description;
        mSortOrder = sortOrder;
    }

    long getId() {
        return m_id;
    }

    String getName() {
        return mName;
    }

    String getDescription() {
        return mDescription;
    }

    int getSortOrder() {
        return mSortOrder;
    }

    void setId(long id)
    {
        this.m_id = id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "m_id=" + m_id +
                ", mName='" + mName + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mSortOrder=" + mSortOrder +
                '}';
    }
}
