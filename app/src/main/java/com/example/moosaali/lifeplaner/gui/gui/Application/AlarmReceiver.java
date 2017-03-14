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

/**
 * Created by Moosa Ali on 2017-02-03.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private int snoozeTime = 1000 * 2; // Place holder snooze time

    @Override
    public void onReceive(Context context, Intent intent) {
        DataFacade dataFacade = new DataFacade(context);

        Intent serviceIntent;
        int status = intent.getIntExtra("ButtonPressed", 0);
        int id = intent.getIntExtra("ID", -1);


        System.out.println(status);
        if(intent == null){
            System.out.println("NULL Intent");

        }
        if(status != 0){
            MediaPlayer mediaPlayer = RingtonePlayingService.getMediaPlayer();
            if(status == 1){ // Dismiss Notification Button Pressed
                mediaPlayer.stop(); // stop the music service

            }else{  // Snooze button pressed, stop music service and set a new alarm

                //serviceIntent = new Intent(context, RingtonePlayingService.class);
                //serviceIntent.putExtra("ButtonPressed", 2);
                Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                alarmIntent.putExtra("ID", id);
                AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + snoozeTime, PendingIntent.getBroadcast(context, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT));
                mediaPlayer.stop();

            }

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(RingtonePlayingService.NOTIFICATION_ID);

        }else{
            Log.d("Alarm Receiver", "Play music");
            //If alarm is on play music
            if(id != -1 && dataFacade.getAlarm(id).isON()){
                Calendar calendar = Calendar.getInstance();
                System.out.println(" Alarm Receiver, NONE.");
                Alarm alarm = dataFacade.getAlarm(id);
                //Check to see if alarm has not been changed.
                if(alarm.getYear() == (int)calendar.get(Calendar.YEAR)
                        && alarm.getDay() == (int)calendar.get(Calendar.DAY_OF_MONTH)
                        && alarm.getHour() == (int)calendar.get(Calendar.HOUR_OF_DAY)
                        && alarm.getMinute() == (int)calendar.get(Calendar.MINUTE)){
                    serviceIntent = new Intent(context, RingtonePlayingService.class);
                    context.startService(serviceIntent);
                }

            }else if(id == -1){
                System.out.println(" Alarm Receiver, NONE.");
                serviceIntent = new Intent(context, RingtonePlayingService.class);
                context.startService(serviceIntent);
            }

        }






    }
}
