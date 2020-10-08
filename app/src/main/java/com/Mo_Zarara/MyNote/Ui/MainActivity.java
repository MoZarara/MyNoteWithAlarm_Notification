package com.Mo_Zarara.MyNote.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.Mo_Zarara.MyNote.Adapter.RecyclerAdapter;
import com.Mo_Zarara.MyNote.Models.Note;
import com.Mo_Zarara.MyNote.ViewModels.MainViewModel;
import com.Mo_Zarara.MyNote.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    RecyclerView mRecyclerView;
    RecyclerAdapter adapter;

    MainViewModel mViewModel;

    FloatingActionButton floatingActionButton;

    List<Note> list;

    public static final String ID_KEY = "KEY_ID_COUNT";
    int ID_COUNT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        floatingActionButton = findViewById(R.id.floating_btn_id);




        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        adapter = new RecyclerAdapter();
        mRecyclerView.setAdapter(adapter);



        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.getAllNews().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> newsModels) {
                adapter.setList(newsModels, getApplicationContext());

                ID_COUNT = newsModels.size();
                Log.d(TAG, "mymyonClick: " + newsModels.size());
                /*for (int i = 0; i < newsModels.size(); i++) {
                    Note newsModel = new Note(newsModels.get(i).getAuthor(),
                            newsModels.get(i).getTitle(),
                            newsModels.get(i).getDescription(),
                            newsModels.get(i).getUrl(),
                            newsModels.get(i).getUrlToImage(),
                            newsModels.get(i).getPublishedAt());


                    mViewModel.insert(newsModel);
                }*/



            }
        });



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*List<Note> list = new ArrayList<>();

                for (int i = 0; list.size() >= i ; i++) {
                    Log.d(TAG, "mymyonClick: " + i);
                }*/
                /*int i = list.size() + 1;
                Log.d(TAG, "mymyonClick: " + i);*/
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                intent.putExtra(ID_KEY, ID_COUNT);
                startActivity(intent);
                //startActivityForResult(new Intent(MainActivity.this, AddNoteActivity.class), 1);
                //Toast.makeText(MainActivity.this, "kkk", Toast.LENGTH_SHORT).show();
            }
        });


        adapter.OnItemClickListnerFunc(new RecyclerAdapter.OnItemClickListenerInterface() {
            @Override
            public void OnItemClick(Note note) {
                Intent i = new Intent(MainActivity.this, AddNoteActivity.class);
                i.putExtra(AddNoteActivity.EXTRA_ID, note.getId());

                i.putExtra(AddNoteActivity.EXTRA_DATE, note.getDate());
                i.putExtra(AddNoteActivity.EXTRA_TITLE, note.getTitle());
                i.putExtra(AddNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                startActivity(i);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
                //Moving the element from place to another
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //Swip for delete item from list
                int position =viewHolder.getAdapterPosition();
                mViewModel.delete(adapter.getNoteAt(position));
            }
        }).attachToRecyclerView(mRecyclerView);



        //adapter = new RecyclerAdapter(list, this);





        /*mViewModel.liveData.observe(this, new Observer<Root>() {
            @Override
            public void onChanged(Root root) {
                if (root != null) {
                    list = root.getArticles();

                    for (int i = 0; i < list.size(); i++) {
                        adapter.setList(list, getApplicationContext());

                        //adapter = new RecyclerAdapter(list, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        //adapter.notifyDataSetChanged();
                    }

                }


            }
        });*/


    }

    /*public void openNews(Context context, int posation) {

        //Uri newsUri = Uri.parse(posation);
        //adapter.getItemId((int) posation);
        Toast.makeText(context, "mainactivity" + posation, Toast.LENGTH_SHORT).show();
        *//*Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
        startActivity(websiteIntent);*//*
    }*/


}
