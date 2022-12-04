package com.example.aggralarm;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

public class ShakeAlarmActivity extends AppCompatActivity {

    SetAlarm alarm;
    int shake_count;

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_shake);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.cancelAll();


        Bundle b = getIntent().getExtras();
        if(b != null) {
            int value = b.getInt("ALARM_EXTRA");
            alarm = AlarmDatabase.getAlarm(value);

            TextView text = findViewById(R.id.shakeAlarmLabel);
            text.setText(alarm.label);
        }

        shake_count = 0;

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                /*
                 * The following method, "handleShakeEvent(count):" is a stub //
                 * method you would use to setup whatever you want done once the
                 * device has been shook.
                 */
                handleShakeEvent(count);
            }
        });
    }

    public void OnDismissClicked(View view) {
        if (shake_count >= 100) {
            finish();
            System.exit(0);
        } else {
            final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
            TextView problemText = findViewById(R.id.shakeAlarmCommand);
            problemText.startAnimation(animShake);
        }
    }

    public void handleShakeEvent(int count) {
        ProgressBar bar = findViewById(R.id.progressBar);
        shake_count += count;
        bar.setProgress(Math.min(100, shake_count));
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}