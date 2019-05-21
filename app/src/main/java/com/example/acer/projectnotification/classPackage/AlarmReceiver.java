/*
 * Copyright 2015 Blanyal D'Souza.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.example.acer.projectnotification.classPackage;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

import com.example.acer.projectnotification.activityPackage.DialogActivity;
import com.example.acer.projectnotification.activityPackage.EditActivity;
import com.example.acer.projectnotification.dbPackage.Pengingat;
import com.example.acer.projectnotification.dbPackage.PengingatDB;
import com.example.acer.projectnotification.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AlarmReceiver extends WakefulBroadcastReceiver {
    AlarmManager mAlarmManager;
    PendingIntent mPendingIntent;
    Pengingat mReceiverRemineder = new Pengingat();;
    private PengingatDB rb;

    @Override
    public void onReceive(Context context, Intent intent) {
        int mReceivedID = Integer.parseInt(intent.getStringExtra(EditActivity.EXTRA_REMINDER_ID));

        // Get notification title from Reminder Database
        PengingatDB rb = new PengingatDB(context);
        Pengingat reminder = rb.getPenginat(mReceivedID);
        String mTitle = reminder.getmTitle();

        //get current date and time
        Date date = new Date();
        String tglFormat = "dd/MM/yyyy";
        String jamFormat = "HH:mm";
        DateFormat timeFormat = new SimpleDateFormat(jamFormat);
        String formattedJam = timeFormat.format(date);
        DateFormat dayFormat = new SimpleDateFormat(tglFormat);
        String formattedTgl = dayFormat.format(date);
        mReceiverRemineder.setmDate(formattedTgl);
        mReceiverRemineder.setmTime(formattedJam);
        mReceiverRemineder.setmID(mReceivedID);

        rb = new PengingatDB(context);
        rb.UpdateReminderTime(mReceiverRemineder);
        // Create intent to open ReminderEditActivity on notification click
        Intent editIntent = new Intent(context, DialogActivity.class);
        editIntent.putExtra(EditActivity.EXTRA_REMINDER_ID, Integer.toString(mReceivedID));
        PendingIntent mClick = PendingIntent.getActivity(context, mReceivedID, editIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //id channel
        String id = "channel_medtime";
        // The user-visible name of the channel.
        CharSequence name = "Medtime Channel";
        // The user-visible description of the channel.
        String description = "Channel for App Med Time";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        //sound
        String filename = "android.resource://" + context.getPackageName() + "/raw/alarm_sound";
        Uri soundUri = Uri.parse(filename);

        // Create Notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_clock)
                .setContentTitle("Waktunya makan obat")
                .setTicker(mTitle)
                .setChannelId(id)
                .setContentText("Klik untuk melihat detail...")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(mClick)
                .setAutoCancel(false)
                .setOngoing(true)
                ;

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(id, name, importance);

            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{0, 0, 0, 0,0, 0, 0, 0, 0});

            nManager.createNotificationChannel(mChannel);
        }

        //Notification notification = mBuilder.build();
        //notification.flags = Notification.FLAG_INSISTENT;

        //Stop sound service to play sound for alarm
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, AlarmSoundService.class));
        } else {
            context.startService(new Intent(context, AlarmSoundService.class));
        }

        nManager.notify(mReceivedID, mBuilder.build());
    }

    public void setAlarm(Context context, Calendar calendar, int ID) {
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Put Reminder ID in Intent Extra
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EditActivity.EXTRA_REMINDER_ID, Integer.toString(ID));
        mPendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Calculate notification time
        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        long diffTime = calendar.getTimeInMillis() - currentTime;

        // Start alarm using notification time
        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + diffTime,
                mPendingIntent);

        // Restart alarm if device is rebooted
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public void setRepeatAlarm(Context context, Calendar calendar, int ID, long RepeatTime) {
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Put Reminder ID in Intent Extra
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EditActivity.EXTRA_REMINDER_ID, Integer.toString(ID));
        mPendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Calculate notification timein
        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        long diffTime = calendar.getTimeInMillis() - currentTime;

        // Start alarm using initial notification time and repeat interval time
        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + diffTime,
                RepeatTime , mPendingIntent);

        // Restart alarm if device is rebooted
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public void cancelAlarm(Context context, int ID) {
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Cancel Alarm using Reminder ID
        mPendingIntent = PendingIntent.getBroadcast(context, ID, new Intent(context, AlarmReceiver.class), 0);
        mAlarmManager.cancel(mPendingIntent);

        // Disable alarm
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}