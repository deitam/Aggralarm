package com.example.aggralarm;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";
    public static SQLiteDatabase mydatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannnel();
        initialiseDatabase();
    }

    private void createNotificationChannnel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Alarm Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private void initialiseDatabase() {
        mydatabase = openOrCreateDatabase("Saved Alarms",MODE_PRIVATE,null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS " +
                "SetAlarm(hours SMALLINT," +
                "minutes SMALLINT," +
                "id INTEGER PRIMARY KEY," +
                "monday TINYINT ," +
                "tuesday TINYINT ," +
                "wednesday TINYINT ," +
                "thursday TINYINT ," +
                "friday TINYINT ," +
                "saturday TINYINT ," +
                "sunday TINYINT ," +
                "enabled TINYINT ," +
                "quest VARCHAR ," +
                "label VARCHAR ," +
                "timeofday VARCHAR);"
        );
    }
}