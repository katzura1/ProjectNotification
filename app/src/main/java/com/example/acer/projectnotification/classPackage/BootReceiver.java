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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.acer.projectnotification.dbPackage.Pengingat;
import com.example.acer.projectnotification.dbPackage.PengingatDB;

import java.util.Calendar;
import java.util.List;


public class BootReceiver extends BroadcastReceiver {

    private String mTitle;
    private String mTime;
    private String mDate;
    private String mRepeatNo;
    private String mRepeatType;
    private String[] mDateSplit;
    private String[] mTimeSplit;
    private int mYear, mMonth, mHour, mMinute, mDay, mReceivedID;
    private long mRepeatTime;

    private Calendar mCalendar;
    private AlarmReceiver mAlarmReceiver;

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            PengingatDB rb = new PengingatDB(context);
            Calendar now = Calendar.getInstance();
            mCalendar = Calendar.getInstance();
            mAlarmReceiver = new AlarmReceiver();

            List<Pengingat> reminders = rb.getAllReminders();

            for (Pengingat rm : reminders) {
                mReceivedID = rm.getmID();
                mRepeatNo = rm.getmRepeatNo();
                mRepeatType = rm.getmRepeatType();
                mDate = rm.getmDate();
                mTime = rm.getmTime();

                mDateSplit = mDate.split("/");
                mTimeSplit = mTime.split(":");

                mDay = Integer.parseInt(mDateSplit[0]);
                mMonth = Integer.parseInt(mDateSplit[1]);
                mYear = Integer.parseInt(mDateSplit[2]);
                mHour = Integer.parseInt(mTimeSplit[0]);
                mMinute = Integer.parseInt(mTimeSplit[1]);

                mCalendar.set(Calendar.MONTH, --mMonth);
                mCalendar.set(Calendar.YEAR, mYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
                mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
                mCalendar.set(Calendar.MINUTE, mMinute);
                mCalendar.set(Calendar.SECOND, 0);

                if(mCalendar.getTimeInMillis()< now.getTimeInMillis()){
                    mCalendar.add(Calendar.DATE, 1);
                }
                // Cancel existing notification of the reminder by using its ID
                //mAlarmReceiver.cancelAlarm(context, mReceivedID);

                // Check repeat type
                if (mRepeatType.equals("Jam")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
                } else if (mRepeatType.equals("Hari")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * 24 * milHour;
                } else if (mRepeatType.equals("Menit")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
                }

                Toast.makeText(context, String.valueOf(mRepeatTime), Toast.LENGTH_SHORT).show();

                // Create a new notification
                mAlarmReceiver.setRepeatAlarm(context, mCalendar, mReceivedID, mRepeatTime);

                Toast.makeText(context, "Alarm loaded", Toast.LENGTH_SHORT).show();
                Log.d("BOOT RECEIVER","ALARM SUCCESS");
            }
        }
    }
}