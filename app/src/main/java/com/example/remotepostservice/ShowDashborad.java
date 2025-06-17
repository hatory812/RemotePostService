package com.example.remotepostservice;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.remotepostservice.databinding.ActivityShowDashboradBinding;

public class ShowDashborad extends AppCompatActivity {
    private String returnString;

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

        Button buttonToUpdatePostActivity = (Button) findViewById(R.id.button_to_update_post_activity);
        buttonToUpdatePostActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
                Intent intent1 = new Intent(getApplicationContext(), ShowUpdateLatestPost.class);
                startActivity(intent1);}
        });

        Button buttonToSetVolumeActivity = (Button) findViewById(R.id.button_to_set_volume_activity);
        buttonToSetVolumeActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
                Intent intent2 = new Intent(getApplicationContext(), SetVolume.class);
                startActivity(intent2);}
        });

        Button buttonToRepellingCrowsActivity = (Button) findViewById(R.id.button_to_repelling_crows_activity);
        buttonToRepellingCrowsActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
                Intent intent3 = new Intent(getApplicationContext(), RepellingCrows.class);
                startActivity(intent3);}
        });
    }

    @Override
    protected void onResume() {
        //画像取得のリクエスト
//        HttpGetTask task = new HttpGetTask(this, returnString);
//        task.execute(1);

        //returnTextViewがレスポンス、

        super.onResume();
    }
}