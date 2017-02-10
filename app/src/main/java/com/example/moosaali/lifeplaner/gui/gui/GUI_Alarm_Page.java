package com.example.moosaali.lifeplaner.gui.gui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.moosaali.lifeplaner.R;
import com.example.moosaali.lifeplaner.gui.gui.Application.AlarmReceiver;

import java.sql.Time;

/**
 * This is a Activity for the alarm Page.
 */
public class GUI_Alarm_Page extends AppCompatActivity {

    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private Time time;
    private Context context;

    /**
     * Set OnClick listiner for Return Button and the addAlarm Button
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui__alarm__page);
        this.context = this;
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);


        final Intent myIntent = new Intent(context, AlarmReceiver.class);


        FloatingActionButton revertFab = (FloatingActionButton) findViewById(R.id.alarmRevertButton);
        revertFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(context, GUI_Front_Page.class);
                startActivity(intent);
            }
        });


        FloatingActionButton addAlarmFab = (FloatingActionButton) findViewById(R.id.addAlarmButton);
        addAlarmFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println( " Add Alarm Clicked");
                pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, pendingIntent);
                sendBroadcast(myIntent);
            }
        });





    }

}
