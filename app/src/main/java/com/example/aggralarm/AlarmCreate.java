package com.example.aggralarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;
import java.util.Random;

public class AlarmCreate extends AppCompatActivity {
    TimePicker alarmTimePicker;
    AlarmManager alarmManager;
    SetAlarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_create);

        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(topToolBar);
        ActionBar actionBar = getSupportActionBar();;
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle b = getIntent().getExtras();
        if(b != null) {
            int value = b.getInt("ALARM_EXTRA");
            alarm = AlarmDatabase.getAlarm(value);
            Toast.makeText(this, String.format("Selected alarm -  %2d", alarm.id), Toast.LENGTH_SHORT).show();
            getSupportActionBar().setTitle("Edit alarm");

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmTimePicker.setMinute(alarm.minutes);
                alarmTimePicker.setHour(alarm.hours);
            } else {
                alarmTimePicker.setCurrentMinute(alarm.minutes);
                alarmTimePicker.setCurrentHour(alarm.hours);
            }

            CheckBox monday = (CheckBox) findViewById(R.id.checkBox1);
            CheckBox tuesday = (CheckBox) findViewById(R.id.checkBox2);
            CheckBox wednesday = (CheckBox) findViewById(R.id.checkBox3);
            CheckBox thursday = (CheckBox) findViewById(R.id.checkBox4);
            CheckBox friday = (CheckBox) findViewById(R.id.checkBox5);
            CheckBox saturday = (CheckBox) findViewById(R.id.checkBox6);
            CheckBox sunday = (CheckBox) findViewById(R.id.checkBox7);

            if (alarm.monday) monday.setChecked(true);
            if (alarm.tuesday) tuesday.setChecked(true);
            if (alarm.wednesday) wednesday.setChecked(true);
            if (alarm.thursday) thursday.setChecked(true);
            if (alarm.friday) friday.setChecked(true);
            if (alarm.saturday) saturday.setChecked(true);
            if (alarm.sunday) sunday.setChecked(true);

            TextView label = (TextView) findViewById(R.id.alarmLabel);
            label.setText(alarm.label);

            RadioButton rbNoQuest = findViewById(R.id.rbNoQuest);
            RadioButton rbShakeQuest = findViewById(R.id.rbShakeQuest);
            RadioButton rbMathQuest = findViewById(R.id.rbMathQuest);

            if (Objects.equals(alarm.quest, "NONE")){
                rbNoQuest.setChecked(true);
            } else if (Objects.equals(alarm.quest, "SHAKE")){
                rbShakeQuest.setChecked(true);
            } else if (Objects.equals(alarm.quest, "MATH")){
                rbMathQuest.setChecked(true);
            }

        } else {
            ImageButton deleteButton = (ImageButton) findViewById(R.id.DeleteButton);
            deleteButton.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Create alarm");
        }

//        alarmTimePicker.setHour(alarm.hours);

    }

    // OnToggleClicked() method is implemented the time functionality
    public void OnSaveClicked(View view) {
            int alarmId;
            if (alarm == null) {
                alarmId = new Random().nextInt(Integer.MAX_VALUE);
            } else {
                alarmId = alarm.id;
            }

            RadioGroup myGroup = findViewById(R.id.radioGroup);
            int buttonId = myGroup.getCheckedRadioButtonId();
            RadioButton rbNoQuest = findViewById(R.id.rbNoQuest);
            RadioButton rbShakeQuest = findViewById(R.id.rbShakeQuest);
            RadioButton rbMathQuest = findViewById(R.id.rbMathQuest);
            String quest = "";

            if (buttonId == rbNoQuest.getId()){
                quest = "NONE";
            } else if (buttonId == rbShakeQuest.getId()){
                quest = "SHAKE";
            } else if (buttonId == rbMathQuest.getId()){
                quest = "MATH";
            }
            CheckBox monday = (CheckBox) findViewById(R.id.checkBox1);
            CheckBox tuesday = (CheckBox) findViewById(R.id.checkBox2);
            CheckBox wednesday = (CheckBox) findViewById(R.id.checkBox3);
            CheckBox thursday = (CheckBox) findViewById(R.id.checkBox4);
            CheckBox friday = (CheckBox) findViewById(R.id.checkBox5);
            CheckBox saturday = (CheckBox) findViewById(R.id.checkBox6);
            CheckBox sunday = (CheckBox) findViewById(R.id.checkBox7);

            TextView label = (TextView) findViewById(R.id.alarmLabel);

            String AM_PM ;
            if(alarmTimePicker.getCurrentHour() < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }

            String label_text = (String) label.getText().toString();

            SetAlarm new_alarm = new SetAlarm(
                    alarmTimePicker.getCurrentHour(),
                    alarmTimePicker.getCurrentMinute(),
                    alarmId,
                    monday.isChecked(),
                    tuesday.isChecked(),
                    wednesday.isChecked(),
                    thursday.isChecked(),
                    friday.isChecked(),
                    saturday.isChecked(),
                    sunday.isChecked(),
                    true,
                    quest,
                    label_text,
                    AM_PM
            );
            if (alarm == null) {
                AlarmDatabase.createAlarm(new_alarm);
            } else {
                AlarmDatabase.updateAlarm(new_alarm);
            }
            new_alarm.turnOn(getApplicationContext(), alarmManager);
            super.onBackPressed();
    }
    public void OnDeleteClicked(View view) {
        alarm.turnOff(getApplicationContext(), alarmManager);
        AlarmDatabase.deleteAlarm(alarm.id);
        super.onBackPressed();
    }
}