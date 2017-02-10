package com.example.moosaali.lifeplaner.gui.gui;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextClock;
import com.example.moosaali.lifeplaner.R;
import com.example.moosaali.lifeplaner.gui.gui.Application.ApplicationFacade;


/**
 * This is the Acivity for the Front Page
 */
public class GUI_Front_Page extends AppCompatActivity {

    private FloatingActionButton selectLocationButton;
    private ApplicationFacade applicationFacade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationFacade = new ApplicationFacade(getApplicationContext());
        setContentView(R.layout.activity_gui__front__page);
        timeSetUp();




        //Select Location Float button
        selectLocationButton = (FloatingActionButton) findViewById(R.id.locationSelectButton);
        selectLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(getApplicationContext(), RingtonePlayingService.class));
                Log.d("ActionButton", "Location Selection Button Pressed.");
                Intent intent = new Intent(getApplicationContext(), TImezoneSelect_Page.class);
                startActivity(intent);
            }
        });

        selectLocationButton = (FloatingActionButton) findViewById(R.id.alarmFloatingButton);
        selectLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ActionButton", "Alarm Floating Button Pressed.");
                Intent intent = new Intent(getApplicationContext(), GUI_Alarm_Page.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gui__front__page, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void timeSetUp(){
        String timeZone = applicationFacade.getTimeZone();
        if(timeZone != null){
            System.out.println("Read Successful: " + timeZone);
            TextClock textClock = (TextClock) findViewById(R.id.textClock);
            textClock.setTimeZone(timeZone);
        }

    }



}
