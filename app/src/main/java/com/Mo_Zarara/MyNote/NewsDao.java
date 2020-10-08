package com.Mo_Zarara.MyNote;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.Mo_Zarara.MyNote.Models.Note;

import java.util.List;


@Dao
public interface NewsDao {

    @Insert
    void insert(Note newsModel);

    @Update
    void update(Note newsModel);

    @Delete
    void delete(Note newsModel);

    @Query("DELETE From noteTable")
    void deleteAll();

    @Query("SELECT * From noteTable")
    LiveData<List<Note>> getAllNews();

}
