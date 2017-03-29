package com.example.moosaali.lifeplaner.gui.gui.Application;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.moosaali.lifeplaner.gui.gui.Data.DataFacade;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Moosa Ali on 2017-02-02.
 */

/**
 * This is the interface for the Application Layer. everything from Activities is accessed through
 * here.
 */
public class ApplicationFacade {
    private Context context;
    private DataFacade dataFacade;
    public ApplicationFacade(Context context){
        this.context = context;
        dataFacade = new DataFacade(context);
    }

    public void SetTimeZone(String newState){
        dataFacade.SetTimeZone(newState);
    }

    public String getTimeZone(){
        return dataFacade.getTimeZone();
    }

    public void addAlarm(int year,int month,int day,int hour,int minute, String type, String message, int id){
        Alarm alarmToAdd = new Alarm(year, month, day, hour, minute, type, message, id);
        dataFacade.addAlarm(alarmToAdd);
    }

    public ArrayList<Alarm> getAllAlarms(){ return dataFacade.getAlarms();}

    public void removeAlarm(Alarm alarmtoRemove){ dataFacade.deleteAlarm(alarmtoRemove.getID());}

    public void toggleAlarm(Alarm alarmToToggle){dataFacade.toggle(alarmToToggle.getID());}

    public int getNextAlarmId() {return dataFacade.getMaxID() + 1;}

    public Alarm getAlarm(int id) {return dataFacade.getAlarm(id);}

    public void editAlarm(int year,int month,int day,int hour,int minute,int id ){
        dataFacade.editAlarm(id, year, month, day, hour, minute);
    }

    public void changeAlarmMessage(Alarm a, String s) {dataFacade.changeMessage(a.getID(), s);}

    public void toggleDailyRepeatable(Alarm alarmToToggle)
    {
        // If alarm is not currently repeatable, we're toggle to make it repeatable
        if (!dataFacade.isDailyRepeatable(alarmToToggle.getID()))
        {
            // Set repeatable
            toggleAlarmManager(alarmToToggle, true, "DAILY");
        }
        else
        {
            // Disable repeatable
            toggleAlarmManager(alarmToToggle, false, "DAILY");
        }

        // Update store
        dataFacade.toggleDailyRepeatable(alarmToToggle.getID());
    }

    public void toggleWeeklyRepeatable(Alarm alarmToToggle)
    {
        // If alarm is not currently repeatable, we're toggling to make it repeatable
        if (!dataFacade.isWeeklyRepeatable(alarmToToggle.getID()))
        {
            // Set repeatable
            toggleAlarmManager(alarmToToggle, true, "WEEKLY");
        }
        else
        {
            // Disable repeatable
            toggleAlarmManager(alarmToToggle, false, "WEEKLY");
        }

        // Update store
        dataFacade.toggleWeeklyRepeatable(alarmToToggle.getID());
    }

    public void toggleAlarmManager(Alarm alarmToToggle, boolean repeat, String interval)
    {
        // Toggle old alarm manager
        int id = alarmToToggle.getID();
        Long alertTime = alarmToToggle.getAlarmTime();
        Intent alarmIntent = new Intent(this.context, AlarmReceiver.class);
        alarmIntent.putExtra("ID", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)this.context.getSystemService(Context.ALARM_SERVICE);
        alertTime = alertTime - (alertTime % 1000);
        alarmManager.cancel(pendingIntent);
        // Decide weather to repeat alarm or not
        if (!repeat)
        {
            // Log.d("toggleAlarmManager: ", "Disable repeating alarm, Milliseconds: "+alertTime);
            timeToHumanReadable(alertTime);
            // When turning off daily or weekly repeat alarms, check if other one is set
            if (interval == "DAILY" && dataFacade.isWeeklyRepeatable(alarmToToggle.getID()))
            {
                Log.d("toggleAlarmManager: ", "DAILY: OFF WEEKLY: ON - Milliseconds: "+alertTime);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alertTime, AlarmManager.INTERVAL_DAY * 7, pendingIntent);
            }
            else if (interval == "WEEKLY" && dataFacade.isDailyRepeatable(alarmToToggle.getID()))
            {
                Log.d("toggleAlarmManager: ", "DAILY: ON WEEKLY: OFF - Milliseconds: "+alertTime);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alertTime, AlarmManager.INTERVAL_DAY, pendingIntent);
            }
            else
            {
                Log.d("toggleAlarmManager: ", "Disable repeating alarm, Milliseconds: "+alertTime);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alertTime, pendingIntent);
            }
        }
        else
        {
            Log.d("toggleAlarmManager: ", "Repeat - "+interval+" Milliseconds: "+alertTime);
            timeToHumanReadable(alertTime);
            if (interval == "DAILY")
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alertTime, AlarmManager.INTERVAL_DAY, pendingIntent);
            if (interval == "WEEKLY")
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alertTime, AlarmManager.INTERVAL_DAY * 7, pendingIntent);

        }

    }

    private void timeToHumanReadable(Long milliseconds)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");
        Date date = new Date(milliseconds);
        Log.d(milliseconds + " ->  ", sdf.format(date));
    }
}
