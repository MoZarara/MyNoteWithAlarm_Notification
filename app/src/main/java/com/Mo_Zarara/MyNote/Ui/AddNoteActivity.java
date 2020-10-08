package com.Mo_Zarara.MyNote.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.Mo_Zarara.MyNote.AlertReceiver;
import com.Mo_Zarara.MyNote.ViewModels.AddNoteViewModel;
import com.Mo_Zarara.MyNote.Models.Note;
import com.Mo_Zarara.MyNote.R;

import java.util.Calendar;
import java.util.Random;

public class AddNoteActivity extends AppCompatActivity {

    private static final String TAG = "AddNoteActivity";
    
    Button date_btn;
    EditText title, descriptiom;

    AddNoteViewModel mViewModel;

    public static final String EXTRA_ID = "com.Mo_Zarara.MyNote.exraid";

    public static final String EXTRA_DATE = "com.Mo_Zarara.MyNote.date";
    public static final String EXTRA_TITLE = "com.Mo_Zarara.MyNote.title";
    public static final String EXTRA_DESCRIPTION = "com.Mo_Zarara.MyNote.description";
    Intent i;

    private boolean editMode;
    public static int mID;


    //public static final int REQUEST_INTENT = 100;
    private static final int dialogID = 1;


    public static final String TITLE_NOTIFICATION_KEY = "title_notification_key";
    public static final String DESCRIPTION_NOTIFICATION_KEY = "description_notification_key";
    public static final String DATE_NOTIFICATION_KEY = "date_notification_key";
    //public static final String ID_NOTIFICATION_KEY = "ID_notification_key";

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_exit_btn);

        intent = getIntent();


        date_btn = findViewById(R.id.date_entry_btn_id);
        title = findViewById(R.id.title_entry_id);
        descriptiom = findViewById(R.id.description_entry_id);


        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(dialogID);
            }
        });

        mViewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);

        i = getIntent();
        if (i.hasExtra(EXTRA_ID)) {
            //UPDATE STATE
            setTitle("Edit Note");
            editMode = true;
            mID = i.getIntExtra(EXTRA_ID, -1);

            date_btn.setText(i.getStringExtra(EXTRA_DATE));
            title.setText(i.getStringExtra(EXTRA_TITLE));
            descriptiom.setText(i.getStringExtra(EXTRA_DESCRIPTION));

        } else if (intent.hasExtra(MainActivity.ID_KEY)){
            //NEW NOTE STATE
            setTitle("Add New Note");
            editMode = false;
            mID = intent.getIntExtra(MainActivity.ID_KEY,-1);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_btn:
                SaveNote();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public String date_Ste;
    public String title_Ste;
    public String descriptiom_Ste;

    private void SaveNote() {
        date_Ste = date_btn.getText().toString().trim();
        title_Ste = title.getText().toString().trim();
        descriptiom_Ste = descriptiom.getText().toString().trim();

        Note noteObject = new Note(date_Ste, title_Ste, descriptiom_Ste);

        if (date_Ste.isEmpty() || title_Ste.isEmpty() || descriptiom_Ste.isEmpty()) {
            Toast.makeText(this, "Please Fill All Fields...", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editMode) {
            noteObject.setId(mID);
            mViewModel.update(noteObject);
        } else {
            mViewModel.insert(noteObject);
        }

        SaveAlarm();

        finish();
    }





    ///////////ALARM + NOTIFICATION
    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendar = Calendar.getInstance();
        if (id == dialogID) {
            return new TimePickerDialog(AddNoteActivity.this, TimeMap,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), false);
        }
        return null;
    }


    Calendar calendar;
    protected TimePickerDialog.OnTimeSetListener TimeMap =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker TimeP, int hourOfDay, int minute) {

                    calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    /*Intent alertIntent = new Intent(AddNoteActivity.this, AlertReceiver.class);
                    alertIntent.putExtra(TITLE_NOTIFICATION_KEY, title_Ste + "my title");
                    alertIntent.putExtra(DESCRIPTION_NOTIFICATION_KEY, descriptiom_Ste + "my desc");

                    AlarmManager alarmManager = (AlarmManager) getSystemService( ALARM_SERVICE );

                    alarmManager.set(AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(),
                            PendingIntent.getBroadcast(AddNoteActivity.this,
                                    REQUEST_INTENT,
                                    alertIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT ));*/

                    date_btn.setText(hourOfDay + ":" + minute);

                }
            };



    public void SaveAlarm() {
        Intent alertIntent = new Intent(AddNoteActivity.this, AlertReceiver.class);
        alertIntent.putExtra(TITLE_NOTIFICATION_KEY, title_Ste);
        alertIntent.putExtra(DESCRIPTION_NOTIFICATION_KEY, descriptiom_Ste);
        alertIntent.putExtra(DATE_NOTIFICATION_KEY, date_Ste);
        //alertIntent.putExtra(ID_NOTIFICATION_KEY, mID);

        AlarmManager alarmManager = (AlarmManager) getSystemService( ALARM_SERVICE );

        /*Random r = new Random();
        int ID = r.nextInt();*/
        Log.d(TAG, "mySaveAlarm: " + mID);
        //ONCE
        if (calendar != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    PendingIntent.getBroadcast(AddNoteActivity.this,
                            mID,
                            //ID,
                            alertIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT));
        }
        //REPEATING
        /*alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                PendingIntent.getBroadcast(getApplicationContext(), REQUEST_INTENT,
                        alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));*/

    }



}
