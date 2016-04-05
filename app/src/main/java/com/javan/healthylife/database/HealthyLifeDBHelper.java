package com.javan.healthylife.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.javan.healthylife.controller.HealthyApplication;

/**
 * Created by javan on 2016/4/4.
 */
public class HealthyLifeDBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HealthyLife.db";
    private static String CREATE_AUDIO="create table audio ("
            +"starttime integer,"
            +"volume real)";
    private static String CREATE_APP_USAGE="create table appUsage ("
            +"day integer,"
            +"pkgname text,"
            +"time integer)";
    public HealthyLifeDBHelper(){
        super(HealthyApplication.getContext(),DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_AUDIO);
        db.execSQL(CREATE_APP_USAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
