package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;
    private CountDownTimer countDownTimer;
    private Common c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Activity mainActivity = this;
        Common c = (Common) getApplication();

        TextView textView = (TextView) findViewById(R.id.timer);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(button.getText()).equals("START")){
                    Log.d("ddd", c.state);
                    button.setText("END");
                    HttpConnectionTask task = new HttpConnectionTask(mainActivity, "start", c);
                    task.execute();
                } else if (String.valueOf(button.getText()).equals("END")) {
                    Log.d("ddd", c.state);
                    button.setText("START");
                    HttpConnectionTask task = new HttpConnectionTask(mainActivity, "end", c);
                    task.execute();
                }
            }
        });
    }
}