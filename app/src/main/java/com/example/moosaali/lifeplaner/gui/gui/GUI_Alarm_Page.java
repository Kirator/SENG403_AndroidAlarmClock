package com.example.moosaali.lifeplaner.gui.gui;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.moosaali.lifeplaner.R;
import com.example.moosaali.lifeplaner.gui.gui.Application.AlarmReceiver;
import java.util.Calendar;


/**
 * This is a Activity for the alarm Page.
 */
public class GUI_Alarm_Page extends AppCompatActivity {
    private int dateDialogId = 1, timeDialogId = 2;
    //private PendingIntent pendingIntent;
    private Context context;
    private int year_a, month_a, day_a;
    private int hour_a, minute_a;


    /**
     * Set OnClick listiner for Return Button and the addAlarm Button
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui__alarm__page);
        this.context = this;
        final Calendar calendar = Calendar.getInstance();
        year_a = calendar.get(Calendar.YEAR);
        month_a = calendar.get(Calendar.MONTH);
        day_a = calendar.get(Calendar.DAY_OF_MONTH);



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
                showDialog(dateDialogId);
            }
        });

        displayAlarms();


    }

    private void displayAlarms() {

        String[] testArray = {"Hell", "asda"};
        CustomAdapter listAdapter = new CustomAdapter(this, testArray);
        ListView listView = (ListView)findViewById(R.id.alarmListView);
        listView.setAdapter(listAdapter);

    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id == this.dateDialogId){
            return new DatePickerDialog(this, datePickerListener,year_a, month_a, day_a );
        } else if(id == this.timeDialogId){
            return new TimePickerDialog(this, timePickerListener, hour_a, minute_a, false);
        } else {
            return null;
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_a = year;
            month_a = month;
            day_a = dayOfMonth;
            showDialog(timeDialogId);
            Toast.makeText(context,year + "  " + month + " " + dayOfMonth,Toast.LENGTH_SHORT).show();
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_a = hourOfDay;
            minute_a = minute;
            Toast.makeText(context,hourOfDay + "  " + minute ,Toast.LENGTH_SHORT).show();

            Long alertTime = getAlarmTime();
            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getBroadcast(context, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT));
            System.out.println( "Alarm Set");

        }
    };

    private Long getAlarmTime(){
        Calendar alarmCal = Calendar.getInstance();
        alarmCal.set(year_a, month_a, day_a, hour_a, minute_a);
        return alarmCal.getTimeInMillis() - 1000;
    }




}


class CustomAdapter extends ArrayAdapter<String>{

    public CustomAdapter(Context context, String[] alarms) {
        super(context, R.layout.alarm_view,alarms);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflator = LayoutInflater.from(getContext());
        View customView = layoutInflator.inflate(R.layout.alarm_view, parent, false);
        String alarmTime = getItem(position);
        TextView alarmTimeText = (TextView) customView.findViewById(R.id.alarmTimeTextView);
        TextView fmOrAmText = (TextView) customView.findViewById(R.id.amOrFmTextView);
        Switch alarmSwitch = (Switch) customView.findViewById(R.id.alarmSwitch);
        alarmSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Clicked!",Toast.LENGTH_SHORT).show();
            }
        });

        return customView;
    }
}