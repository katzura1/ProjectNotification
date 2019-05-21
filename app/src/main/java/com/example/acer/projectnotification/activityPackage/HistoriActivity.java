package com.example.acer.projectnotification.activityPackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.acer.projectnotification.R;
import com.example.acer.projectnotification.classPackage.CustomListAdapter;
import com.example.acer.projectnotification.dbPackage.HistoryPengingat;
import com.example.acer.projectnotification.dbPackage.PengingatDB;

import java.util.ArrayList;

public class HistoriActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PengingatDB rb = new PengingatDB(this);

        ArrayList<HistoryPengingat> historyPengingats = rb.getAllHistory();
        final ListView listView = findViewById(R.id.listView_History);
        listView.setAdapter(new CustomListAdapter(this,historyPengingats));
    }
}
