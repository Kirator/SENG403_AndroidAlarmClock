package com.example.moosaali.lifeplaner.gui.gui.Application;

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
    private boolean on;
    private int year, month, day, hour, minute;
    private String type;
    private String message;

    private static int currentID = -1;

    public Alarm(int year,int month,int day,int hour,int minute,
            String type, String message)
    {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = hour;
        currentID++; id = currentID;
        on = true;
        this.type = type;
        this.message = message;
    }

    public int getID()                      {return id;}
    public static int getMaxID()            {return currentID;}
    public void toggle()                    {on = !on;}
}
