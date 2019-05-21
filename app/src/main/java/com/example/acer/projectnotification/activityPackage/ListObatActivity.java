package com.example.acer.projectnotification.activityPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.acer.projectnotification.R;

public class ListObatActivity extends AppCompatActivity {
    private ListView lvListObat;
    private String[] listNamaObat = {"GLIBENKLAMID","METFORMIN","GLIMEPIRIDE","INSULIN"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_obat);
        getSupportActionBar().setTitle("Edukasi Obat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        lvListObat = findViewById(R.id.lv_list_obat);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listNamaObat);
        lvListObat.setAdapter(arrayAdapter);
        lvListObat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long itemId = lvListObat.getItemIdAtPosition(position);
                Intent intent = new Intent(ListObatActivity.this, DetailObatActivity.class);
                intent.putExtra("id_obat",itemId);
                startActivity(intent);
            }
        });
    }
}
