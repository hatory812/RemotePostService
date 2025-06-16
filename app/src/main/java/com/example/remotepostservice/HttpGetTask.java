package com.example.remotepostservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetTask extends AsyncTask<Integer, Void, String> {
    private String responseUuidAndTime;
    private String image_uuid, image_time;
    private String reString;
    private Activity mParentActivity;
    private ProgressDialog mDialog = null;
    private String mUri;

    public HttpGetTask(Activity parentActivity, String string) {
        this.mParentActivity = parentActivity;
        this.reString = string;
    }

    @Override
    protected void onPreExecute(){
        mDialog = new ProgressDialog(mParentActivity);
        mDialog.setMessage("");
        mDialog.show();
    }

    @Override
    protected String doInBackground(Integer... params) {
        int taskId = params[0];
        if(taskId == 1){
            //サーバのデータを参照し画像を取得
            mUri = "https://api.abc.ef/image?amount=1/";
            responseUuidAndTime = exec_get(mUri);
            try {
                JSONObject root = new JSONObject(responseUuidAndTime);
                image_uuid = root.getString("uuid");
                image_time = root.getString("timestamp");
            } catch (Exception e) {
                e.printStackTrace();
            }

            mUri = "https://api.abc.ef/image/" + image_uuid;
            return exec_get(mUri);
        }

        else if(taskId == 11){
            //撮影し直し、画像を取得
            mUri = "https://api.abc.ef/image?amount=1/";
            responseUuidAndTime = exec_get(mUri);
            try {
                JSONObject root = new JSONObject(responseUuidAndTime);
                image_uuid = root.getString("uuid");
                image_time = root.getString("timestamp");
            } catch (Exception e) {
                e.printStackTrace();
            }

            mUri = "https://api.abc.ef/image/" + image_uuid;
            return exec_get(mUri);
        }

        else if(taskId == 12){
            //音量を調節する
            mUri = "";
            return exec_get(mUri);
        }

        else if(taskId == 13){
            //カラスを撃退する
            mUri = "";
            return exec_get(mUri);
        }
        return "error";
    }

    @Override
    protected void onPostExecute(String string) {
        mDialog.dismiss();
        this.reString = string;
    }

    private String exec_get(String uri) {
        HttpURLConnection http = null;
        InputStream in = null;
        String src = "";
        System.out.println("check0: " + uri);
        try {
            URL url = new URL(uri);
            System.out.println("check1: " + url);
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.connect();
            in = http.getInputStream();
            byte[] line = new byte[1024];
            int size;
            while (true) {
                size = in.read();
                if (size <= 0) {
                    break;
                }
                src += new String(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (http != null) {
                    http.disconnect();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ignored) {

            }
        }
        System.out.println("check2: " + src);
        return src;
    }

}
