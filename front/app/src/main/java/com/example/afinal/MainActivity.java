package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements TimeHandler.UpdateListener{
    private Button timerButton;
    private Button startAllButton;
    private Button startIndividualButton;
    private Button endIndividualButton;
    private Button startCommentButton;
    private Button endCommentButton;
    private Button endAllButton;
    private Button otherButton;
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
        timerButton = findViewById(R.id.timer_button);
        startAllButton = findViewById(R.id.start_all_button);
        startIndividualButton = findViewById(R.id.start_individual_button);
        endIndividualButton = findViewById(R.id.end_individual_button);
        startCommentButton = findViewById(R.id.start_comment_button);
        endCommentButton = findViewById(R.id.end_comment_button);
        endAllButton = findViewById(R.id.end_all_button);
        otherButton = findViewById(R.id.other_button);
        timeHandler = new TimeHandler(this);

        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(timerButton.getText()).equals("START")) {
                    c.state = "processing"; // 状態を処理中に変更
                    timerButton.setText("RESET");
                    String minutesString = minutesEditText.getText().toString();
                    String secondsString = secondsEditText.getText().toString();

                    int minutes = Integer.parseInt(minutesString);
                    int seconds = Integer.parseInt(secondsString);
                    timeHandler.startTimer(minutes, seconds);
                } else if (String.valueOf(timerButton.getText()).equals("RESET")) {
                    c.state = "standby"; // 状態をスタンバイに変更
                    c.monitor = "non"; // 音声モニターもなしに変更
                    stopTimer();
                }
            }
        });

        startAllButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu startAllMenu = new PopupMenu(MainActivity.this, v);

                startAllMenu.getMenuInflater().inflate(R.menu.start_all_menu, startAllMenu.getMenu());

                startAllMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.start_all_item1) {
                            HttpConnectionTask task = getHttpConnectionTask("start_overall", "0");
                            task.execute();
                            return true;
                        } else if (item.getItemId() == R.id.start_all_item2) {
                            HttpConnectionTask task = getHttpConnectionTask("start_overall", "1");
                            task.execute();
                            return true;
                        } else {
                            return false;
                        }
                    }
                });

                //popup menuを表示
                startAllMenu.show();
            }
        });

        startIndividualButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu startIndividualMenu = new PopupMenu(MainActivity.this, v);

                startIndividualMenu.getMenuInflater().inflate(R.menu.start_individual_menu, startIndividualMenu.getMenu());

                startIndividualMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.start_individual_item1) {
                            HttpConnectionTask task = getHttpConnectionTask("start_individual", "0");
                            task.execute();
                            return true;
                        } else if (item.getItemId() == R.id.start_individual_item2) {
                            HttpConnectionTask task = getHttpConnectionTask("start_individual", "1");
                            task.execute();
                            return true;
                        } else {
                            return false;
                        }
                    }
                });

                //popup menuを表示
                startIndividualMenu.show();
            }
        });

        endIndividualButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu endIndividualMenu = new PopupMenu(MainActivity.this, v);

                endIndividualMenu.getMenuInflater().inflate(R.menu.end_individual_menu, endIndividualMenu.getMenu());

                endIndividualMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.end_individual_item1) {
                            HttpConnectionTask task = getHttpConnectionTask("end_individual", "0");
                            task.execute();
                            return true;
                        } else {
                            return false;
                        }
                    }
                });

                //popup menuを表示
                endIndividualMenu.show();
            }
        });

        startCommentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu startCommentMenu = new PopupMenu(MainActivity.this, v);

                startCommentMenu.getMenuInflater().inflate(R.menu.start_comment_menu, startCommentMenu.getMenu());

                startCommentMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.start_comment_item1) {
                            HttpConnectionTask task = getHttpConnectionTask("start_comment", "0");
                            task.execute();
                            return true;
                        } else if (item.getItemId() == R.id.start_comment_item2) {
                            HttpConnectionTask task = getHttpConnectionTask("start_comment", "1");
                            task.execute();
                            return true;
                        } else if (item.getItemId() == R.id.start_comment_item3) {
                            HttpConnectionTask task = getHttpConnectionTask("start_comment", "2");
                            task.execute();
                            return true;
                        } else {
                            return false;
                        }
                    }
                });

                //popup menuを表示
                startCommentMenu.show();
            }
        });

        endCommentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu endCommentMenu = new PopupMenu(MainActivity.this, v);

                endCommentMenu.getMenuInflater().inflate(R.menu.end_comment_menu, endCommentMenu.getMenu());

                endCommentMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.end_comment_item1) {
                            HttpConnectionTask task = getHttpConnectionTask("end_comment", "0");
                            task.execute();
                            return true;
                        } else {
                            return false;
                        }
                    }
                });

                //popup menuを表示
                endCommentMenu.show();
            }
        });

        endAllButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu endAllMenu = new PopupMenu(MainActivity.this, v);

                endAllMenu.getMenuInflater().inflate(R.menu.end_all_menu, endAllMenu.getMenu());

                endAllMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.end_all_item1) {
                            HttpConnectionTask task = getHttpConnectionTask("end_overall", "0");
                            task.execute();
                            return true;
                        } else if (item.getItemId() == R.id.end_all_item2) {
                            HttpConnectionTask task = getHttpConnectionTask("end_overall", "1");
                            task.execute();
                            return true;
                        } else {
                            return false;
                        }
                    }
                });

                //popup menuを表示
                endAllMenu.show();
            }
        });

        otherButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu otherMenu = new PopupMenu(MainActivity.this, v);

                otherMenu.getMenuInflater().inflate(R.menu.other_menu, otherMenu.getMenu());

                otherMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.other_item1) {
                            HttpConnectionTask task = getHttpConnectionTask("other", "0");
                            task.execute();
                            return true;
                        } else {
                            return false;
                        }
                    }
                });

                //popup menuを表示
                otherMenu.show();
            }
        });

    }

    @Override
    public void onUpdate(String[] time) {
        Log.d("check", c.state);
        Log.d("check", c.monitor);

        minutesEditText.setText(time[0]);
        secondsEditText.setText(time[1]);

        if (isTimerExpired) {
            // Set EditText text color to red
            minutesEditText.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            secondsEditText.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));

            if (Objects.equals(c.monitor, "non") && Objects.equals(c.state, "processing")) {
                c.monitor = "monitoring";
                // 時間切れで音声がモニターされていないなら，音声モニターを開始
                HttpConnectionTask task = getHttpConnectionTask("start-monitoring", "-1");
                task.execute();
            } else if(Objects.equals(c.monitor, "non") && Objects.equals(c.state, "end-monitoring")){
                c.state = "wait-reset";
                // 時間切れで音声モニターが終了していた場合，状態をスタンバイに変更し強制終了の音声を再生
                // issue: タイマーが止まらない
                HttpConnectionTask task = new HttpConnectionTask(mainActivity, "end_force", "0", c);
                task.execute();
            }
        }
    }

    private void stopTimer(){
        timerButton.setText("START");
        timeHandler.stopTimer();
        minutesEditText.setText("00");
        secondsEditText.setText("00");
        // Set EditText text color to default (black)
        minutesEditText.setTextColor(getResources().getColor(android.R.color.white));
        secondsEditText.setTextColor(getResources().getColor(android.R.color.white));
    }

    private HttpConnectionTask getHttpConnectionTask(String mode, String no){
        HttpConnectionTask task = new HttpConnectionTask(mainActivity, mode, no, c);
        return task;
    }
}