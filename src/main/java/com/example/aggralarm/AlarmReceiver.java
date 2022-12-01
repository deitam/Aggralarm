package com.example.aggralarm;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    // implement onReceive() method
    public void onReceive(Context context, Intent intent) {

        // we will use vibrator first
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(4000);

        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        // setting default ringtone
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);

        // play ringtone
        ringtone.play();


        //start activity
//        TODO doesnt work, start a full screen notification instead, or a notification that opens an activity

        Intent intentService = new Intent(context, AlarmService.class);
//        context.startForegroundService(intentService);
        Toast.makeText(context, "Opening service", Toast.LENGTH_LONG).show();

        context.startService(intentService);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            Toast.makeText(context, "Opening foreground service", Toast.LENGTH_LONG).show();
//            context.startForegroundService(intentService);
//        } else {
//            Toast.makeText(context, "Opening service", Toast.LENGTH_LONG).show();
//            context.startService(intentService);
        }

//        context.startActivity(i);
//    }
}