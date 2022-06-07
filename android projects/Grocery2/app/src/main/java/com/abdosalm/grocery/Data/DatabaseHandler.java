package com.abdosalm.grocery.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.abdosalm.grocery.Model.Grocery;
import com.abdosalm.grocery.Util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private Context context;
    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CURSORY_TABLE = "CREATE TABLE "+ Constants.TABLE_NAME+"("+Constants.KEY_ID+" INTEGER PRIMARY KEY,"+Constants.KEY_GROCERY_ITEM+" TEXT,"+Constants.KEY_QTY_NUMBER+" TEXT,"+Constants.KEY_DATE_NAME+" LONG);";
        db.execSQL(CREATE_CURSORY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.TABLE_NAME);
        onCreate(db);
    }
    //Add grocery
    public void addGrocery(Grocery grocery){
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues values = new ContentValues();
       values.put(Constants.KEY_GROCERY_ITEM,grocery.getName());
       values.put(Constants.KEY_QTY_NUMBER,grocery.getQuantity());
       values.put(Constants.KEY_DATE_NAME,java.lang.System.currentTimeMillis());
       //insert the row
        db.insert(Constants.TABLE_NAME,null,values);
    }

    //Get a Grocery
    public Grocery getGrocery(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME,new String[]{Constants.KEY_ID,Constants.KEY_GROCERY_ITEM,Constants.KEY_QTY_NUMBER,Constants.KEY_DATE_NAME},Constants.KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
            Grocery grocery = new Grocery();
            grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
            grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
            grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));
            //convert timestamp to something readable
            java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
            String formateDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
            grocery.setDateItemAdded(formateDate);
            cursor.close();
            return grocery;
        }
        return null;
    }

    //Get all Groceries
    public List<Grocery> getAllGroceries(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Grocery> groceryList = new ArrayList<>();
        Cursor cursor = db.query(Constants.TABLE_NAME,new String[]{Constants.KEY_ID,Constants.KEY_GROCERY_ITEM,Constants.KEY_QTY_NUMBER,Constants.KEY_DATE_NAME},null,null,null,null,Constants.KEY_DATE_NAME + " DESC");
        if (cursor.moveToFirst()){
            do {
                Grocery grocery = new Grocery();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
                grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));
                //Convert timestamp to something readable
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formateDAte = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
                grocery.setDateItemAdded(formateDAte);
                //Add to groceryList
                groceryList.add(grocery);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return groceryList;
    }

    //Update Grocery
    public int updateGrocery(Grocery grocery){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM,grocery.getName());
        values.put(Constants.KEY_QTY_NUMBER,grocery.getQuantity());
        values.put(Constants.KEY_DATE_NAME,java.lang.System.currentTimeMillis());
        //update row
        return db.update(Constants.TABLE_NAME,values,Constants.KEY_ID+"=?",new String[]{String.valueOf(grocery.getId())});
    }

    //Delete Grocery
    public void deleteGrocery(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME,Constants.KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }
    //Get Count
    public int getGroceriesCount(){
        String countQuery = "SELECT * FROM "+Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

}
