package com.example.moosaali.lifeplaner.gui.gui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.TimeZone;
import com.example.moosaali.lifeplaner.R;
import com.example.moosaali.lifeplaner.gui.gui.Application.ApplicationFacade;
import com.example.moosaali.lifeplaner.gui.gui.Data.DataFacade;

/**
 * This Activity is for timezone Select we can leave it in
 * as we might need it later but I was just using this to test some stuff,
 */
public class TImezoneSelect_Page extends AppCompatActivity {
    private ApplicationFacade applicationFacade;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationFacade = new ApplicationFacade(getApplicationContext());


        setContentView(R.layout.activity_timezone_select__page);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GUI_Front_Page.class);
                startActivity(intent);
            }
        });

        String[] timeZones = getAllTimezones();
        showList(timeZones);


    }

    private String[] getAllTimezones(){
        String[] timeZoneIds = TimeZone.getAvailableIDs();
        
        return timeZoneIds;
    }

    private void showList(String[] names){
        LinearLayout layout = (LinearLayout) findViewById(R.id.timeZoneLinearLayout);
        TextView t;
        RelativeLayout l;

        for(int i  = 0; i < names.length; i ++){

            final String id = names[i];
            t = new TextView(getApplicationContext());
            t.setText(names[i]);
            l = new RelativeLayout(getApplicationContext());

            l.setMinimumHeight(100);
            l.addView(t);
            layout.addView(l);

            if(i%2 ==1){
                l.setBackgroundColor(Color.parseColor("#97d185"));
            } else{
                l.setBackgroundColor(Color.parseColor("#a1b59a"));
            }
            l.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                public void onClick(View v) {
                    applicationFacade.SetTimeZone(id);
                }
            });

        }

    }

}
