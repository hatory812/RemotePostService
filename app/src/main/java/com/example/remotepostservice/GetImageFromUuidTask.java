package com.example.remotepostservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;

import java.io.InputStream;

public class GetImageFromUuidTask extends AsyncTask<String, Void, Bitmap> {
    private Activity mParentActivity;
    private ProgressDialog mDialog = null;
    private String mUrl = "https://strawberrypi.b-i.li/image/";
    private OnImageResultListener mListener;

    // コールバックインターフェース
    public interface OnImageResultListener {
        void onResult(Bitmap bitmap);
    }

    public GetImageFromUuidTask(Activity parentActivity, OnImageResultListener listener) {
        this.mParentActivity = parentActivity;
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        mDialog = new ProgressDialog(mParentActivity);
        mDialog.setMessage("通信中...");
        mDialog.show();
    }

    @Override
    protected Bitmap doInBackground(String... uuid) {
        String fullUrl = mUrl + uuid[0];
        return fetchImage(fullUrl);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        mDialog.dismiss();
        if (mListener != null && result != null) {
            mListener.onResult(result);  // 呼び出し元に返す
        }
    }

    private Bitmap fetchImage(String urlString) {
        HttpURLConnection http = null;
        InputStream in = null;
        try {
            URL url = new URL(urlString);
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.connect();
            in = http.getInputStream();
            return BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (http != null) http.disconnect();
                if (in != null) in.close();
            } catch (Exception ignored) {}
        }
    }
}

