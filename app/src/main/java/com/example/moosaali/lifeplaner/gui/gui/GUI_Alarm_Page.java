package com.example.moosaali.lifeplaner.gui.gui;
import android.app.AlarmManager;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import android.text.TextWatcher;

import com.example.moosaali.lifeplaner.R;
import com.example.moosaali.lifeplaner.gui.gui.Application.Alarm;
import com.example.moosaali.lifeplaner.gui.gui.Application.AlarmReceiver;
import com.example.moosaali.lifeplaner.gui.gui.Application.ApplicationFacade;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * This is a Activity for the alarm Page.
 */
public class GUI_Alarm_Page extends AppCompatActivity {
    private static int dateDialogId = 1, timeDialogId = 2, nameDialogId = 3;
    private Context context;
    private int year_a, month_a, day_a, hour_a, minute_a;
    private ApplicationFacade appFacade;
    private ArrayList<Alarm> allAlarms;
    public int editAlarmId = 0;
    public boolean alarmEdit = false;
    private static GUI_Alarm_Page alarmPageInstance;
    /**
     * Set OnClick listiner for Return Button and the addAlarm Button
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui__alarm__page);
        this.context = this;
        alarmPageInstance = this;
        final Calendar calendar = Calendar.getInstance();
        appFacade = new ApplicationFacade(context);

        year_a = calendar.get(Calendar.YEAR);
        month_a = calendar.get(Calendar.MONTH);
        day_a = calendar.get(Calendar.DAY_OF_MONTH);
        hour_a = calendar.get(Calendar.HOUR_OF_DAY);
        minute_a = calendar.get(Calendar.MINUTE);

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
        allAlarms = appFacade.getAllAlarms();
        CustomAdapter listAdapter = new CustomAdapter(this, allAlarms, appFacade);
        ListView listView = (ListView)findViewById(R.id.alarmListView);
        listView.setAdapter(listAdapter);

    }

    @Override
    public Dialog onCreateDialog(int id){
        if(id == this.dateDialogId){
            return new DatePickerDialog(this, datePickerListener,year_a, month_a, day_a );
        } else if(id == this.timeDialogId){
            return new TimePickerDialog(this, timePickerListener, hour_a, minute_a, false);
        } else {
            return null;
        }
    }

    public DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_a = year;
            month_a = month;
            day_a = dayOfMonth;
            showDialog(timeDialogId);
        }
    };

    public TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_a = hourOfDay;
            minute_a = minute;
            Long alertTime = getAlarmTime();
            int id = (alarmEdit == true ? editAlarmId : appFacade.getNextAlarmId());
            Toast.makeText(context,"ID"  + ": " + id,Toast.LENGTH_SHORT).show();
            if(alertTime >= Calendar.getInstance().getTimeInMillis()){
                Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                alarmIntent.putExtra("ID", id);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alertTime = alertTime - (alertTime % 1000);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP , alertTime , PendingIntent.getBroadcast(context, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT));
                Toast.makeText(context,"Alarm Set"  + ": " + id,Toast.LENGTH_SHORT).show();
            }
            if(alarmEdit == false){
                appFacade.addAlarm(year_a, month_a, day_a, hour_a, minute_a,"ALARM", "Enter Alarm Name Here", id);
            } else{
                appFacade.editAlarm(year_a,month_a, day_a, hour_a, minute_a, id);
            }
            allAlarms = appFacade.getAllAlarms();
            getInstance().displayAlarms();
            alarmEdit = false;
        }
    };



    private Long getAlarmTime(){
        Calendar alarmCal = Calendar.getInstance();
        alarmCal.set(year_a, month_a, day_a, hour_a, minute_a);
        return alarmCal.getTimeInMillis();
    }

    public static GUI_Alarm_Page getInstance(){return alarmPageInstance;}


}


class CustomAdapter extends ArrayAdapter<Alarm>{
    private ArrayList<Alarm> alarms;
    private ApplicationFacade appFacade;
    private CustomAdapter adapter;
    public CustomAdapter(Context context, ArrayList<Alarm> alarms, ApplicationFacade appFacade) {
        super(context, R.layout.alarm_view, alarms);
        this.alarms = alarms;
        this.appFacade = appFacade;
        adapter = this;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflator = LayoutInflater.from(getContext());
        View customView = layoutInflator.inflate(R.layout.alarm_view, parent, false);
        final Alarm currentAlarm = getItem(position);
        TextView alarmTimeText = (TextView) customView.findViewById(R.id.alarmTimeTextView);
        TextView fmOrAmText = (TextView) customView.findViewById(R.id.amOrFmTextView);
        TextView alarmDateText = (TextView)customView.findViewById(R.id.alarmDatetextView);

        int hour = currentAlarm.getHour();
        if(hour < 12){
            hour = (hour == 0 ? 12 : hour);
            fmOrAmText.setText("AM");
        }else{
            hour -= 12;
            hour = (hour == 0 ? 12 : hour);
            fmOrAmText.setText("PM");
        }
        alarmTimeText.setText(String.format("%02d:%02d",hour, currentAlarm.getMinute()));
        String month = "";
        try {
            SimpleDateFormat monthParse = new SimpleDateFormat("MM");
            SimpleDateFormat monthDisplay = new SimpleDateFormat("MMMM");
            month = monthDisplay.format(monthParse.parse(Integer.toString(currentAlarm.getMonth() + 1)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        alarmDateText.setText(month + " " + currentAlarm.getDay() + ", " + currentAlarm.getYear());

        final Switch alarmSwitch = (Switch) customView.findViewById(R.id.alarmSwitch);
        if(appFacade.getAlarm(currentAlarm.getID()).isON()) {
            alarmSwitch.setChecked(true);
        }else{
            alarmSwitch.setChecked(false);
        }



        alarmSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appFacade.toggleAlarm(currentAlarm);
                adapter.clear();
                //appFacade.removeAlarm(currentAlarm);
                alarms = appFacade.getAllAlarms();
                for(int i = 0; i < alarms.size(); i++){
                    adapter.insert(alarms.get(i), i);
                }
                adapter.notifyDataSetInvalidated();


                Toast.makeText(getContext(),"Clicked " + position,Toast.LENGTH_SHORT).show();
            }
        });


        alarmTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GUI_Alarm_Page alarmPageInstance = GUI_Alarm_Page.getInstance();
                alarmPageInstance.alarmEdit = true;
                alarmPageInstance.editAlarmId = currentAlarm.getID();
                Toast.makeText(getContext(),"ID " + currentAlarm.getID(),Toast.LENGTH_SHORT).show();
                alarmPageInstance.showDialog(1);

            }
        });

        ImageButton cancleButton = (ImageButton) customView.findViewById(R.id.cancleAlarmButton);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                appFacade.removeAlarm(currentAlarm);
                alarms = appFacade.getAllAlarms();
                for(int i = 0; i < alarms.size(); i++){
                    adapter.insert(alarms.get(i), i);
                }
                adapter.notifyDataSetInvalidated();


                Toast.makeText(getContext(),"Alarm Removed " + position,Toast.LENGTH_SHORT).show();
            }
        });


        // DAILY ALARM SWITCH
        final TextView dailyAlarmSwitch = (TextView) customView.findViewById(R.id.dailyAlarmSwitch);

        dailyAlarmSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dailyAlarmSwitch.getTextColors().getDefaultColor() == Color.YELLOW) {
                    dailyAlarmSwitch.setTextColor(Color.BLACK);
                } else {
                    dailyAlarmSwitch.setTextColor(Color.YELLOW);
                }
            }
        });




        // ALARM MESSAGE DISPLAY
        class ListenerOnTextChange implements TextWatcher {
            private Context mContext;
            EditText mEditText;

            public ListenerOnTextChange (Context context, EditText editText) {
                super();
                this.mContext = context;
                this.mEditText = editText;
            }

            @Override
            public void afterTextChanged(Editable s){
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                appFacade.changeAlarmMessage(currentAlarm, s.toString());
                currentAlarm.setMessage(s.toString());
            }
        }

        EditText alarmName = (EditText) customView.findViewById(R.id.editAlarmText);
        alarmName.setText(currentAlarm.getMessage());
        alarmName.addTextChangedListener(new ListenerOnTextChange(getContext(), alarmName));

        return customView;
    }




}