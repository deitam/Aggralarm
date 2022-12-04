package com.example.aggralarm;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

public class MathAlarmActivity extends AppCompatActivity {

    SetAlarm alarm;
    int first;
    int second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_math);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.cancelAll();


        Bundle b = getIntent().getExtras();
        if(b != null) {
            int value = b.getInt("ALARM_EXTRA");
            alarm = AlarmDatabase.getAlarm(value);

            TextView text = findViewById(R.id.mathAlarmLabel);
            text.setText(alarm.label);
        }

        first = new Random().nextInt(50);
        second = new Random().nextInt(50);

        TextView problemText = findViewById(R.id.mathAlarmProblem);
        problemText.setText(String.format("%2d + %2d =", first, second));
    }

    // OnToggleClicked() method is implemented the time functionality
    public void OnDismissClicked(View view) {
        TextView result_text = findViewById(R.id.password_field);
        String result_text_str = result_text.getText().toString();
        int result;
        if (result_text_str.equals("")) {
            final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
            TextView problemText = findViewById(R.id.mathAlarmProblem);
            problemText.startAnimation(animShake);
            Toast.makeText(this, "Solve math problem to dismiss", Toast.LENGTH_SHORT).show();
            return;
        } else {
            result = Integer.parseInt(result_text.getText().toString());
        }

        if (result == first + second) {
            finish();
            System.exit(0);
        } else {
            final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
            TextView problemText = findViewById(R.id.mathAlarmProblem);
            problemText.startAnimation(animShake);
            Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show();
        }
    }
}