package com.example.remotepostservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SetVolume extends AppCompatActivity {
    private String returnString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_volume);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonToRepellingCrowsActivity = (Button) findViewById(R.id.button_set_volume);
        buttonToRepellingCrowsActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
            //リクエスト12
//                HttpGetTask task = new HttpGetTask(SetVolume.this, returnString);
//                task.execute(12);
            }
        });

        Button buttonReturnDashboardFromVolume = (Button) findViewById(R.id.button_return_dashboard_from_volume);
        buttonReturnDashboardFromVolume.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {
                Intent intent3 = new Intent(getApplicationContext(), ShowDashborad.class);
                startActivity(intent3);
            }
        });
    }
}