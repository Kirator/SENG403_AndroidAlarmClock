package com.example.moosaali.lifeplaner.gui.gui.Data;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Moosa Ali on 2017-02-02.
 * Mainly File IO but feel free to add functions here
 * that you feel do not belong in other classes that exists
 * or ones you may have made.
 */

public class Utility {

    private Context context;
    public Utility(Context context){
        this.context = context;
    }

    public String[] byteToStringArray(byte[] bytes){
        String states = "";
        for(byte c : bytes){
            states += (char)c;
        }
        String[] statesArray = states.split("\r\n");
        return statesArray;
    }

    public byte[] readFile(String fileName){
        FileInputStream fis;
        byte[] stateBytes = new byte[256];
        try{
            fis = context.openFileInput(fileName);
            fis.read(stateBytes);
            fis.close();

        }catch(FileNotFoundException nfne){
            Log.e("File IO", "State File Not Found.");
        } catch (IOException e) {
            Log.e("File IO", "State File Not Found.");
        }

        return stateBytes;

    }
    public void writeFile(String fileName, byte[] bytes){

        FileOutputStream fos;
        try{
            fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            fos.write(bytes, 0, bytes.length);
            fos.close();
        }catch(FileNotFoundException fne){
            Log.d("File Read", "Failed to Read State File.");
        } catch (IOException e) {
            Log.d("File Read", "IOException");
        }

    }


}
