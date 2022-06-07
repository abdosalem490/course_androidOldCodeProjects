package Data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Utils.Util;
import model.Contact;

public class DatabaseHandler extends SQLiteOpenHelper {


    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }
    //create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL : Structured Query language
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME +"("+Util.KEY_ID+" INTEGER PRIMARY KEY,"+Util.KEY_NAME+" TEXT,"+Util.KEY_PHONE_NUMBER+" TEXT" +");";
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //dropping is deleting the table
        db.execSQL("DROP TABLE IF EXISTS "+Util.TABLE_NAME);
        //Create TAble again
        onCreate(db);
    }
    //Add contact
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME,contact.getName());
        values.put(Util.KEY_PHONE_NUMBER,contact.getPhoneNumber());
        //Insert to row
        db.insert(Util.TABLE_NAME,null,values);
        db.close();
    }
    //get a Contact
    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME,new String[]{Util.KEY_ID,Util.KEY_NAME,Util.KEY_NAME},Util.KEY_ID +"=?",new String[]{String.valueOf(id)},null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));

        cursor.close();
        return contact;
    }
    //Get All Contacts
    public List<Contact> getAllContacts(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Contact> contacts = new ArrayList<>();
        //select all contacts
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll,null);
        // Loop through our contacts
        if (cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                //add contact object to our contact list
                contacts.add(contact);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return contacts;
    }

    //update contact
    public int update(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME,contact.getName());
        values.put(Util.KEY_PHONE_NUMBER,contact.getPhoneNumber());
        //update row
        return db.update(Util.TABLE_NAME,values,Util.KEY_ID+"=?", new String[]{String.valueOf(contact.getId())});
    }
    //delete single contact
    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME ,Util.KEY_ID + " =?" ,new String[]{String.valueOf(contact.getId())});
        db.close();
    }
    //Get Contacts count
    public int getContactsCount(){
        String countQuery = "SELECT * FROM "+Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase() ;
        Cursor cursor = db.rawQuery(countQuery,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
