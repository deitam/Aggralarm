package com.example.aggralarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.Random;

public class AlarmActivity extends AppCompatActivity {

    SetAlarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.cancelAll();


        Bundle b = getIntent().getExtras();
        if(b != null) {
            int value = b.getInt("ALARM_EXTRA");
            alarm = AlarmDatabase.getAlarm(value);

            TextView text = findViewById(R.id.noneAlarmLabel);
            text.setText(alarm.label);
        }
    }

    public void OnDismissClicked(View view) {
        finish();
        System.exit(0);
    }
}