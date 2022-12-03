package com.example.aggralarm;

import static com.example.aggralarm.App.mydatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class AlarmDatabase {
    public static void createAlarm(
            SetAlarm alarm
    ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("hours", alarm.hours);
        contentValues.put("minutes", alarm.minutes);
        contentValues.put("id", alarm.id);
        contentValues.put("monday", AlarmDatabase.booleanToInt(alarm.monday));
        contentValues.put("tuesday", AlarmDatabase.booleanToInt(alarm.tuesday));
        contentValues.put("wednesday", AlarmDatabase.booleanToInt(alarm.wednesday));
        contentValues.put("thursday", AlarmDatabase.booleanToInt(alarm.thursday));
        contentValues.put("friday", AlarmDatabase.booleanToInt(alarm.friday));
        contentValues.put("saturday", AlarmDatabase.booleanToInt(alarm.saturday));
        contentValues.put("sunday", AlarmDatabase.booleanToInt(alarm.sunday));
        contentValues.put("enabled", AlarmDatabase.booleanToInt(alarm.enabled));
        contentValues.put("quest", alarm.quest);
        contentValues.put("label", alarm.label);
        contentValues.put("timeofday", alarm.timeofday);
        mydatabase.insert("SetAlarm", null, contentValues);
    }

    public static int booleanToInt(boolean foo) {
        int bar = 0;
        if (foo) {
            bar = 1;
        }
        return bar;
    }

    public static boolean intToBoolean(int foo) {
        return foo == 1;
    }

    public static ArrayList<SetAlarm> listAlarms() {
        ArrayList<SetAlarm> alarms = new ArrayList<SetAlarm>();

        Cursor res =  mydatabase.rawQuery( "select * from SetAlarm order by timeofday ASC, hours ASC, minutes ASC", null );
        res.moveToFirst();


        while(!res.isAfterLast()){
            SetAlarm alarm = new SetAlarm(
                    res.getInt(res.getColumnIndex("hours")),
                    res.getInt(res.getColumnIndex("minutes")),
                    res.getInt(res.getColumnIndex("id")),
                    AlarmDatabase.intToBoolean(res.getInt(res.getColumnIndex("monday"))),
                    AlarmDatabase.intToBoolean(res.getInt(res.getColumnIndex("tuesday"))),
                    AlarmDatabase.intToBoolean(res.getInt(res.getColumnIndex("wednesday"))),
                    AlarmDatabase.intToBoolean(res.getInt(res.getColumnIndex("thursday"))),
                    AlarmDatabase.intToBoolean(res.getInt(res.getColumnIndex("friday"))),
                    AlarmDatabase.intToBoolean(res.getInt(res.getColumnIndex("saturday"))),
                    AlarmDatabase.intToBoolean(res.getInt(res.getColumnIndex("sunday"))),
                    AlarmDatabase.intToBoolean(res.getInt(res.getColumnIndex("enabled"))),
                    res.getString(res.getColumnIndex("quest")),
                    res.getString(res.getColumnIndex("label")),
                    res.getString(res.getColumnIndex("timeofday"))
            );
            alarms.add(alarm);
            res.moveToNext();
        }

        res.close();
        return alarms;
    }

    public static void updateAlarm(SetAlarm alarm) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("hours", alarm.hours);
        contentValues.put("minutes", alarm.minutes);
        contentValues.put("monday", AlarmDatabase.booleanToInt(alarm.monday));
        contentValues.put("tuesday", AlarmDatabase.booleanToInt(alarm.tuesday));
        contentValues.put("wednesday", AlarmDatabase.booleanToInt(alarm.wednesday));
        contentValues.put("thursday", AlarmDatabase.booleanToInt(alarm.thursday));
        contentValues.put("friday", AlarmDatabase.booleanToInt(alarm.friday));
        contentValues.put("saturday", AlarmDatabase.booleanToInt(alarm.saturday));
        contentValues.put("sunday", AlarmDatabase.booleanToInt(alarm.sunday));
        contentValues.put("enabled", AlarmDatabase.booleanToInt(alarm.enabled));
        contentValues.put("quest", alarm.quest);
        contentValues.put("label", alarm.label);
        contentValues.put("timeofday", alarm.timeofday);
        mydatabase.update("SetAlarm", contentValues, "id = ? ", new String[] { Integer.toString(alarm.id) } );
    }

    public static SetAlarm getAlarm(int id) {
        Cursor res =  mydatabase.rawQuery( "select * from SetAlarm where id = ? ", new String[] { Integer.toString(id) } );
        res.moveToFirst();
        SetAlarm alarm = null;

            alarm = new SetAlarm(
                    res.getInt(res.getColumnIndex("hours")),
                    res.getInt(res.getColumnIndex("minutes")),
                    res.getInt(res.getColumnIndex("id")),
                    AlarmDatabase.intToBoolean(res.getInt(res.getColumnIndex("monday"))),
                    AlarmDatabase.intToBoolean(res.getInt(res.getColumnIndex("tuesday"))),
                    AlarmDatabase.intToBoolean(res.getInt(res.getColumnIndex("wednesday"))),
                    AlarmDatabase.intToBoolean(res.getInt(res.getColumnIndex("thursday"))),
                    AlarmDatabase.intToBoolean(res.getInt(res.getColumnIndex("friday"))),
                    AlarmDatabase.intToBoolean(res.getInt(res.getColumnIndex("saturday"))),
                    AlarmDatabase.intToBoolean(res.getInt(res.getColumnIndex("sunday"))),
                    AlarmDatabase.intToBoolean(res.getInt(res.getColumnIndex("enabled"))),
                    res.getString(res.getColumnIndex("quest")),
                    res.getString(res.getColumnIndex("label")),
                    res.getString(res.getColumnIndex("timeofday"))
                    );

        res.close();
        return alarm;
    }

    public static void deleteAlarm(int id) {
        mydatabase.delete("SetAlarm", "id = ? ", new String[] { Integer.toString(id) } );
    }

}