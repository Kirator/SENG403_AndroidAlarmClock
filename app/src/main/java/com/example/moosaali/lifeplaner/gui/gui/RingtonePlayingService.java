package com.example.moosaali.lifeplaner.gui.gui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;

import com.example.moosaali.lifeplaner.R;
import com.example.moosaali.lifeplaner.gui.gui.Application.AlarmReceiver;
import com.example.moosaali.lifeplaner.gui.gui.GUI_Front_Page;

import static android.app.PendingIntent.getBroadcast;

/**
 * Created by Moosa Ali on 2017-02-03.
 */

/**
 * This is the service that runs when you set an alarm.
 * It creates a notification with Off and Snooze button.
 * Currently working on way to make this work as this is not
 * as i expected.
 */
public class RingtonePlayingService extends Service{
    private static final int OFF = 1;
    private static final int SNOOZE = 2;

    private int startId;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }



    public int onStartCommand(Intent intent, int flags, int startId)
    {

        int pressed = intent.getIntExtra("ButtonPressed", 0);
        if(pressed == OFF){

        } else if(pressed == SNOOZE){

            System.out.println("SNOOZE IN RINGTONE");
        }


        final NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        int i = 1, j = 2;
        Intent intent1 = new Intent(this.getApplicationContext(), AlarmReceiver.class);
        intent1.putExtra("ButtonPressed", OFF);



        Intent intent2 = new Intent(this.getApplicationContext(), AlarmReceiver.class);
        intent2.putExtra("ButtonPressed", SNOOZE);

        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent1, 0);


        PendingIntent snoozeIntent = getBroadcast(this, 2, intent2,0);

        PendingIntent offIntent = getBroadcast(this, 3, intent1,0);

        Notification notification  = new Notification.Builder(this)
                .setContentTitle("Notification Title" + "!")
                .setContentText("Notification Message")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_stat_name, "Dismiss", offIntent)
                .addAction(R.drawable.ic_stat_name, "Snooze", snoozeIntent)
                .build();




        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sample_ringtone);

        mediaPlayer.start();
        notificationManager.notify(0 ,notification);




        return START_NOT_STICKY;
    }



    @Override
    public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();


    }











}
