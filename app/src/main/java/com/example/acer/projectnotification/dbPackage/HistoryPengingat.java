package com.example.acer.projectnotification.dbPackage;

public class HistoryPengingat {
    private String ID;
    private String mDate;
    private String mTime;
    private String obat1;
    private String obat2;

    public HistoryPengingat() {
    }

    public HistoryPengingat(String ID, String mDate, String mTime, String obat1, String obat2) {
        this.ID = ID;
        this.mDate = mDate;
        this.mTime = mTime;
        this.obat1 = obat1;
        this.obat2 = obat2;
    }

    public HistoryPengingat(String mDate, String mTime, String obat1, String obat2) {
        this.mDate = mDate;
        this.mTime = mTime;
        this.obat1 = obat1;
        this.obat2 = obat2;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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
}
