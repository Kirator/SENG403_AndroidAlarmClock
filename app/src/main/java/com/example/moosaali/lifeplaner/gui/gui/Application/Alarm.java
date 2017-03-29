package com.example.moosaali.lifeplaner.gui.gui.Application;

import java.util.Calendar;

/**
 * Created by Moosa Ali on 2017-02-02
 * Modified by Sebastian Crites 2107-28-02
 *
 * Alarm class has the date/time, type and message
 * for each created Alarm, and a boolean
 * to toggle the alarm on and off
 */

public class Alarm
{
    private int id;
    private boolean on, dailyRepeatable, weeklyRepeatable;
    private int year, month, day, hour, minute;
    //In case we need this later
    private String type;
    private String message;
    private String ringTone;

    private static int currentID = -1;

    public Alarm(int year,int month,int day,int hour,int minute,
            String type, String message, int id)
    {
        this.dailyRepeatable = false;
        this.weeklyRepeatable = false;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        currentID++; this.id = id;
        on = true;
        this.type = type;
        this.message = message;
        this.ringTone = "sample_ringtone";
    }

    public int getID()                      {return id;}
    public static int getMaxID()            {return currentID;}
    public void toggle()                    {on = !on;}
    public int getHour()                    {return hour;}
    public int getMinute()                  {return minute;}
    public boolean isON()                   {return on;}
    public int getMonth()                   {return month;}
    public int getDay()                     {return day;}
    public int getYear()                    {return year;}
    public String getMessage()              {return message;}
    public String getRingTone()             {return ringTone;}
    public void setMessage(String m)        {message = m;}
    public void setYear(int year)           {this.year = year;}
    public void setMonth(int month)         {this.month = month;}
    public void setDay(int day)             {this.day = day;}
    public void setHour(int hour)           {this.hour = hour;}
    public void setMinute(int minute)       {this.minute = minute;}
    public void setRingTone(String name)    {this.ringTone = name;}




    // Daily and Weekly Alarm Repeatable
    public boolean isDailyRepeatable() {
        return dailyRepeatable;
    }
    public void toggleDailyRepeatable () {
        dailyRepeatable = !dailyRepeatable;
    }
    public boolean isWeeklyRepeatable() {
        return weeklyRepeatable;
    }
    public void toggleWeeklyRepeatable () {
        weeklyRepeatable = !weeklyRepeatable;
    }

    public Long getAlarmTime(){
        Calendar alarmCal = Calendar.getInstance();
        alarmCal.set(this.year, this.month, this.day, this.hour, this.minute);
        // If time in past, prevent alarm manager from firing prematurely.
        if(alarmCal.before(Calendar.getInstance())){
            Calendar today = Calendar.getInstance();
            long diff = today.getTimeInMillis() - alarmCal.getTimeInMillis();
            int days = (int)(diff / (24 * 60 * 60 * 1000));
            System.out.println("Current days difference: " + days);
            alarmCal.add(Calendar.DATE, (days + 1));
        }
        return alarmCal.getTimeInMillis();
    }
}
