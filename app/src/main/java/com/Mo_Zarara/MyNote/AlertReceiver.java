package com.Mo_Zarara.MyNote;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.Mo_Zarara.MyNote.Ui.AddNoteActivity;
import com.Mo_Zarara.MyNote.Ui.MainActivity;

public class AlertReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        String title_Notification = intent.getStringExtra(AddNoteActivity.TITLE_NOTIFICATION_KEY);
        String des_Notification = intent.getStringExtra(AddNoteActivity.DESCRIPTION_NOTIFICATION_KEY);
        String date_Notification = intent.getStringExtra(AddNoteActivity.DATE_NOTIFICATION_KEY);
        /*String id_Notification = intent.getStringExtra(AddNoteActivity.ID_NOTIFICATION_KEY);
        int mID = Integer.parseInt(id_Notification);*/

        //The page that will go after the click notification
        PendingIntent notification = PendingIntent.getActivity(context,
                AddNoteActivity.mID,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_save_btn)
                .setContentTitle(title_Notification)
                .setContentText(des_Notification);

        builder.setContentIntent(notification);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setAutoCancel(true);

        NotificationManager nM = (NotificationManager) context
                .getSystemService(context.NOTIFICATION_SERVICE);
        nM.cancel(1);
        nM.notify(1, builder.build());


    }

}
