package com.example.remotepostservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonToShowDashboard = (Button) findViewById(R.id.button_to_show_dashboard);
        buttonToShowDashboard.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
                //画像取得のリクエスト←ダッシュボードのresumeでいいのでは？
                Intent intent = new Intent(getApplicationContext(), ShowDashborad.class);
                startActivity(intent);}
        });
    }
}