package com.example.acer.projectnotification.activityPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.acer.projectnotification.R;

public class DetailObatActivity extends AppCompatActivity {
    private WebView wv_detail_obat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_obat);
        getSupportActionBar().setTitle("Informasi Obat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        wv_detail_obat = findViewById(R.id.wv_detail_obat);

        //get intent
        Intent intent = getIntent();
        Long itemId = intent.getLongExtra("id_obat",99);

        String htmlObat ="";

        if(itemId==0){
            htmlObat = "obat_1.html";
        }else if(itemId==1){
            htmlObat = "obat_2.html";
        }else if(itemId==2){
            htmlObat = "obat_3.html";
        }else if(itemId==3){
            htmlObat = "obat_insulin.html";
        }

        wv_detail_obat.loadUrl("file:///android_res/raw/"+htmlObat);
    }
}
