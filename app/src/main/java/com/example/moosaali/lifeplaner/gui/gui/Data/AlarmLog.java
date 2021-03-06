package com.example.moosaali.lifeplaner.gui.gui.Data;

import android.content.Context;
import android.content.Intent;

import com.example.moosaali.lifeplaner.gui.gui.Application.Alarm;
import com.example.moosaali.lifeplaner.gui.gui.Data.TinyDB;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Sebastian Crites on 2017-02-28.
 *
 * This is the database which stores the existing alarms created by the user
 */

public class AlarmLog
{
    private TinyDB tdb;
    private static final String key = "alarms";

    //Pass Android context to constructor
    public AlarmLog(Context context)
    {
        tdb = new TinyDB(context);
    }

    //Returns all alarms in the database in an ArrayList<Alarms>
    public ArrayList<Alarm> getAllAlarms()
    {
        ArrayList<Object> alarmObjects = tdb.getListObject(key,Alarm.class);

        ArrayList<Alarm> alarms = new ArrayList<Alarm>();
        for (Object next : alarmObjects)
        {
            alarms.add((Alarm)next);
        }
        return alarms;
    }

    //Add an alarm to the log
    public void addAlarm(Alarm alarmToAdd)
    {
        ArrayList<Object> alarms = getAlarmObjects();
        alarms.add(alarmToAdd);
        tdb.remove(key);
        tdb.putListObject(key,alarms);
    }

    //Get the Alarm with the specified ID from the database
    public Alarm getAlarm(int id)
    {
        ArrayList<Alarm> alarms = getAllAlarms();

        int i = 0;
        Alarm next = alarms.get(i);
        while (next.getID() != id)
        {
            i++;
            if(i < alarms.size()){
                next = (Alarm) alarms.get(i);
            }

        }

        return next;
    }

    //Delete an Alarm from the database
    public void deleteAlarm(int id)
    {
        ArrayList<Object> alarms = getAlarmObjects();

        int indexToDelete = getIndexOfID(id);
        alarms.remove(indexToDelete);

        tdb.remove(key);
        tdb.putListObject(key,alarms);
    }

    //Turn an Alarm on or off and update the log
    public void toggle(int id)
    {
        ArrayList<Object> alarms = getAlarmObjects();

        Alarm toToggle = getAlarm(id);
        toToggle.toggle();

        alarms.set(getIndexOfID(id),(Object)toToggle);

        tdb.remove(key);
        tdb.putListObject(key,alarms);
    }

    //Helper method to get index of Alarm with specified ID
    private int getIndexOfID(int id)
    {
        ArrayList<Alarm> alarms = getAllAlarms();

        int i = 0;
        Alarm next = alarms.get(i);
        while (next.getID() != id)
        {
            i++;
            next = (Alarm) alarms.get(i);
        }

        return i;
    }

    //Get the maximum id from the database.
    public int getMaxId()
    {
        ArrayList<Alarm> alarms = getAllAlarms();
        int maxId = -1;
        if(alarms.size() > 0){
           maxId = alarms.get(0).getID();
            for(int i = 0 ; i < alarms.size(); i ++){
                maxId = (alarms.get(i).getID() > maxId ? alarms.get(i).getID() : maxId);
            }
        }
        return maxId;
    }

    public void editAlarm(int id, int year, int month, int day, int hour, int minute){
        ArrayList<Object> alarms = getAlarmObjects();
        Alarm alarmToEdit = (Alarm)alarms.get(getIndexOfID(id));
        alarmToEdit.setYear(year);
        alarmToEdit.setMonth(month);
        alarmToEdit.setDay(day);
        alarmToEdit.setHour(hour);
        alarmToEdit.setMinute(minute);
        tdb.remove(key);
        tdb.putListObject(key, alarms);
    }



    public void changeMessage(int id, String s)
    {
        ArrayList<Object> alarms = getAlarmObjects();
        Alarm a = getAlarm(id);
        a.setMessage(s);

        alarms.set(getIndexOfID(id),(Object)a);

        tdb.remove(key);
        tdb.putListObject(key, alarms);
    }

    public void toggleDailyRepeatable(int id){
        ArrayList<Object> alarms = getAlarmObjects();
        Alarm alarmToEdit = (Alarm)alarms.get(getIndexOfID(id));
        alarmToEdit.toggleDailyRepeatable();
        tdb.remove(key);
        tdb.putListObject(key, alarms);
    }

    public void toggleWeeklyRepeatable(int id){
        ArrayList<Object> alarms = getAlarmObjects();
        Alarm alarmToEdit = (Alarm)alarms.get(getIndexOfID(id));
        alarmToEdit.toggleWeeklyRepeatable();
        tdb.remove(key);
        tdb.putListObject(key, alarms);
    }

    public void setRingtone(int index, String name){
        ArrayList<Object> alarms = getAlarmObjects();
        Alarm alarmToEdit = (Alarm)alarms.get(index);
        alarmToEdit.setRingTone(name);
        tdb.remove(key);
        tdb.putListObject(key, alarms);
    }

    //Helper method to get the Alarms as an ArrayList<Object>
    private ArrayList<Object> getAlarmObjects()
    {
        return tdb.getListObject(key, Alarm.class);
    }
}

