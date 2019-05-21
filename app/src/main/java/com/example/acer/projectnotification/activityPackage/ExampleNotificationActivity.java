package com.example.acer.projectnotification.activityPackage;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.acer.projectnotification.R;

public class ExampleNotificationActivity extends AppCompatActivity {

    //deklarasi//
    public static int notifikasi = 1;

    private Button btSet;
    private EditText etJudul, etIsi;
    //---end---//

    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_notificaton);

        //inisialisasi komponen
        btSet = findViewById(R.id.btn_set);
        etJudul = findViewById(R.id.tv_judul);
        etIsi = findViewById(R.id.tv_isi);

        //set aksi ketika tombol di klik
        btSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExampleNotificationActivity.class);

                tampilNotif(etJudul.getText().toString(), etIsi.getText().toString(), intent);
            }
        });


        //setting channel notifikasi untuk api 27 keatas
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //id channel
        String id = "my_channel_01";
        // The user-visible name of the channel.
        CharSequence name = getString(R.string.channel_name);
        // The user-visible description of the channel.
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_LOW;

        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(id, name, importance);

            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});

            notificationManager.createNotificationChannel(mChannel);
        }
    }

    private void tampilNotif(String t, String i, Intent intent){
        //membuat komponen pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(ExampleNotificationActivity.this, notifikasi, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //membuat komponen notifikasi
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        Notification notification;
        notification = builder.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(t)
                .setLargeIcon(BitmapFactory.decodeResource(ExampleNotificationActivity.this.getResources(), R.mipmap.ic_launcher))
                .setContentText(i)
                .setChannelId("my_channel_01")
                .build();

        //notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(notifikasi,notification);
    }
}
