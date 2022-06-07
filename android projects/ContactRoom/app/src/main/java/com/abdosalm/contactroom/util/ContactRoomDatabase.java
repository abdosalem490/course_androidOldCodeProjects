package com.abdosalm.contactroom.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.abdosalm.contactroom.data.ContactDao;
import com.abdosalm.contactroom.model.Contact;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Contact.class} , version = 1 , exportSchema = false)
public abstract class ContactRoomDatabase extends RoomDatabase
{
    public abstract ContactDao contactDao();

    public static final int NUMBER_OF_THREADS =4;

    private static volatile ContactRoomDatabase INSTANCE;

    public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ContactRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (ContactRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext() , ContactRoomDatabase.class , "contact_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull  SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriterExecutor.execute(()-> {
                ContactDao contactDao = INSTANCE.contactDao();
                contactDao.deleteAll();

                Contact contact = new Contact("abdo" , "0112278504");
                contactDao.insert(contact);

                contact = new Contact("basma" , "0113355446");
                contactDao.insert(contact);

            });
        }
    };
}
