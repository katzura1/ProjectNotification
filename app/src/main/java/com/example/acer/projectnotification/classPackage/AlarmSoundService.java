package com.example.acer.projectnotification.classPackage;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.acer.projectnotification.R;

import java.io.IOException;

import static android.media.AudioManager.STREAM_ALARM;

public class AlarmSoundService extends Service {
    private MediaPlayer mp;
    private Vibrator vibrator;
    private static final int NOTIF_ID = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        Notification notification = notificationBuilder.setOngoing(true)
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(0,notification);
        String filename = "android.resource://" + this.getPackageName() + "/raw/alarm_sound";
        AudioManager amanager = (AudioManager) this.getSystemService(AUDIO_SERVICE);
        int maxVolume = amanager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        amanager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume, 0);
        //Start media player
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_ALARM);
        mp.setVolume(100,100);
        mp.setLooping(true);
        try {
            mp.setDataSource(AlarmSoundService.this, Uri.parse(filename));
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
        vibrator = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
        long[] pattern = { 0, 100, 500, 100, 500, 100, 500, 100, 500, 100, 500};
        vibrator.vibrate(pattern , 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //On destory stop and release the media player
        if (mp != null && mp.isPlaying()) {
            mp.stop();
            mp.reset();
            mp.release();
            vibrator.cancel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_clock)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(98, notification);
    }
}
