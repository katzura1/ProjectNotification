package com.example.acer.projectnotification.activityPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.acer.projectnotification.R;

public class EdukasiActivity extends AppCompatActivity {
    private WebView wv_penyakit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edukasi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        wv_penyakit = findViewById(R.id.wv_penyakit);
        wv_penyakit.loadUrl("file:///android_res/raw/penyakit.html");
    }
}
