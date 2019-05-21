package com.example.acer.projectnotification.activityPackage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.projectnotification.R;
import com.example.acer.projectnotification.classPackage.AlarmReceiver;
import com.example.acer.projectnotification.dbPackage.Pengingat;
import com.example.acer.projectnotification.dbPackage.PengingatDB;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private Toolbar mToolbar;
    private TextView tvTanggal, tvJam, tvJangka, tvTipeJangka;
    private EditText etNama, etObat1, etObat2;
    private String mTitle;
    private String mTime;
    private String mDate;
    private String mRepeatNo;
    private String mRepeatType;
    private String obat1;
    private String obat2;
    private String[] mDateSplit;
    private String[] mTimeSplit;
    private int mReceivedID;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private long mRepeatTime;
    private Calendar mCalendar;
    private Pengingat mReceivedReminder;
    private PengingatDB rb;
    private AlarmReceiver mAlarmReceiver;

    // Constant Intent String
    public static final String EXTRA_REMINDER_ID = "Reminder_ID";

    // Values for orientation change
    private static final String KEY_TITLE = "title_key";
    private static final String KEY_TIME = "time_key";
    private static final String KEY_DATE = "date_key";
    private static final String KEY_REPEAT = "repeat_key";
    private static final String KEY_REPEAT_NO = "repeat_no_key";
    private static final String KEY_REPEAT_TYPE = "repeat_type_key";
    private static final String KEY_ACTIVE = "active_key";

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Initialize Views
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTanggal = findViewById(R.id.set_tanggal);
        tvJam = findViewById(R.id.set_jam);
        tvJangka = findViewById(R.id.set_jangka);
        etNama = findViewById(R.id.set_judul);
        etObat1 = findViewById(R.id.obat_1);
        etObat2 = findViewById(R.id.obat_2);
        tvTipeJangka = findViewById(R.id.set_tipe);

        //set toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.title_activity_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Get reminder id from intent
        mReceivedID = Integer.parseInt(getIntent().getStringExtra(EXTRA_REMINDER_ID));

        // Get reminder using reminder id
        rb = new PengingatDB(this);
        mReceivedReminder = rb.getPenginat(mReceivedID);

        // Get values from reminder
        mTitle = mReceivedReminder.getmTitle();
        mDate = mReceivedReminder.getmDate();
        mTime = mReceivedReminder.getmTime();
        mRepeatNo = mReceivedReminder.getmRepeatNo();
        mRepeatType = mReceivedReminder.getmRepeatType();
        obat1 = mReceivedReminder.getObat1();
        obat2 = mReceivedReminder.getObat2();

        // Setup TextViews using reminder values
        etNama.setText(mTitle);
        etObat1.setText(obat1);
        etObat2.setText(obat2);
        tvTanggal.setText(mDate);
        tvJam.setText(mTime);
        tvJangka.setText(mRepeatNo);
        tvTipeJangka.setText(mRepeatType);

        // Obtain Date and Time details
        mCalendar = Calendar.getInstance();
        mAlarmReceiver = new AlarmReceiver();

        mDateSplit = mDate.split("/");
        mTimeSplit = mTime.split(":");

        mDay = Integer.parseInt(mDateSplit[0]);
        mMonth = Integer.parseInt(mDateSplit[1]);
        mYear = Integer.parseInt(mDateSplit[2]);
        mHour = Integer.parseInt(mTimeSplit[0]);
        mMinute = Integer.parseInt(mTimeSplit[1]);
    }

    // On pressing the back button
    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    // Creating the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tambah_reminder, menu);
        return true;
    }

    // On clicking menu buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // On clicking the back arrow
            // Discard any changes
            case R.id.home:
                onBackPressed();
                return true;
            case R.id.action_cancel:
                onBackPressed();
                Toast.makeText(this, "Perubahan dibatalkan", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_save_reminder:
                if (etNama.getText().toString().length() == 0){
                    etNama.setError("Field Judul tidak boleh kosong!");
                }
                else if (etObat1.getText().toString().length() == 0) {
                    etObat1.setError("Field Obat tidak boleh kosong!!");
                }
                else {
                    mTitle = etNama.getText().toString();
                    obat1 = etObat1.getText().toString();
                    obat2 = etObat2.getText().toString();
                    updateReminder();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setJangka(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Masukkan Angka");

        // Create EditText box to input repeat number
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (input.getText().toString().length() == 0) {
                            mRepeatNo = Integer.toString(1);
                            tvJangka.setText(mRepeatNo);
                        } else {
                            mRepeatNo = input.getText().toString().trim();
                            tvJangka.setText(mRepeatNo);
                        }
                    }
                });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // do nothing
            }
        });
        alert.show();
    }

    public void setTipejangka(View view) {
        final String[] items = new String[3];

        items[0] = "Jam";
        items[1] = "Hari";
        items[2] = "Menit";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Tipe");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                mRepeatType = items[item];
                tvTipeJangka.setText(mRepeatType);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear ++;
        mDay = dayOfMonth;
        mMonth = monthOfYear;
        mYear = year;
        mDate = dayOfMonth + "/" + monthOfYear + "/" + year;
        tvTanggal.setText(mDate);
    }

    public void setDate(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMinDate(now);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    public void setJam(View view) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.setThemeDark(false);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    // Obtain time from time picker
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        if (minute < 10) {
            mTime = hourOfDay + ":" + "0" + minute;
        } else {
            mTime = hourOfDay + ":" + minute;
        }
        tvJam.setText(mTime);
    }

    //ubah pengingat
    public void updateReminder(){
        PengingatDB rb = new PengingatDB(this);

        //set new value
        mReceivedReminder.setmTitle(mTitle);
        mReceivedReminder.setObat1(obat1);
        mReceivedReminder.setObat2(obat2);
        mReceivedReminder.setmDate(mDate);
        mReceivedReminder.setmTime(mTime);
        mReceivedReminder.setmRepeatNo(mRepeatNo);
        mReceivedReminder.setmRepeatType(mRepeatType);

        //update reminder
        rb.updateReminder(mReceivedReminder);

        // Set up calender for creating the notification
        mCalendar.set(Calendar.MONTH, --mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);

        // Cancel existing notification of the reminder by using its ID
        mAlarmReceiver.cancelAlarm(getApplicationContext(), mReceivedID);

        // Check repeat type
        if (mRepeatType.equals("Jam")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
        } else if (mRepeatType.equals("Hari")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
        } else if (mRepeatType.equals("Menit")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
        }

        Toast.makeText(this, String.valueOf(mRepeatTime), Toast.LENGTH_SHORT).show();

        mAlarmReceiver.setRepeatAlarm(getApplicationContext(), mCalendar, mReceivedID, mRepeatTime);

        // Create toast to confirm update
        Toast.makeText(getApplicationContext(), "Edited",
                Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    public void setJudul(View view) {
        final String[] items = new String[3];

        items[0] = "Obat Pagi";
        items[1] = "Obat Siang";
        items[2] = "Obat Malam";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                mTitle = items[item];
                etNama.setText(mTitle);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
