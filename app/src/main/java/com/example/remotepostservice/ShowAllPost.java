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

public class ShowAllPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_all_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonToUpdatePostActivity = (Button) findViewById(R.id.button_return_dashboard_from_all_post_1);
        buttonToUpdatePostActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
                Intent intent1 = new Intent(getApplicationContext(), ShowDashborad.class);
                startActivity(intent1);}
        });

//        Button buttonToSetVolumeActivity = (Button) findViewById(R.id.button_return_dashboard_from_all_post_2);
//        buttonToSetVolumeActivity.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View arg0) {
//                Intent intent2 = new Intent(getApplicationContext(), ShowDashborad.class);
//                startActivity(intent2);}
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        LinearLayout container = findViewById(R.id.layout_post_container);
        container.removeAllViews(); // 再表示時にリセット

        new GetUuidTask(this, new GetUuidTask.OnUuidResultListener() {
            @Override
            public void onResult(String result) {
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String uuid = obj.getString("uuid");
                        long timestamp = obj.getLong("timestamp");

                        // 日付フォーマット変換
                        Date date = new Date(timestamp * 1000L);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                        String formattedDate = sdf.format(date);

                        // TextView作成
                        TextView textView = new TextView(ShowAllPost.this);
                        textView.setText("投稿日時: " + formattedDate);
                        textView.setPadding(0, 16, 0, 8);
                        textView.setTextSize(16);

                        // ImageView作成（取得後にsetImageBitmap）
                        ImageView imageView = new ImageView(ShowAllPost.this);
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                600
                        ));
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        imageView.setPadding(0, 0, 0, 32);

                        // 非同期で画像取得してImageViewに反映
                        new GetImageFromUuidTask(ShowAllPost.this, new GetImageFromUuidTask.OnImageResultListener() {
                            @Override
                            public void onResult(Bitmap bitmap) {
                                imageView.setImageBitmap(bitmap);
                            }
                        }).execute(uuid);

                        // LinearLayoutに追加
                        container.addView(textView);
                        container.addView(imageView);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).execute();

    }
}