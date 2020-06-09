package com.example.roomwordssample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase
{
    public abstract WordDao wordDao();

    private static WordRoomDatabase sInstance;
    public static WordRoomDatabase getDatabase(final Context context)
    {
        if (sInstance == null)
        {
            synchronized (WordRoomDatabase.class)
            {
                if (sInstance == null)
                {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database").fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return sInstance;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db)
        {
            super.onOpen(db);
            new MainActivity.PopulateDbAsync(sInstance).execute();
        }
    };

}
