package com.example.afinal;

import android.os.Handler;
import android.os.Looper;

public class TimeHandler {
    private Handler handler;
    private Runnable runnable;
    private long startTime;
    private UpdateListener updateListener;

    public interface UpdateListener {
        void onUpdate(String time);
    }

    public TimeHandler(UpdateListener listener) {
        this.updateListener = listener;
    }

    public void startTimer() {
        handler = new Handler(Looper.getMainLooper());
        startTime = System.currentTimeMillis(); // reset startTime
        runnable = new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;
                String time = formatTime(elapsedTime);
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
    }

    private String formatTime(long elapsedTime) {
        int seconds = (int) (elapsedTime / 1000);
        int minutes = seconds / 60;

        seconds %= 60;

        String time = String.format("%02d:%02d", minutes, seconds);
        return time;
    }

}
