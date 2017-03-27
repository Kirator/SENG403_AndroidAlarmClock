package com.example.moosaali.lifeplaner.gui.gui.Data;

import android.content.Context;

import com.example.moosaali.lifeplaner.gui.gui.Application.Alarm;

import java.util.ArrayList;

/**
 * Created by Moosa Ali on 2017-02-02.
 *
 * This is the interface for the Data layer.
 * Any FileIO is done through this class
 * Look a Utility Class for IO functions
 *
 */
public class DataFacade
{
    private State state;
    private AlarmLog log;

    public DataFacade(Context context)
    {
        state = new State(context);
        log = new AlarmLog(context);
    }

    public int getMaxID()                       {return log.getMaxId();}

    public void SetTimeZone(String newState)    {state.setTimeZone(newState);}

    public String getTimeZone()                 {return state.getTimeZone();}

    public ArrayList<Alarm> getAlarms()         {return log.getAllAlarms();}

    public void addAlarm(Alarm toAdd)           {log.addAlarm(toAdd);}

    public Alarm getAlarm(int id)               {return log.getAlarm(id);}

    public void deleteAlarm(int id)             {log.deleteAlarm(id);}

    public void toggle(int id)                  {log.toggle(id);}

    public void changeMessage(int id, String m) {log.changeMessage(id, m);}

    public void editAlarm (int id, int year, int month, int day, int hour, int minute) {log.editAlarm(id, year, month, day, hour, minute);}

    public void toggleDailyRepeatable(int id)   {log.toggleDailyRepeatable(id);}

    public boolean isDailyRepeatable(int id)    {return getAlarm(id).isDailyRepeatable();}

    public void toggleWeeklyRepeatable(int id)   {log.toggleWeeklyRepeatable(id);}

    public boolean isWeeklyRepeatable(int id)    {return getAlarm(id).isWeeklyRepeatable();}
}
