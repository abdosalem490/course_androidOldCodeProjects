package com.example.tasktimer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppDatabase extends SQLiteOpenHelper
{
    private static final String TAG = "AppDatabase";
    public static  String DATABASE_NAME = "TaskTimer.db";
    public static int DATABASE_VERSION = 3;
    private static AppDatabase instance = null;
    private AppDatabase(Context context)
    {
        super(context , DATABASE_NAME ,null, DATABASE_VERSION);
        Log.d(TAG, "AppDatabase: constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: starts");
        String sSQL;

        sSQL = "CREATE TABLE "+TasksContract.TABLE_NAME + " ("+TasksContract.Columns.STRING_ID +" INTEGER PRIMARY KEY NOT NULL, "+TasksContract.Columns.TASKS_NAME +" TEXT NOT NULL, "
                +TasksContract.Columns.TASKS_DESCRIPTION + " TEXT, "+TasksContract.Columns.TASKS_SORTORDER +" INTEGER);";
        Log.d(TAG, sSQL);
        db.execSQL(sSQL);

        addTimingsTable(db);
        addDurationsView(db);

        Log.d(TAG, "onCreate: ends");
    }

    static AppDatabase getInstance(Context context)
    {
        if (instance  == null)
        {
            Log.d(TAG, "getInstance: creating new instance");
            instance = new AppDatabase(context);
        }
        return instance;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.d(TAG, "onUpgrade: starts");
        switch (oldVersion)
        {
            case 1:
                addTimingsTable(db);
            case 2:
                addDurationsView(db);
                break;
            default:
                throw  new IllegalStateException("onUpgrade() with unknown new version "+newVersion);
        }
        Log.d(TAG, "onUpgrade: ends");
    }
    private void addTimingsTable(SQLiteDatabase db)
    {

        String sSQL = "CREATE TABLE "+TimingsContract.TABLE_NAME+" (" + TimingsContract.Columns.STRING_ID+" INTEGER PRIMARY KEY NOT NULL, "+TimingsContract.Columns.TIMINGS_TASK_ID+" INTEGER NOT NULL, "+
                TimingsContract.Columns.TIMINGS_START_TIME+" INTEGER, "+TimingsContract.Columns.TIMINGS_DURATION+" INTEGER);";

        Log.d(TAG, sSQL);
        db.execSQL(sSQL);

        sSQL = "CREATE TRIGGER Remove_Task AFTER DELETE ON "+TasksContract.TABLE_NAME +" FOR EACH ROW BEGIN DELETE FROM "+TasksContract.TABLE_NAME
                +" WHERE "+TimingsContract.Columns.TIMINGS_TASK_ID +" = OLD."+TasksContract.Columns.STRING_ID+";"+" END;";
        Log.d(TAG, sSQL);
        db.execSQL(sSQL);
    }
    private void addDurationsView(SQLiteDatabase db)
    {
        /*CREATE VIEW vwTaskDurations AS SELECT Timings._id , Tasks.Name , Tasks.Description , Timings.startTime , DATE(Timings.StartTime , 'unixepoch') AS StartDate , SUM(Timings.Durati
         on) AS Duration FROM Tasks INNER JOIN  Timings ON Tasks._id = Timings.TaskId GROUP BY Tasks._id, StartDate;
        sqlite> SELECT * FROM vwTaskDurations;*/
        String sSQL = "CREATE VIEW " + DurationsContract.TABLE_NAME
                + " AS SELECT " + TimingsContract.TABLE_NAME + "." + TimingsContract.Columns.STRING_ID + ", "
                + TasksContract.TABLE_NAME + "." + TasksContract.Columns.TASKS_NAME + ", "
                + TasksContract.TABLE_NAME + "." + TasksContract.Columns.TASKS_DESCRIPTION + ", "
                + TimingsContract.TABLE_NAME + "." + TimingsContract.Columns.TIMINGS_START_TIME + ","
                + " DATE(" + TimingsContract.TABLE_NAME + "." + TimingsContract.Columns.TIMINGS_START_TIME + ", 'unixepoch')"
                + " AS " + DurationsContract.Columns.DURATIONS_START_DATE + ","
                + " SUM(" + TimingsContract.TABLE_NAME + "." + TimingsContract.Columns.TIMINGS_DURATION + ")"
                + " AS " + DurationsContract.Columns.DURATIONS_DURATION
                + " FROM " + TasksContract.TABLE_NAME + " JOIN " + TimingsContract.TABLE_NAME
                + " ON " + TasksContract.TABLE_NAME + "." + TasksContract.Columns.STRING_ID + " = "
                + TimingsContract.TABLE_NAME + "." + TimingsContract.Columns.TIMINGS_TASK_ID
                + " GROUP BY " + DurationsContract.Columns.DURATIONS_START_DATE + ", " + DurationsContract.Columns.DURATIONS_NAME
                + ";";
        Log.d(TAG, sSQL);
        db.execSQL(sSQL);
    }
}
