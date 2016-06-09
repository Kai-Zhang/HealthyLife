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
            + "period integer,"
            + "time integer,"
            + "eno integer)";
    private static final String CREATE_APP = "create table app ("
            + "pkg_name text,"
            + "time integer)";
    private static final String CREATE_WIFI = "create table wifi ("
            + "start_time integer,"
            +"bssid text,"
            +"rssi integer"
            + "ssid text)";
    private static final String CREATE_GYRO_SENSOR = "create table gyro ("
            + "start_time integer,"
            + "steps integer,"
            + "x_axis float,"
            + "y_axis float,"
            + "z_axis float)";
    private static final String CREATE_LOCATION = "create table location ("
            + "time integer,"
            + "altitude float,"
            + "longitude float,"
            + "latitude float)";
    private static final String CREATE_EMOTION = "create table emotion ("
            + "eno integer,"
            + "emotion integer,"
            + "time integer)";
    private static final String CREATE_MAGNETIC="create table magnetic ("
            +"time integer,"
            +"x_magnetic float,"
            +"y_magnetic float,"
            +"z_magnetic float)";
    private static final String CREATE_GYROSCOPE="create table gyroscope ("
            +"time integer,"
            +"x_gyroscope float,"
            +"y_gyroscope float,"
            +"z_gyroscope float)";
    private static final String CREATE_CONTACTS="create table contacts ("
            +"name integer,"//姓名的hash
            +"phonenum integer)";//号码的hash
    private static final String CREATE_CALLS="create table calls ("
            +"time integer,"//通话开始时间,单位ms
            +"phonenum integer,"//号码的hash
            +"type integer,"//类型,1 is incomming,2 is outgoing,3 is missed
            +"duration integer)";//通话时长，单位s(包括响铃时间)
    private static final String CREATE_SMS="create table sms ("
            +"time integer,"//时间的hash
            +"address integer,"//对方号码的hash
            +"type integer)";//1 is incomming,2 is outgoing
    public HealthyLifeDBHelper(){
        super(MainApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_AUDIO);
        db.execSQL(CREATE_LIGHT);
        // appUsage table used for Lollipop MR1 and above
        // while app used for older system
        db.execSQL(CREATE_APP_USAGE);
        db.execSQL(CREATE_APP);
        db.execSQL(CREATE_WIFI);
        db.execSQL(CREATE_GYRO_SENSOR);
        db.execSQL(CREATE_LOCATION);
        db.execSQL(CREATE_EMOTION);

        db.execSQL(CREATE_MAGNETIC);
        db.execSQL(CREATE_GYROSCOPE);
        db.execSQL(CREATE_CONTACTS);
        db.execSQL(CREATE_CALLS);
        db.execSQL(CREATE_SMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
