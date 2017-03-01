package com.example.moosaali.lifeplaner.gui.gui.Application;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import com.example.moosaali.lifeplaner.gui.gui.RingtonePlayingService;

/**
 * Created by Moosa Ali on 2017-02-03.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private int snoozeTime = 1000 * 60; // Place holder snooze time

    @Override
    public void onReceive(Context context, Intent intent) {


        Intent serviceIntent;
        int status = intent.getIntExtra("ButtonPressed", 0);

        System.out.println(status);
        if(intent == null){
            System.out.println("NULL Intent");

        }
        if(status != 0){
            MediaPlayer mediaPlayer = RingtonePlayingService.getMediaPlayer();
            if(status == 1){ // Dismiss Notification Button Pressed
                mediaPlayer.stop(); // stop the music service

            }else{  // Snooze button pressed, stop music service and set a new alarm

                serviceIntent = new Intent(context, RingtonePlayingService.class);
                serviceIntent.putExtra("ButtonPressed", 2);
                Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + snoozeTime, PendingIntent.getBroadcast(context, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT));
                mediaPlayer.stop();

            }

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(RingtonePlayingService.NOTIFICATION_ID);

        }else{
            System.out.println(" Alarm Receiver, NONE.");
            serviceIntent = new Intent(context, RingtonePlayingService.class);
            serviceIntent.putExtra("ButtonPressed", 0);
            context.startService(serviceIntent);
        }






    }
}
