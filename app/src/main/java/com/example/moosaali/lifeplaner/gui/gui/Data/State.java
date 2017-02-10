package com.example.moosaali.lifeplaner.gui.gui.Data;
import android.content.Context;


/**
 * Created by Moosa Ali on 2017-02-02.
 * This is used to store data in file that is used on startup.
 */

public class State {
    private Utility utility;
    private Context context;
    private final int NUMBER_OF_STATES = 1;
    private final int TIMEZONE_LINE = 0;
    private final String STATE_FILE_NAME = "States";

    public State(Context context){
        this.context = context;
        utility = new Utility(context);
    }

    private String[] readState(){
        byte[] stateBytes = utility.readFile(STATE_FILE_NAME);
        String[] statesArray = utility.byteToStringArray(stateBytes);
        return statesArray;
    }


    public void setTimeZone( String newTimeZone){
        String[] newStatesArray = readState();
        if(newStatesArray == null){
            newStatesArray = new String[NUMBER_OF_STATES];
        }
        newStatesArray[TIMEZONE_LINE] = newTimeZone;
        String newStates = "";
        for(String st : newStatesArray){
            newStates += (st + "\r\n");
        }

        byte[] newStatesByteArray = newStates.getBytes();
        utility.writeFile(STATE_FILE_NAME, newStatesByteArray);

    }

    public String getTimeZone(){
        String[] states = readState();
        if(states == null){
            return null;
        }else{
            return states[TIMEZONE_LINE];
        }

    }




}
