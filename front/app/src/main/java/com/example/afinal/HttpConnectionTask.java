package com.example.afinal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;


public class HttpConnectionTask extends AsyncTask<Void, Void, String> {
    private Activity mParentActivity;
    private Common c;
    private ProgressDialog mDialog = null;
    public String mUri;

    public HttpConnectionTask(Activity parentActivity, String mode, String no, Common c){
        this.mParentActivity = parentActivity;
        this.c = c;
        if (Objects.equals(mode, "start-monitor")){
            // モニター開始
            mUri = "http://192.168.32.32/~pi/voice_monitor.php";
        } else {
            // 音声の再生
            mUri = "http://192.168.32.32/~pi/voice_create.php?" + "mode=" + mode + "&" + "no=" + no;
        }
    }

    @Override
    protected void onPreExecute(){
        mDialog = new ProgressDialog(mParentActivity);
        mDialog.setMessage("");
        mDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids){
        return exec_get();
    }

    @Override
    protected void onPostExecute(String string){
        if (Objects.equals(c.monitor, "monitoring")){
            // モニターが終了したら，状態を変更
            c.monitor = "non";
            c.state = "end-monitoring";
        }
        mDialog.dismiss();
    }

    private String exec_get(){
        HttpURLConnection http = null;
        InputStream in = null;
        String src = "";

        Log.d("check", mUri);

        try {
            URL url = new URL(mUri);
            Log.d("url", String.valueOf(url));
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.connect();
            in = http.getInputStream();
            byte[] line = new byte[1024];
            int size;
            while(true){
                size = in.read(line);
                if (size <= 0){
                    break;
                }
                src += new String(line);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(http != null){
                    http.disconnect();
                }
                if(in != null){
                    in.close();
                }
            } catch (Exception ignored){
            }
        }

        return src;
    }
}