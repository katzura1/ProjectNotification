package com.example.acer.projectnotification.activityPackage;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.acer.projectnotification.R;
import com.example.acer.projectnotification.classPackage.AlarmReceiver;
import com.example.acer.projectnotification.classPackage.AlarmSoundService;
import com.example.acer.projectnotification.dbPackage.HistoryPengingat;
import com.example.acer.projectnotification.dbPackage.Pengingat;
import com.example.acer.projectnotification.dbPackage.PengingatDB;

import java.util.Calendar;

public class DialogActivity extends Activity {
    private PengingatDB pengingatDB;
    private Pengingat pengingat;
    private int pengingatID;

    private String obat1;
    private String obat2;
    private String mTime;
    private String mDate;
    private Calendar mCalendar;
    private int mYear, mMonth, mHour, mMinute, mDay;

    private EditText etObat1, etObat2;
    private Button btnSelesai;
    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        pengingatDB = new PengingatDB(this);

        pengingatID = Integer.parseInt(getIntent().getStringExtra(EditActivity.EXTRA_REMINDER_ID));

        pengingat = pengingatDB.getPenginat(pengingatID);

        etObat1 = findViewById(R.id.obat_makan_1);
        etObat2 = findViewById(R.id.obat_makan_2);
        btnSelesai = findViewById(R.id.button_selesai);

        obat1 = pengingat.getObat1();
        obat2 = pengingat.getObat2();

        etObat1.setText("1."+obat1);
        etObat2.setText("2."+(obat2.length()==0?"-":obat2));


//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.cancel(01);

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(DialogActivity.this, AlarmSoundService.class));
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(pengingatID);

                mCalendar = Calendar.getInstance();
                mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
                mMinute = mCalendar.get(Calendar.MINUTE);
                mYear = mCalendar.get(Calendar.YEAR);
                mMonth = mCalendar.get(Calendar.MONTH) + 1;
                mDay = mCalendar.get(Calendar.DATE);

                mDate = mDay + "/" + mMonth + "/" + mYear;

                if (mMinute < 10) {
                    mTime = mHour + ":" + "0" + mMinute;
                } else {
                    mTime = mHour + ":" + mMinute;
                }

                pengingatDB.addHistory(new HistoryPengingat(mDate, mTime, obat1, obat2));

                DialogActivity.this.finish();
            }
        });
    }
}
