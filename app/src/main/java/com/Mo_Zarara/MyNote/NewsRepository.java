package com.Mo_Zarara.MyNote;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.Mo_Zarara.MyNote.Models.Note;

import java.util.List;

public class NewsRepository {

    private NewsDao mNewsDao;

    private LiveData<List<Note>> getAllNews;

    public NewsRepository(Application app) {
        NewsRoomDB db = NewsRoomDB.getInstance(app);
        mNewsDao = db.newsDao();
        getAllNews = mNewsDao.getAllNews();
    }

    public void insert(Note newsModel){
        new InsertAsyncTask(mNewsDao).execute(newsModel);
    }

    public void delete(Note newsModel){
        new DeleteAsyncTask(mNewsDao).execute(newsModel);
    }

    public void update(Note newsModel){
        new UpdateAsyncTask(mNewsDao).execute(newsModel);
    }

    public LiveData<List<Note>> getAllNews(){
        return getAllNews;
    }

    public void deleteAllNews(){
        new DeleteAllNewsAsyncTask(mNewsDao).execute();
    }


    private static class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

        private NewsDao mNewsDao;

        public InsertAsyncTask(NewsDao mNewsDao) {
            this.mNewsDao = mNewsDao;
        }

        @Override
        protected Void doInBackground(Note... newsModels) {

            mNewsDao.insert(newsModels[0]);
            return null;
        }
    }


    private static class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NewsDao mNewsDao;

        public DeleteAsyncTask(NewsDao mNewsDao) {
            this.mNewsDao = mNewsDao;
        }

        @Override
        protected Void doInBackground(Note... newsModels) {

            mNewsDao.delete(newsModels[0]);
            return null;
        }
    }


    private static class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {

        private NewsDao mNewsDao;

        public UpdateAsyncTask(NewsDao mNewsDao) {
            this.mNewsDao = mNewsDao;
        }

        @Override
        protected Void doInBackground(Note... newsModels) {

            mNewsDao.update(newsModels[0]);
            return null;
        }
    }

    private static class DeleteAllNewsAsyncTask extends AsyncTask<Void, Void, Void> {

        private NewsDao mNewsDao;

        public DeleteAllNewsAsyncTask(NewsDao mNewsDao) {
            this.mNewsDao = mNewsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mNewsDao.deleteAll();
            return null;
        }
    }

}
