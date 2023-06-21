package com.example.afinal;

import android.os.Handler;
import android.os.Looper;


public class TimeHandler {
    private Handler handler;
    private Runnable runnable;
    private long startTime;
    private UpdateListener updateListener;

    public interface UpdateListener {
        void onUpdate(String time[]);
    }

    public TimeHandler(UpdateListener listener) {
        this.updateListener = listener;
    }

    public void startTimer(int minutes, int seconds) {
        int timeInSeconds = minutes * 60 + seconds;
        int milliseconds = timeInSeconds * 1000;
        handler = new Handler(Looper.getMainLooper());
        startTime = System.currentTimeMillis(); // reset startTime
        runnable = new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;
                long remainingTime = milliseconds - elapsedTime;
                String[] time;
                if(remainingTime < 0) {
                    time = formatTime( - remainingTime);
                    MainActivity.isTimerExpired = true;
                } else {
                    time = formatTime(remainingTime);
                }
                if (updateListener != null) {
                    updateListener.onUpdate(time);
                }
                handler.postDelayed(this, 1000); // The task is performed again every 1000 milliseconds
            }
        };
        handler.post(runnable); // First time the task is performed


    }

    public void stopTimer() {
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }

        MainActivity.isTimerExpired = false;
    }

    private String[] formatTime(long elapsedTime) {
        int seconds = (int) (elapsedTime / 1000);
        int minutes = seconds / 60;

        seconds %= 60;

        String[] time = {String.valueOf(minutes), String.valueOf(seconds)};
        return time;
    }

}
