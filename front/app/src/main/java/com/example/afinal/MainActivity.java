package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Activity mainActivity = this;

        TextView textView = (TextView) findViewById(R.id.timer);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(button.getText()).equals("START")){
                    button.setText("END");
                    HttpConnectionTask task = new HttpConnectionTask(mainActivity, "start");
                    task.execute();
                } else if (String.valueOf(button.getText()).equals("END")) {
                    button.setText("START");
                    HttpConnectionTask task = new HttpConnectionTask(mainActivity, "end");
                    task.execute();
                }
            }
        });
    }
}