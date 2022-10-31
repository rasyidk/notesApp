package com.example.notesapp.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.notesapp.model.MainActivityModel;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db_notes";
    public static final String TABLE_USER = "tb_user";
    public static final String COL_USERNAME = "username";
    public static final String COL_ID_USER = "id_user";
    public static final String COL_PASSWORD = "password";
    public static final String COL_NAME = "name";
    public static final String TABLE_NOTES = "tb_notes";
    public static final String COL_ID_NOTES = "id_notes";
    public static final String COL_JUDUL = "judul";
    public static final String COL_ISI = "isi";
    public static final String COL_TANGGAL = "tanggal";



    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("create table " + TABLE_USER + " (" + COL_USERNAME + " TEXT PRIMARY KEY, " + COL_PASSWORD +
                " TEXT, " + COL_NAME + " TEXT)");

        db.execSQL("create table " + TABLE_NOTES + " (" + COL_ID_NOTES + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COL_USERNAME +" TEXT ," +
                COL_JUDUL + " TEXT, " + COL_ISI + " TEXT" + "," + COL_TANGGAL + " TEXT)");

        db.execSQL("insert into " + TABLE_USER + " values ('g','g','g');");
        db.execSQL("insert into " + TABLE_NOTES + " values (1,'g','judul','isi','tanggal');");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public boolean Register(String username, String password, String name) throws SQLException {

        @SuppressLint("Recycle") Cursor mCursor = db.rawQuery("INSERT INTO " + TABLE_USER + "(" + COL_USERNAME + ", " + COL_PASSWORD + ", " + COL_NAME + ") VALUES (?,?,?)", new String[]{username, password, name});
        if (mCursor != null) {
            return mCursor.getCount() > 0;
        }
        return false;
    }

    public boolean Login(String username, String password) throws SQLException {
        @SuppressLint("Recycle") Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?", new String[]{username, password});
        if (mCursor != null) {
            return mCursor.getCount() > 0;
        }
        return false;
    }


}
