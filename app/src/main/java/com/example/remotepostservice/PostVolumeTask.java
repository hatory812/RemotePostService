package com.example.remotepostservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostVolumeTask extends AsyncTask<Void, Void, String> {
    private String mString;
    private Activity mParentActivity;
    private ProgressDialog mDialog = null;
    private JSONObject mJsonData;

    // 実行するphp/URL
    private String mUrl = "https://starwberrypi.b-i.li/event";

    public PostVolumeTask(Activity parentActivity, JSONObject jsonData) {
        this.mParentActivity = parentActivity;
        this.mJsonData = jsonData;
    }

    // タスク開始時
    @Override
    protected void onPreExecute() {
        mDialog = new ProgressDialog(mParentActivity);
        mDialog.setMessage("通信中...");
        mDialog.show();
    }

    // メイン処理
    @Override
    protected String doInBackground(Void... voids) {
        return exec_get();
    }

    // タスク終了時
    @Override
    protected void onPostExecute(String string) {
        mDialog.dismiss();
        this.mString = string;
    }

    private String exec_get() {
        HttpURLConnection http = null;
        InputStream in = null;
        String src = "";
        try {
            URL url = new URL(mUrl);
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setDoOutput(true);
            http.setDoInput(true);

            try (OutputStream os = http.getOutputStream()) {
                os.write(mJsonData.toString().getBytes("UTF-8"));
                os.flush();
            }

            byte[] line = new byte[1024];
            int size;
            while (true) {
                size = in.read(line);
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
        return src;
    }
}