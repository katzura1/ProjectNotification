package com.example.acer.projectnotification.activityPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.acer.projectnotification.R;

public class MenuActivity extends AppCompatActivity implements  View.OnClickListener{
    private Button btnAlarm, btnInfo, btnEdukasi, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnAlarm = findViewById(R.id.button_alarm);
        btnInfo = findViewById(R.id.button_info);
        btnEdukasi = findViewById(R.id.button_edukasi);
        btnAbout = findViewById(R.id.button_about);

        btnAlarm.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        btnEdukasi.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_alarm:
                intent = new Intent(MenuActivity.this, MainActivity.class);
                break;
            case R.id.button_info:
                intent = new Intent(MenuActivity.this,EdukasiActivity.class);
                break;
            case R.id.button_edukasi:
                intent = new Intent(MenuActivity.this,ListObatActivity.class);
                break;
            case R.id.button_about:
                intent = new Intent(MenuActivity.this,AboutActivity.class);
                break;
        }

        startActivity(intent);
    }
}
