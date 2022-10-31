package com.example.notesapp.model;

import java.io.Serializable;

public class MainActivityModel implements Serializable {

//    private String mIdBook;
//    private String mTanggal;
//    private String mRiwayat;
//    private String mTotal;
//    private int mImageResourceId;
//    private static final int NO_IMAGE_PROVIDED = -1;
//
//    public MainActivityModel(String idBook, String tanggal, String riwayat, String total, int imageResourceId) {
//        mIdBook = idBook;
//        mTanggal = tanggal;
//        mRiwayat = riwayat;
//        mTotal = total;
//        mImageResourceId = imageResourceId;
//    }
//
//    public String getIdBook() {
//        return mIdBook;
//    }
//
//    public String getTanggal() {
//        return mTanggal;
//    }
//
//    public String getRiwayat() {
//        return mRiwayat;
//    }
//
//    public String getTotal() {
//        return mTotal;
//    }
//
//    public int getImageResourceId() {
//        return mImageResourceId;
//    }
//
//    public boolean hasImage() {
//        return mImageResourceId != NO_IMAGE_PROVIDED;
//    }

    private String mid_notes;
    private String musername;
    private String mjudul;
    private String misi;
    private String mtanggal;

    public MainActivityModel(String mid_notes, String musername, String mjudul, String misi, String mtanggal) {
        this.mid_notes = mid_notes;
        this.musername = musername;
        this.mjudul = mjudul;
        this.misi = misi;
        this.mtanggal = mtanggal;
    }



    public String getMid_notes() {
        return mid_notes;
    }

    public String getMusername() {
        return musername;
    }

    public String getMjudul() {
        return mjudul;
    }

    public String getMisi() {
        return misi;
    }

    public String getMtanggal() {
        return mtanggal;
    }

    public void setMid_notes(String mid_notes) {
        this.mid_notes = mid_notes;
    }

    public void setMusername(String musername) {
        this.musername = musername;
    }

    public void setMjudul(String mjudul) {
        this.mjudul = mjudul;
    }

    public void setMisi(String misi) {
        this.misi = misi;
    }

    public void setMtanggal(String mtanggal) {
        this.mtanggal = mtanggal;
    }
}