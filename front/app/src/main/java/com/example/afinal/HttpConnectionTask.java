package com.example.afinal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;


public class HttpConnectionTask extends AsyncTask<Void, Void, String> {
    private Activity mParentActivity;
    private ProgressDialog mDialog = null;
    public String mUri;
    public String num;
    public String stat;

    public HttpConnectionTask(Activity parentActivity, String mode){
        this.mParentActivity = parentActivity;
        if(Objects.equals(mode, "start")){
            mUri = "http://IP/~pi/start.php?" + "mode=" + mode;
        } else if (Objects.equals(mode, "end")) {
            mUri = "http://IP/~pi/end.php?" + "mode=" + mode;
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
        mDialog.dismiss();
    }

    private String exec_get(){
        HttpURLConnection http = null;
        String src = "";
        try{
            URL url = new URL(mUri);
            Log.d("URL", String.valueOf(url));
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.connect();
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(http != null){
                    http.disconnect();
                }
            } catch (Exception ignored){
            }
        }
        return src;
    }


}
