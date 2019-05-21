package com.example.acer.projectnotification.activityPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.acer.projectnotification.R;

public class AboutActivity extends AppCompatActivity {

    private ListView lvMenuAbout;
    private String[] arrMenuAbout = {"Cara Penggunaan Alarm", "Kegunaan Tiap Menu"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle("Tentang");

        lvMenuAbout = findViewById(R.id.lv_menu_about);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrMenuAbout);
        lvMenuAbout.setAdapter(arrayAdapter);
        lvMenuAbout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long itemId = lvMenuAbout.getItemIdAtPosition(position);
                Intent intent = new Intent(AboutActivity.this, DetailEdukasiActivity.class);
                intent.putExtra("id_item",itemId);
                startActivity(intent);
            }
        });

    }
}
