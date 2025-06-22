package com.example.remotepostservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetUuidTask extends AsyncTask<Void, Void, String> {
    private Activity mParentActivity;
    private ProgressDialog mDialog = null;
    private String mUrl = "https://strawberrypi.b-i.li/image";
    private OnUuidResultListener mListener;

    public interface OnUuidResultListener {
        void onResult(String result);
    }

    public GetUuidTask(Activity parentActivity, OnUuidResultListener listener) {
        this.mParentActivity = parentActivity;
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        mDialog = new ProgressDialog(mParentActivity);
        mDialog.setMessage("通信中…");
        mDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        return exec_get();
    }

    @Override
    protected void onPostExecute(String result) {
        mDialog.dismiss();
        if (mListener != null) {
            mListener.onResult(result); // ← 呼び出し元に返す！
        }
    }

    private String exec_get() {
        HttpURLConnection http = null;
        InputStream in = null;
        StringBuilder src = new StringBuilder();
        try {
            URL url = new URL(mUrl);
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.connect();
            in = http.getInputStream();
            byte[] line = new byte[1024];
            int size;
            while ((size = in.read(line)) > 0) {
                src.append(new String(line, 0, size)); // ← 不要なゴミ文字防止のため size を使う
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (http != null) http.disconnect();
                if (in != null) in.close();
            } catch (Exception ignored) {}
        }
        return src.toString();
    }
}
