package com.example.aggralarm;

import static com.example.aggralarm.App.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.Objects;

public class AlarmReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    // implement onReceive() method
    public void onReceive(Context context, Intent intent) {
        int value = Integer.parseInt(intent.getAction());
        SetAlarm alarm = AlarmDatabase.getAlarm(value);
        boolean recurring = alarm.monday || alarm.tuesday || alarm.wednesday || alarm.thursday || alarm.friday || alarm.saturday || alarm.sunday;

        if (!recurring) {
            alarm.enabled = false;
            AlarmDatabase.updateAlarm(alarm);
        } else {
            if (!alarmIsToday(alarm)) return;
        }

        // we will use vibrator first
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(4000);


        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        // setting default ringtone
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);

        // play ringtone
        ringtone.play();
        Toast.makeText(context, "Making intent", Toast.LENGTH_LONG).show();
        Intent i;
        if (Objects.equals(alarm.quest, "SHAKE")) {
            i = new Intent(context, ShakeAlarmActivity.class);
        } else if (Objects.equals(alarm.quest, "MATH")) {
            i = new Intent(context, MathAlarmActivity.class);
        } else {
            i = new Intent(context, AlarmActivity.class);
        }
        Toast.makeText(context, String.format("Selected quest -  %s", alarm.quest), Toast.LENGTH_SHORT).show();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Bundle b = new Bundle();
        b.putInt("ALARM_EXTRA", alarm.id); //Your id
        i.putExtras(b);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 5, i, PendingIntent.FLAG_MUTABLE);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Aggralarm")
                .setContentText(alarm.label)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(pendingIntent)
                .setFullScreenIntent(pendingIntent, true)
                .setAutoCancel(true)
                .setOngoing(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(69420, notification);

//        startForeground(69420, notification);


//        Intent intentService = new Intent(context, AlarmService.class);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            Toast.makeText(context, "Opening foreground service", Toast.LENGTH_LONG).show();
//            context.startForegroundService(intentService);
//        } else {
//            Toast.makeText(context, "Opening service", Toast.LENGTH_LONG).show();
//            context.startService(intentService);
//        }

    }

    public boolean alarmIsToday(SetAlarm alarm) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = calendar.get(Calendar.DAY_OF_WEEK);

        switch(today) {
            case Calendar.MONDAY:
                if (alarm.monday)
                    return true;
                return false;
            case Calendar.TUESDAY:
                if (alarm.tuesday)
                    return true;
                return false;
            case Calendar.WEDNESDAY:
                if (alarm.wednesday)
                    return true;
                return false;
            case Calendar.THURSDAY:
                if (alarm.thursday)
                    return true;
                return false;
            case Calendar.FRIDAY:
                if (alarm.friday)
                    return true;
                return false;
            case Calendar.SATURDAY:
                if (alarm.saturday)
                    return true;
                return false;
            case Calendar.SUNDAY:
                if (alarm.sunday)
                    return true;
                return false;
        }
        return false;
    }
}
