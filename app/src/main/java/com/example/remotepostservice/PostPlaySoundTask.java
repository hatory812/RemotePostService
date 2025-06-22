package com.example.remotepostservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostPlaySoundTask extends AsyncTask<Void, Void, String> {
    private Activity mParentActivity;
    private ProgressDialog mDialog = null;
    private JSONObject mJsonData;
    private String mUrl = "https://strawberrypi.b-i.li/event";

    public PostPlaySoundTask(Activity parentActivity, JSONObject jsonData) {
        this.mParentActivity = parentActivity;
        this.mJsonData = jsonData;
    }

    @Override
    protected void onPreExecute() {
        mDialog = new ProgressDialog(mParentActivity);
        mDialog.setMessage("通信中...");
        mDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        return exec_post();
    }

    @Override
    protected void onPostExecute(String response) {
        mDialog.dismiss();
        System.out.println("POST response: " + response);
    }

    private String exec_post() {
        HttpURLConnection http = null;
        InputStream in = null;
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(mUrl);
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setDoOutput(true);
            http.setDoInput(true);

            // JSONを送信
            try (OutputStream os = http.getOutputStream()) {
                os.write(mJsonData.toString().getBytes("UTF-8"));
                os.flush();
            }

            // レスポンス取得
            in = http.getInputStream(); // ✅ 必須
            byte[] buffer = new byte[1024];
            int size;
            while ((size = in.read(buffer)) != -1) {
                result.append(new String(buffer, 0, size));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (http != null) http.disconnect();
                if (in != null) in.close();
            } catch (Exception ignored) {}
        }

        return result.toString();
    }
}
