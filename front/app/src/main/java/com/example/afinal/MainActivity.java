package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TimeHandler.UpdateListener{
    private TextView textView;
    private Button button;
    private TimeHandler timeHandler;

    private EditText minutesEditText;
    private EditText secondsEditText;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Activity mainActivity = this;

        minutesEditText = findViewById(R.id.minutesEditText);
        secondsEditText = findViewById(R.id.secondsEditText);
        button = (Button) findViewById(R.id.button);
        timeHandler = new TimeHandler(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(button.getText()).equals("START")){
                    button.setText("END");
                    HttpConnectionTask task = new HttpConnectionTask(mainActivity, "start");
                    task.execute();
                    timeHandler.startTimer();
                } else if (String.valueOf(button.getText()).equals("END")) {
                    button.setText("START");
                    HttpConnectionTask task = new HttpConnectionTask(mainActivity, "end");
                    task.execute();
                    timeHandler.stopTimer();
                    minutesEditText.setText("00");
                    secondsEditText.setText("00");
                }
            }
        });

    }

    @Override
    public void onUpdate(String[] time) {
        minutesEditText.setText(time[0]);
        secondsEditText.setText(time[1]);
    }
}