package com.example.acer.projectnotification.activityPackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.acer.projectnotification.R;

public class DetailEdukasiActivity extends AppCompatActivity {

    private WebView wvAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_edukasi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        wvAbout = findViewById(R.id.wv_about);
        long itemId = getIntent().getLongExtra("id_item",99);
        if(itemId==0){
            getSupportActionBar().setTitle("Penggunaan Alarm");
            wvAbout.loadUrl("file:///android_res/raw/menu_1.html");
        }else{
            getSupportActionBar().setTitle("Kegunaan Menu");
            wvAbout.loadUrl("file:///android_res/raw/about.html");
        }
    }
}
