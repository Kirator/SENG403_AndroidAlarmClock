package com.example.moosaali.lifeplaner.gui.gui.Application;


import java.util.Date;



/**
 * Created by Moosa Ali on 2017-02-02.
 */

public class Alarm {
    private int id;
    private Date date;
    private String type;
    private String message;
    private String day;
    public static int alarmNum = 0;


    public Alarm (String date, String type, String message){

        this.date = new Date(Long.parseLong(date));
        this.type = type;
        this.message = message;
        id = alarmNum++;
    }


}
