package com.Mo_Zarara.MyNote.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Mo_Zarara.MyNote.Models.Note;
import com.Mo_Zarara.MyNote.NewsRepository;

import java.util.List;

public class AddNoteViewModel extends AndroidViewModel {

    private static final String TAG = "AddNoteViewModel";
    private NewsRepository mRepository;

    public AddNoteViewModel(@NonNull Application application) {
        super(application);

        mRepository = new NewsRepository(application);
    }


    public void insert(Note newsModel){
        mRepository.insert(newsModel);
    }

    public void update(Note newsModel){
        mRepository.update(newsModel);
    }


}
