package com.example.moosaali.lifeplaner.gui.gui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.moosaali.lifeplaner.gui.gui.Application.Alarm;
import com.example.moosaali.lifeplaner.gui.gui.Application.ApplicationFacade;

import java.util.ArrayList;

/**
 * Created by Moosa Ali on 2017-02-03.
 * Please Ignore This for now, We might need it latter
 */

public class TimeService extends Service {
    private ApplicationFacade applicationFacade;
    ArrayList<Alarm> alarms;



    public void onCreate(){
        alarms = new ArrayList();
        applicationFacade = new ApplicationFacade(this);

    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {

    }
    public void onStart(Intent intent,int ringtoneId){

        Log.d("TimeService", "On start");

    }


}
