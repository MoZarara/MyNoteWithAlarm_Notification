package com.Mo_Zarara.MyNote;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.Mo_Zarara.MyNote.Models.Note;

@Database(entities = Note.class, version = 1)
public abstract class NewsRoomDB extends RoomDatabase {

    private static NewsRoomDB instance;

    public abstract NewsDao newsDao();

    //Singlton
    public static synchronized NewsRoomDB getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NewsRoomDB.class,
                    "News_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulatDataAsyncTask(instance).execute();

        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }

    };


    private static class PopulatDataAsyncTask extends AsyncTask<Void, Void, Void> {

        private NewsDao mNewsDao;

        public PopulatDataAsyncTask(NewsRoomDB newsRoomDB) {
            mNewsDao = newsRoomDB.newsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

           // mNewsDao.insert( new Note("18/6/2020", "النوت الاولي", "اعمل الاشعارات"));

            return null;
        }
    }


}
