package com.example.remotepostservice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.remotepostservice.databinding.ActivityShowDashboradBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ShowDashborad extends AppCompatActivity {
    private String returnString;
    private ImageView returnImageView;
    private Bitmap returnBitmap;
    private String returnUuid;
    private TextView returnTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_dashborad);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        returnImageView = (ImageView) findViewById(R.id.ImageView_latest_post_in_dashboard);
        returnTimeStamp = (TextView) findViewById(R.id.TextView_latest_post_date);

        Button buttonToUpdatePostActivity = (Button) findViewById(R.id.button_to_update_post_activity);
        buttonToUpdatePostActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
                Intent intent1 = new Intent(getApplicationContext(), ShowUpdateLatestPost.class);
                startActivity(intent1);}
        });

//        Button buttonToSetVolumeActivity = (Button) findViewById(R.id.button_to_set_volume_activity);
//        buttonToSetVolumeActivity.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View arg0) {
//                Intent intent2 = new Intent(getApplicationContext(), SetVolume.class);
//                startActivity(intent2);}
//        });

        Button buttonToRepellingCrowsActivity = (Button) findViewById(R.id.button_to_repelling_crows_activity);
        buttonToRepellingCrowsActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
                Intent intent3 = new Intent(getApplicationContext(), RepellingCrows.class);
                startActivity(intent3);}
        });

        Button buttonToShowAllPostActivity = (Button) findViewById(R.id.button_to_show_all_post_activity);
        buttonToShowAllPostActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
                Intent intent4 = new Intent(getApplicationContext(), ShowAllPost.class);
                startActivity(intent4);}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetUuidTask(this, new GetUuidTask.OnUuidResultListener() {
            @Override
            public void onResult(String result) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject firstObject = jsonArray.getJSONObject(0);
                    returnUuid = firstObject.getString("uuid");
                    long timestamp = firstObject.getLong("timestamp");

                    Date date = new Date(timestamp * 1000L);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
                    String formattedDate = sdf.format(date);

                    returnTimeStamp.setText("最新の投稿 : " + formattedDate);

                    // UUID が取得できたので画像取得開始
                    new GetImageFromUuidTask(ShowDashborad.this, new GetImageFromUuidTask.OnImageResultListener() {
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
    }
}