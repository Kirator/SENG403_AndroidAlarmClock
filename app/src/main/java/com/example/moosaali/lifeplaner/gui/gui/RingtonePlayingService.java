package com.example.moosaali.lifeplaner.gui.gui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.moosaali.lifeplaner.R;
import com.example.moosaali.lifeplaner.gui.gui.Application.Alarm;
import com.example.moosaali.lifeplaner.gui.gui.Application.AlarmReceiver;
import com.example.moosaali.lifeplaner.gui.gui.Data.DataFacade;

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
    public static final int OFF = 1;
    public static final int SNOOZE = 2;
    public static boolean Playing;
    private static int startId = 0;
    public static MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {

        int id = intent.getIntExtra("ID", -1);

        System.out.println("Playing music for Alarm: " + id);


        DataFacade dataFacade = new DataFacade(this.getApplicationContext());
        makeMediaPlayer(dataFacade.getAlarm(id).getRingTone());
        startId ++;

        final NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);


        Intent intent1 = new Intent(this.getApplicationContext(), AlarmReceiver.class);
        intent1.putExtra("ID", id);
        intent1.putExtra("ButtonPressed", OFF);
        intent1.putExtra("NotificationID", id);


        Intent intent2 = new Intent(this.getApplicationContext(), AlarmReceiver.class);
        intent2.putExtra("ID", id);
        intent2.putExtra("ButtonPressed", SNOOZE);
        intent2.putExtra("NotificationID", id);



        PendingIntent pIntent = PendingIntent.getActivity(this, 100 +id, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent snoozeIntent = getBroadcast(this, 200 + id , intent2,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent offIntent = getBroadcast(this, 300  + id, intent1,PendingIntent.FLAG_UPDATE_CURRENT);

        // Get current alarm intent & object from store


        Alarm alarm = dataFacade.getAlarm(id);

        Notification notification  = new Notification.Builder(this)
                .setContentTitle("Alarm " + id + "!")
                .setContentText(alarm.getMessage()) // Message
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_stat_name, "Dismiss", offIntent)
                .addAction(R.drawable.ic_stat_name, "Snooze", snoozeIntent)
                .build();


        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        notificationManager.notify(id ,notification);






        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();
    }

    private synchronized void makeMediaPlayer(String ringtone){
        //Stop Music if already playing before playing new music
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }

        //Play correct music
        if(startId  == 0){
            // OK so the proper way of doing this is samewhat complicated and i dont have time right now to di it properly.
            int id = R.raw.office;
            if(ringtone.equals("alarm1")){
                id = R.raw.alarm1;
            }else if(ringtone.equals("bells")){
                id = R.raw.bells;
            }else if(ringtone.equals("good_morning")){
                id = R.raw.good_morning;
            }else if(ringtone.equals("office")){
                id = R.raw.office;
            }else if(ringtone.equals("sample_ringtone")){
                id = R.raw.sample_ringtone;
            }


            // Play ringtone found from the database
            mediaPlayer = MediaPlayer.create(this, id);

        }
    }

    public synchronized static MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }

}

