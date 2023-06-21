package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements TimeHandler.UpdateListener{
    private Button button;
    private TimeHandler timeHandler;
    private Common c;
    private EditText minutesEditText;
    private EditText secondsEditText;
    public static boolean isTimerExpired = false;
    private Activity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        c = (Common) getApplication();

        minutesEditText = findViewById(R.id.minutesEditText);
        secondsEditText = findViewById(R.id.secondsEditText);
        button = findViewById(R.id.button);
        timeHandler = new TimeHandler(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.equals(c.state, "standby") && String.valueOf(button.getText()).equals("START")){
                    c.state = "processing";
                    button.setText("END");
                    HttpConnectionTask task = new HttpConnectionTask(mainActivity, "start", c);
                    task.execute();
                    String minutesString = minutesEditText.getText().toString();
                    String secondsString = secondsEditText.getText().toString();

                    int minutes = Integer.parseInt(minutesString);
                    int seconds = Integer.parseInt(secondsString);
                    timeHandler.startTimer(minutes, seconds);
                } else if (String.valueOf(button.getText()).equals("END")) {
                    c.state = "standby";
                    c.monitor = "non";
                    if (isTimerExpired) {
                        stopTimer();
                        Log.d("mode", "end-manual");
                        HttpConnectionTask task = new HttpConnectionTask(mainActivity, "end", c);
                        task.execute();
                    } else {
                        stopTimer();
                        Log.d("mode", "end-force-manual");
                        HttpConnectionTask task = new HttpConnectionTask(mainActivity, "end-force", c);
                        task.execute();
                    }
                }
            }
        });

    }

    @Override
    public void onUpdate(String[] time) {
        Log.d("check", String.valueOf(isTimerExpired));
        minutesEditText.setText(time[0]);
        secondsEditText.setText(time[1]);

        Log.d("check", c.state);
        Log.d("check", c.monitor);

        if (isTimerExpired) {
            // Set EditText text color to red
            minutesEditText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            secondsEditText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));

            if (Objects.equals(c.monitor, "non") && Objects.equals(c.state, "processing")) {
                HttpConnectionTask task = new HttpConnectionTask(mainActivity, "start-monitor", c);
                task.execute();
            } else if(Objects.equals(c.monitor, "non") && Objects.equals(c.state, "end-monitoring")){
                c.state = "standby";
                stopTimer();
                Log.d("mode", "end-force-auto");
                HttpConnectionTask task = new HttpConnectionTask(mainActivity, "end-force", c);
                task.execute();
            }
        }
    }

    private void stopTimer(){
        button.setText("START");
        timeHandler.stopTimer();
        minutesEditText.setText("00");
        secondsEditText.setText("00");
        // Set EditText text color to default (black)
        minutesEditText.setTextColor(getResources().getColor(android.R.color.black));
        secondsEditText.setTextColor(getResources().getColor(android.R.color.black));
    }
}