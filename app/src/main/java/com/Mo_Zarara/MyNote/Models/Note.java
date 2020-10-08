package com.Mo_Zarara.MyNote.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "noteTable")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date, title, description;

    public Note(String date, String title, String description) {
        this.date = date;
        this.title = title;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
