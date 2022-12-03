package com.example.aggralarm;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.Calendar;
import java.util.Random;
import java.util.StringJoiner;

public class MainActivity extends AppCompatActivity {
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    LinearLayout alarmList;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_list);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmList = (LinearLayout) findViewById(R.id.alarm_list_layout);
        context = getApplicationContext();

        for (SetAlarm alarm: AlarmDatabase.listAlarms()) {
            addAlarm(alarm);
        }

//        SetAlarm alarm = new SetAlarm(12, 34, 123, true, true, true, true, true, true, true, true, "SHAKE", "WAAKE", "AM");
//        addAlarm(alarm);
//        addAlarm(alarm);
//        addAlarm(alarm);

        getSupportActionBar().setTitle("Aggralarm");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.alarm_list);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmList = (LinearLayout) findViewById(R.id.alarm_list_layout);
        context = getApplicationContext();

        for (SetAlarm alarm: AlarmDatabase.listAlarms()) {
            addAlarm(alarm);
        }

        getSupportActionBar().setTitle("Aggralarm");
    }

    public void addAlarm(SetAlarm alarm) {
        View v = LayoutInflater.from(this).inflate(R.layout.alarm_item, alarmList, false);
        TextView time = v.findViewById(R.id.ar_time);
        TextView timeofday = v.findViewById(R.id.ar_am_pm);
        TextView label = v.findViewById(R.id.ar_label);
        TextView weekdays = v.findViewById(R.id.ar_days);
        TextView quest = v.findViewById(R.id.ar_quest);
        Switch on_off = v.findViewById(R.id.switch1);

        int hours = alarm.hours;
        if (hours > 12) hours = hours - 12;

        time.setText(String.format("%02d:%02d", hours, alarm.minutes));
        timeofday.setText(alarm.timeofday);
        label.setText(alarm.label);
        String weekdays_string = getWeekdaysString(alarm);
        weekdays.setText(weekdays_string);
        quest.setText(String.format("Quest: %s", alarm.quest));
        on_off.setChecked(alarm.enabled);

        on_off.setOnClickListener(new Switch.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((Switch) view).isChecked()) {
                    alarm.turnOn(getApplicationContext(), alarmManager);
                } else {
                    alarm.turnOff(getApplicationContext(), alarmManager);
                }
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(context, AlarmCreate.class);
                Bundle b = new Bundle();
                b.putInt("ALARM_EXTRA", alarm.id); //Your id
                i.putExtras(b);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        alarmList.addView(v);

    }

    public void onAddClicked(View view) {
        final Intent i = new Intent(context, AlarmCreate.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public String getWeekdaysString(SetAlarm alarm) {
        if (!alarm.monday &&
                !alarm.tuesday &&
                !alarm.wednesday &&
                !alarm.thursday &&
                !alarm.friday &&
                !alarm.saturday &&
                !alarm.sunday ) {
            return "Does not repeat";
        }

        if (alarm.monday &&
                alarm.tuesday &&
                alarm.wednesday &&
                alarm.thursday &&
                alarm.friday &&
                alarm.saturday &&
                alarm.sunday ) {
            return "Every day";
        }

        if (alarm.monday &&
                alarm.tuesday &&
                alarm.wednesday &&
                alarm.thursday &&
                alarm.friday &&
                !alarm.saturday &&
                !alarm.sunday ) {
            return "Weekdays";
        }

        if (!alarm.monday &&
                !alarm.tuesday &&
                !alarm.wednesday &&
                !alarm.thursday &&
                !alarm.friday &&
                alarm.saturday &&
                alarm.sunday ) {
            return "Weekends";
        }

        StringBuilder built_string = new StringBuilder();

        StringBuilder mo = new StringBuilder(" Mo");    //String 1
        StringBuilder tu = new StringBuilder(" Tu");    //String 2
        StringBuilder we = new StringBuilder(" We");    //String 2
        StringBuilder th = new StringBuilder(" Th");    //String 2
        StringBuilder fr = new StringBuilder(" Fr");    //String 2
        StringBuilder sa = new StringBuilder(" Sa");    //String 2
        StringBuilder su = new StringBuilder(" Su");    //String 2

        if (alarm.monday) built_string.append(mo);
        if (alarm.tuesday) built_string.append(tu);
        if (alarm.wednesday) built_string.append(we);
        if (alarm.thursday) built_string.append(th);
        if (alarm.friday) built_string.append(fr);
        if (alarm.saturday) built_string.append(sa);
        if (alarm.sunday) built_string.append(su);

        return built_string.substring(1);
    }

    // OnToggleClicked() method is implemented the time functionality
//    public void OnToggleClicked(View view) {
//        long time;
//        if (((ToggleButton) view).isChecked()) {
//            int alarmId = new Random().nextInt(Integer.MAX_VALUE);
//            Toast.makeText(MainActivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();
//            Calendar calendar = Calendar.getInstance();
//
//            // calendar is called to get current time in hour and minute
//            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
//            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
//
//            RadioGroup myGroup = findViewById(R.id.radioGroup);
//            int buttonId = myGroup.getCheckedRadioButtonId();
//            RadioButton rbNoQuest = findViewById(R.id.rbNoQuest);
//            RadioButton rbShakeQuest = findViewById(R.id.rbShakeQuest);
//            RadioButton rbMathQuest = findViewById(R.id.rbMathQuest);
//            String button_name = "";
//
//            if (buttonId == rbNoQuest.getId()){
//                button_name = "No quest";
//            } else if (buttonId == rbShakeQuest.getId()){
//                button_name = "Shake quest";
//            } else if (buttonId == rbMathQuest.getId()){
//                button_name = "Math quest";
//            }
//            Toast.makeText(MainActivity.this, String.format("Selected radio -  %s", button_name), Toast.LENGTH_SHORT).show();
//
//
//            // using intent i have class AlarmReceiver class which inherits
//            // BroadcastReceiver
//            Intent intent = new Intent(this, AlarmReceiver.class);
//
//            // we call broadcast using pendingIntent
//            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), alarmId, intent, PendingIntent.FLAG_IMMUTABLE);
//
//            time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
//            if (System.currentTimeMillis() > time) {
//                // setting time as AM and PM
//                if (Calendar.AM_PM == 0)
//                    time = time + (1000 * 60 * 60 * 12);
//                else
//                    time = time + (1000 * 60 * 60 * 24);
//            }
//            // Alarm rings continuously until toggle button is turned off
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
//            // alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (time * 1000), pendingIntent);
//        } else {
//            alarmManager.cancel(pendingIntent);
//            Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
//        }
//    }
}