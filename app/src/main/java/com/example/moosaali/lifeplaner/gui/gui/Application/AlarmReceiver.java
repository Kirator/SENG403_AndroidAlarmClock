package com.example.moosaali.lifeplaner.gui.gui.Application;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
//import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;
import java.util.Calendar;
import com.example.moosaali.lifeplaner.gui.gui.Data.DataFacade;
import com.example.moosaali.lifeplaner.gui.gui.RingtonePlayingService;

import static android.app.PendingIntent.getBroadcast;

/**
 * Created by Moosa Ali on 2017-02-03.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private int snoozeTime = 1000 * 5; // Place holder snooze time

    @Override
    public void onReceive(Context context, Intent intent) {
        DataFacade dataFacade = new DataFacade(context);
        System.out.println("________________________________________________________________");
        Intent serviceIntent;
        int notificationButtonPressed = intent.getIntExtra("ButtonPressed", -1);
        System.out.println("ButtonPressed " + notificationButtonPressed);
        int alarmId = intent.getIntExtra("ID", -1);
        System.out.println("AlarmID " + alarmId);
        int notificationId = intent.getIntExtra("NotificationID", -1);
        System.out.println("NotificationID " + notificationId);
        System.out.println("________________________________________________________________");

        System.out.println(notificationButtonPressed);
        if(intent == null){
            System.out.println("NULL Intent");

        }

        //If button presson on notification
        if(notificationButtonPressed != -1 & notificationId != -1){
            MediaPlayer mediaPlayer = RingtonePlayingService.getMediaPlayer();
            if(notificationButtonPressed == RingtonePlayingService.OFF){ // Dismiss Notification Button Pressed
                mediaPlayer.stop(); // stop the music service

            }else if(notificationButtonPressed == RingtonePlayingService.SNOOZE){  // Snooze button pressed, stop music service and set a new alarm


                Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                alarmIntent.putExtra("ID", alarmId);
                System.out.println("Sent Alarm ID: " + alarmId);
                alarmIntent.putExtra("NotificationID", notificationId);
                PendingIntent pIntent = getBroadcast(context, alarmId + 1000, alarmIntent ,PendingIntent.FLAG_ONE_SHOT);


                AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + snoozeTime, pIntent);
                mediaPlayer.stop();

            }

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(notificationId);

        }else{
            Log.d("Alarm Receiver", "Play music");
            //If alarm is on play music
            if(alarmId != -1 && dataFacade.getAlarm(alarmId).isON()){
                System.out.println("ID not -1, ID: " + alarmId);


                serviceIntent = new Intent(context, RingtonePlayingService.class);
                serviceIntent.putExtra("ID", alarmId);
                context.startService(serviceIntent);


            }else if(alarmId == -1){
                System.out.println(" Alarm Receiver, NONE.");
                serviceIntent = new Intent(context, RingtonePlayingService.class);
                context.startService(serviceIntent);
            }

        }






    }
}
