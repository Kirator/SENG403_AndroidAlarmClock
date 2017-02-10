package com.example.moosaali.lifeplaner.gui.gui.Data;

import android.content.Context;

/**
 * Created by Moosa Ali on 2017-02-02.
 */

/**
 * This is the interface for the Data layer.
 * Any FileIO is done through this.
 * Look a Utility Class for IO functions
 *
 */
public class DataFacade {
    private State state;

    public DataFacade(Context context){
        state = new State(context);
    }

    public void SetTimeZone(String newState){
        state.setTimeZone(newState);
    }

    public String getTimeZone(){
        return state.getTimeZone();
    }

    public String[] getAllAlarms(){return null;}



}
