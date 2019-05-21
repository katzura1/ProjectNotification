package com.example.acer.projectnotification.dbPackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PengingatDB extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ReminderDatabase";

    // Table name
    private static final String TABLE_REMINDERS = "ReminderTable";
    private static final String TABLE_HISTORY = "HistoryTable";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_OBAT_1 = "obat_1";
    private static final String KEY_OBAT_2 = "obat_2";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_REPEAT_NO = "repeat_no";
    private static final String KEY_REPEAT_TYPE = "repeat_type";
    private static final String KEY_NAMA_OBAT = "nama_obat";

    public PengingatDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REMINDERS_TABLE =
                "CREATE TABLE " + TABLE_REMINDERS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_TIME + " INTEGER,"
                + KEY_REPEAT_NO + " INTEGER,"
                + KEY_REPEAT_TYPE + " TEXT,"
                        + KEY_OBAT_1 + " TEXT,"
                        + KEY_OBAT_2 + " TEXT"+ ")";

        String CREATE_HISTORY_TABLE =
                "CREATE TABLE " + TABLE_HISTORY + "("
                        + KEY_ID + " INTEGER PRIMARY KEY,"
                        + KEY_DATE + " TEXT,"
                        + KEY_TIME + " TEXT,"
                        + KEY_OBAT_1 + " TEXT,"
                        + KEY_OBAT_2 + " TEXT"
                        +")" ;

        //create table
        db.execSQL(CREATE_REMINDERS_TABLE);
        db.execSQL(CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        if (oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        // Create tables again
        onCreate(db);
    }

    // Adding new Reminder
    public int addReminder(Pengingat pengingat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE , pengingat.getmTitle());
        values.put(KEY_DATE , pengingat.getmDate());
        values.put(KEY_TIME , pengingat.getmTime());
        values.put(KEY_REPEAT_NO , pengingat.getmRepeatNo());
        values.put(KEY_REPEAT_TYPE, pengingat.getmRepeatType());
        values.put(KEY_OBAT_1 , pengingat.getObat1());
        values.put(KEY_OBAT_2 , pengingat.getObat2());

        // Inserting Row
        long ID = db.insert(TABLE_REMINDERS, null, values);
        db.close();
        return (int) ID;
    }

    public void addHistory(HistoryPengingat historyPengingat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(KEY_ID, historyPengingat.getID());
        values.put(KEY_DATE, historyPengingat.getmDate());
        values.put(KEY_TIME, historyPengingat.getmTime());
        values.put(KEY_OBAT_1 , historyPengingat.getObat1());
        values.put(KEY_OBAT_2 , historyPengingat.getObat2());

        // Inserting Row
        long ID = db.insert(TABLE_HISTORY, null, values);
        db.close();
    }

    //getting single reminder
    public Pengingat getPenginat(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_REMINDERS, new String[]
                {
                        KEY_ID,
                        KEY_TITLE,
                        KEY_DATE,
                        KEY_TIME,
                        KEY_REPEAT_NO,
                        KEY_REPEAT_TYPE,
                        KEY_OBAT_1,
                        KEY_OBAT_2
                },KEY_ID+"=?",
                new String[] {String.valueOf(id)},null,null,null);

        if(cursor!=null){
            cursor.moveToNext();
        }

        Pengingat pengingat = new Pengingat(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7)

        );
        return pengingat;
    }

    // Getting all Reminders
    public List<Pengingat> getAllReminders(){
        List<Pengingat> reminderList = new ArrayList<>();

        // Select all Query
        String selectQuery = "SELECT * FROM " + TABLE_REMINDERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                Pengingat reminder = new Pengingat();
                reminder.setmID(Integer.parseInt(cursor.getString(0)));
                reminder.setmTitle(cursor.getString(1));
                reminder.setmDate(cursor.getString(2));
                reminder.setmTime(cursor.getString(3));
                reminder.setmRepeatNo(cursor.getString(4));
                reminder.setmRepeatType(cursor.getString(5));

                // Adding Reminders to list
                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }
        return reminderList;
    }

    // Deleting single Reminder
    public void deleteReminder(Pengingat pengingat){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDERS, KEY_ID + "=?",
                new String[]{String.valueOf(pengingat.getmID())});
        db.close();
    }

    // Updating single Reminder
    public int updateReminder(Pengingat reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE , reminder.getmTitle());
        values.put(KEY_OBAT_1 , reminder.getObat1());
        values.put(KEY_OBAT_2 , reminder.getObat2());
        values.put(KEY_DATE , reminder.getmDate());
        values.put(KEY_TIME , reminder.getmTime());
        values.put(KEY_REPEAT_NO , reminder.getmRepeatNo());
        values.put(KEY_REPEAT_TYPE, reminder.getmRepeatType());

        // Updating row
        return db.update(TABLE_REMINDERS, values, KEY_ID + "=?",
                new String[]{String.valueOf(reminder.getmID())});
    }

    //Updating Date and Time
    //update tanggal sesuai alarm terakhir muncul
    public int UpdateReminderTime(Pengingat reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, reminder.getmDate());
        values.put(KEY_TIME, reminder.getmTime());

        //updateing
        return db.update(TABLE_REMINDERS, values, KEY_ID+"=?",
                new String[]{String.valueOf(reminder.getmID())});
    }

    // Getting all History
    public ArrayList<HistoryPengingat> getAllHistory(){
        ArrayList<HistoryPengingat> historyrList = new ArrayList<>();

        // Select all Query
        String selectQuery = "SELECT * FROM " + TABLE_HISTORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                HistoryPengingat reminder = new HistoryPengingat();
                reminder.setmDate(cursor.getString(1));
                reminder.setmTime(cursor.getString(2));
                reminder.setObat1(cursor.getString(3));
                reminder.setObat2(cursor.getString(4));

                // Adding Reminders to list
                historyrList.add(reminder);
            } while (cursor.moveToNext());
        }
        return historyrList;
    }
}
