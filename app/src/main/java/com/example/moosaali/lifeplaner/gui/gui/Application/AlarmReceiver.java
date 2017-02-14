package com.example.moosaali.lifeplaner.gui.gui.Application;

import android.app.AlarmManager;
import android.app.NotificationManager;
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



    @Override
    public void onReceive(Context context, Intent intent) {


        Intent serviceIntent;
        int status = intent.getIntExtra("ButtonPressed", 0);

        System.out.println(status);
        if(intent == null){
            System.out.println("NULL Intent");

        }
        if(status != 0){
            if(status == 1){
                System.out.println(" Alarm Receiver, OFF.");
                MediaPlayer mediaPlayer = RingtonePlayingService.getMediaPlayer();
                mediaPlayer.stop();
                NotificationManager notificationManager = (NotificationManager)
                        context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(RingtonePlayingService.NOTIFICATION_ID);

            }else{
                System.out.println(" Alarm Receiver, SNOOZE.");
                serviceIntent = new Intent(context, RingtonePlayingService.class);
                serviceIntent.putExtra("ButtonPressed", 2);
            }
        }else{
            System.out.println(" Alarm Receiver, NONE.");
            serviceIntent = new Intent(context, RingtonePlayingService.class);
            serviceIntent.putExtra("ButtonPressed", 0);
            context.startService(serviceIntent);
        }





    }
}
