package org.graduation.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.graduation.healthylife.MainApplication;

/**
 * Created by javan on 2016/4/4.
 */
public class HealthyLifeDBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HealthyLife.db";
    private static final String CREATE_AUDIO = "create table audio ("
            + "start_time integer,"
            + "volume real)";
    private static final String CREATE_LIGHT = "create table light ("
            + "start_time integer,"
            + "volume real)";
    private static final String CREATE_APP_USAGE = "create table appUsage ("
            + "pkg_name text,"
            + "period integer"
            + "time integer,"
            + "eno integer)";
    private static final String CREATE_WIFI = "create table wifi ("
            + "start_time integer,"
            + "ssid text)";
    private static final String CREATE_GYRO_SENSOR = "create table gyro ("
            + "start_time integer,"
            + "steps integer,"
            + "x_axis float,"
            + "y_axis float,"
            + "z_axis float)";
    private static final String CREATE_EMOTION = "create table emotion ("
            + "eno integer,"
            + "emotion integer,"
            + "time integer)";

    public HealthyLifeDBHelper(){
        super(MainApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_AUDIO);
        db.execSQL(CREATE_LIGHT);
        db.execSQL(CREATE_APP_USAGE);
        db.execSQL(CREATE_WIFI);
        db.execSQL(CREATE_GYRO_SENSOR);
        db.execSQL(CREATE_EMOTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
