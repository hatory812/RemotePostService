package com.example.remotepostservice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ShowUpdateLatestPost extends AppCompatActivity {
    private String returnString;
    private ImageView returnImageView;
    private Bitmap returnBitmap;
    private String returnUuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_update_latest_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonReturnDashboardFromUpdate = (Button) findViewById(R.id.button_return_dashboard_from_update);
        returnImageView = (ImageView) findViewById(R.id.ImageView_latest_post_in_update);

        buttonReturnDashboardFromUpdate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
                Intent intent3 = new Intent(getApplicationContext(), ShowDashborad.class);
                startActivity(intent3);}
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("event", "take_photo");
            jsonObject.put("timestamp", System.currentTimeMillis() / 1000);

            // POSTが終わったあとに5秒待って画像取得を行う
            new PostTakePhotoTask(this, jsonObject) {
                @Override
                protected void onPostExecute(String string) {
                    super.onPostExecute(string);

                    // 5秒待機してから処理
                    new android.os.Handler().postDelayed(() -> {
                        // 最新UUID取得
                        new GetUuidTask(ShowUpdateLatestPost.this, new GetUuidTask.OnUuidResultListener() {
                            @Override
                            public void onResult(String result) {
                                try {
                                    JSONArray jsonArray = new JSONArray(result);
                                    JSONObject firstObject = jsonArray.getJSONObject(0);
                                    returnUuid = firstObject.getString("uuid");

                                    // UUID が取得できたので画像取得開始
                                    new GetImageFromUuidTask(ShowUpdateLatestPost.this, new GetImageFromUuidTask.OnImageResultListener() {
                                        @Override
                                        public void onResult(Bitmap bitmap) {
                                            returnImageView.setImageBitmap(bitmap);
                                        }
                                    }).execute(returnUuid);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).execute();
                    }, 5000); // 5000ミリ秒 = 5秒

                }
            }.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}