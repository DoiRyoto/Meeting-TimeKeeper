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

        c = (Common) getApplication(); // 状態管理のためのクラス

        minutesEditText = findViewById(R.id.minutesEditText);
        secondsEditText = findViewById(R.id.secondsEditText);
        button = findViewById(R.id.button);
        timeHandler = new TimeHandler(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 状態がスタンバイなら開始用の音声を再生
                if(Objects.equals(c.state, "standby") && String.valueOf(button.getText()).equals("START")){
                    c.state = "processing"; // 状態を処理中に変更
                    button.setText("END");
                    HttpConnectionTask task = new HttpConnectionTask(mainActivity, "start", c);
                    task.execute();
                    String minutesString = minutesEditText.getText().toString();
                    String secondsString = secondsEditText.getText().toString();

                    int minutes = Integer.parseInt(minutesString);
                    int seconds = Integer.parseInt(secondsString);
                    timeHandler.startTimer(minutes, seconds);
                } else if (String.valueOf(button.getText()).equals("END")) {
                    c.state = "standby"; // 状態をスタンバイに変更
                    c.monitor = "non"; // 音声モニターもなしに変更
                    if (isTimerExpired) {
                        // 手動で停止した際，時間切れだったら強制終了の音声を再生
                        stopTimer();
                        Log.d("mode", "end-force-manual");
                        HttpConnectionTask task = new HttpConnectionTask(mainActivity, "end-force", c);
                        task.execute();
                    } else {
                        // 手動で停止した際，時間切れでないなら終了の音声を再生
                        stopTimer();
                        Log.d("mode", "end-manual");
                        HttpConnectionTask task = new HttpConnectionTask(mainActivity, "end", c);
                        task.execute();
                    }
                }
            }
        });

    }

    @Override
    public void onUpdate(String[] time) {
        minutesEditText.setText(time[0]);
        secondsEditText.setText(time[1]);

        // Log.d("check", c.state);
        // Log.d("check", c.monitor);

        if (isTimerExpired) {
            // Set EditText text color to red
            minutesEditText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            secondsEditText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));

            if (Objects.equals(c.monitor, "non") && Objects.equals(c.state, "processing")) {
                // 時間切れで音声がモニターされていないなら，音声モニターを開始
                HttpConnectionTask task = new HttpConnectionTask(mainActivity, "start-monitor", c);
                task.execute();
            } else if(Objects.equals(c.monitor, "non") && Objects.equals(c.state, "end-monitoring")){
                // 時間切れで音声モニターが終了していた場合，状態をスタンバイに変更し強制終了の音声を再生
                // issue: タイマーが止まらない
                c.state = "standby";
                stopTimer();
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