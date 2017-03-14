package com.example.moosaali.lifeplaner.gui.gui.Application;

import android.content.Context;

import com.example.moosaali.lifeplaner.gui.gui.Data.DataFacade;

import java.util.ArrayList;

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

    public void toggleRepeatable(Alarm alarmToToggle) {dataFacade.toggleRepeatable(alarmToToggle.getID());}
}
