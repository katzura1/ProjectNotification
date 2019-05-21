package com.example.acer.projectnotification.dbPackage;

public class Pengingat {
    private int mID;
    private String mTitle;
    private String mDate;
    private String mTime;
    private String mRepeatNo;
    private String mRepeatType;
    private String obat1;
    private String obat2;

    public Pengingat(int mID, String mTitle, String mDate, String mTime, String mRepeatNo, String mRepeatType, String obat1, String obat2) {
        this.mID = mID;
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mRepeatNo = mRepeatNo;
        this.mRepeatType = mRepeatType;
        this.obat1 = obat1;
        this.obat2 = obat2;
    }

    public Pengingat(String mTitle, String mDate, String mTime, String mRepeatNo, String mRepeatType, String obat1, String obat2) {
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mRepeatNo = mRepeatNo;
        this.mRepeatType = mRepeatType;
        this.obat1 = obat1;
        this.obat2 = obat2;
    }

    public Pengingat() {
    }

    public String getObat1() {
        return obat1;
    }

    public void setObat1(String obat1) {
        this.obat1 = obat1;
    }

    public String getObat2() {
        return obat2;
    }

    public void setObat2(String obat2) {
        this.obat2 = obat2;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmRepeatNo() {
        return mRepeatNo;
    }

    public void setmRepeatNo(String mRepeatNo) {
        this.mRepeatNo = mRepeatNo;
    }

    public String getmRepeatType() {
        return mRepeatType;
    }

    public void setmRepeatType(String mRepeatType) {
        this.mRepeatType = mRepeatType;
    }
}
