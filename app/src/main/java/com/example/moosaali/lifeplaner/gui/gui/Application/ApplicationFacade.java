package com.example.moosaali.lifeplaner.gui.gui.Application;

import android.content.Context;

import com.example.moosaali.lifeplaner.gui.gui.Data.DataFacade;

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



}
