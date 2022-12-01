package com.example.aggralarm;

import static com.example.aggralarm.App.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Random;

public class AlarmService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);
        Toast.makeText(this, "Making intent", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, AlarmActivity.class);
        Toast.makeText(this, "Making pending intent", Toast.LENGTH_LONG).show();
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), alarmId, i, PendingIntent.FLAG_IMMUTABLE);
        Toast.makeText(this, "Making notification", Toast.LENGTH_LONG).show();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Alarm")
                .setContentText("Wake up pls")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setFullScreenIntent(pendingIntent, true)
                .build();
        Toast.makeText(this, "Starting foreground", Toast.LENGTH_LONG).show();

        startForeground(69420, notification);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}