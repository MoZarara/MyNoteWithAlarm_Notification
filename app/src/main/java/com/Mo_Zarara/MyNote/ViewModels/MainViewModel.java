package com.Mo_Zarara.MyNote.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Mo_Zarara.MyNote.Models.Note;
import com.Mo_Zarara.MyNote.NewsRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";
    private NewsRepository mRepository;

    private LiveData<List<Note>> mAllNews;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = new NewsRepository(application);
        mAllNews = mRepository.getAllNews();
    }



    public void insert(Note newsModel){
        mRepository.insert(newsModel);
    }

    public void delete(Note newsModel){
        mRepository.delete(newsModel);
    }

    public void update(Note newsModel){
        mRepository.update(newsModel);
    }

    public void deleteAllNews(){
        mRepository.deleteAllNews();
    }

    public LiveData<List<Note>> getAllNews(){
        return  mAllNews;
    }






}
