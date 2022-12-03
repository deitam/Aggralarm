package com.example.aggralarm;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class SetAlarm {
    public int hours;
    public int minutes;
    public int id;
    public boolean monday;
    public boolean tuesday;
    public boolean wednesday;
    public boolean thursday;
    public boolean friday;
    public boolean saturday;
    public boolean sunday;
    public boolean enabled;
    public String quest;
    public String label;
    public String timeofday;

    public SetAlarm (
        int hours,
        int minutes,
        int id,
        boolean monday,
        boolean tuesday,
        boolean wednesday,
        boolean thursday,
        boolean friday,
        boolean saturday,
        boolean sunday,
        boolean enabled,
        String quest,
        String label,
        String timeofday
    ) {
        this.hours = hours;
        this.minutes = minutes;
        this.id = id;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.enabled = enabled;
        this.quest = quest;
        this.label = label;
        this.timeofday = timeofday;

    }

    public void turnOn(Context context, AlarmManager alarmManager) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // if alarm time has already passed, increment day by 1
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        // BroadcastReceiver
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(Integer.toString(id));
        // we call broadcast using pendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_MUTABLE);
        boolean recurring = monday || tuesday || wednesday || thursday || friday || saturday || sunday;

        if (!recurring) {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        } else {
            final long RUN_DAILY = 24 * 60 * 60 * 1000;
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    RUN_DAILY,
                    pendingIntent
            );

            // Alarm rings continuously until toggle button is turned off
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            // alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (time * 1000), pendingIntent);
        }

        enabled = true;
        AlarmDatabase.updateAlarm(this);
        Toast.makeText(context, "ALARM ON", Toast.LENGTH_SHORT).show();

    }

    public void turnOff(Context context, AlarmManager alarmManager) {
        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                id,
                intent,
                PendingIntent.FLAG_MUTABLE
        );

        alarmManager.cancel(pendingIntent);

        enabled = false;
        AlarmDatabase.updateAlarm(this);
        Toast.makeText(context, "ALARM OFF", Toast.LENGTH_SHORT).show();

    }
}