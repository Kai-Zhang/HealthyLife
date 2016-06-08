package org.graduation.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.graduation.healthylife.MainApplication;

/**
 * Created by javan on 2016/3/8.
 */
public class DatabaseManager {
    public static final String appTypeDbName = "apps.db";
    private SQLiteDatabase db;
    private static DatabaseManager self = new DatabaseManager();

    public static DatabaseManager getDatabaseManager() {
        return self;
    }

    private DatabaseManager() {
        db = new HealthyLifeDBHelper().getWritableDatabase();
    }

    public String getAppTypeDbPath(){
        return MainApplication.getContext().getDatabasePath(appTypeDbName).toString();
    }
    public String getDBDirPath(){
        return MainApplication.getContext().getDatabasePath(appTypeDbName).getParent();
    }

    public void saveAudio(long startTime, double volume) {
        ContentValues values = new ContentValues();
        values.put("start_time",startTime);
        values.put("volume",volume);
        db.insert("audio", null, values);
    }
    public Cursor queryAudio(long startTime, long endTime) {
        Cursor cursor = db.rawQuery("select * from audio where start_time>? and start_time<?",
                new String[]{ String.valueOf(startTime), String.valueOf(endTime) });
        return cursor;
    }

    public void saveLight(long startTime, double volume) {
        ContentValues values = new ContentValues();
        values.put("start_time",startTime);
        values.put("volume",volume);
        db.insert("light", null, values);
    }
    public Cursor queryLight(long startTime,long endTime) {
        Cursor cursor = db.rawQuery("select * from light where start_time>? and start_time<?",
                new String[]{ String.valueOf(startTime), String.valueOf(endTime) });
        return cursor;
    }

    public void saveAppUsage(String pkgName, long period, int emotionNo) {
        ContentValues values = new ContentValues();
        values.put("pkg_name", pkgName);
        values.put("period", period);
        values.put("time", System.currentTimeMillis());
        values.put("eno", emotionNo);
        db.insert("appUsage", null, values);
    }
    public Cursor queryAppUsage(long startTime, long endTime) {
        Cursor cursor = db.rawQuery("select * from appUsage where time>? and time<?",
                new String[]{ String.valueOf(startTime), String.valueOf(endTime) });
        return cursor;
    }
    public Cursor queryAppUsage(long eno) {
        Cursor cursor = db.rawQuery("select * from appUsage where eno=?",
                new String[]{ String.valueOf(eno) });
        return cursor;
    }

    public void saveAppUsage(String pkgName) {
        ContentValues values = new ContentValues();
        values.put("pkg_name", pkgName);
        values.put("time", System.currentTimeMillis());
        db.insert("app", null, values);
    }

    public void saveWifi(long startTime, String ssid) {
        ContentValues values = new ContentValues();
        values.put("start_time", startTime);
        values.put("ssid", ssid);
        db.insert("wifi", null, values);
    }

    public void saveGyroSensor(long startTime, int step, float xAxis, float yAxis, float zAxis) {
        ContentValues values = new ContentValues();
        values.put("start_time", startTime);
        values.put("steps", step);
        values.put("x_axis", xAxis);
        values.put("y_axis", yAxis);
        values.put("z_axis", zAxis);
        db.insert("gyro", null, values);
    }

    public void saveLocation(double altitude, double longitude, double latitude) {
        ContentValues values = new ContentValues();
        values.put("time", System.currentTimeMillis());
        values.put("altitude", altitude);
        values.put("longitude", longitude);
        values.put("latitude", latitude);
        db.insert("location", null, values);
    }

    public void saveEmotion(int emotionNo, int happiness, int sadness,
                            int anger, int surprise, int fear, int disgust) {
        ContentValues values = new ContentValues();
        values.put("eno", emotionNo);
        values.put("happiness", happiness);
        values.put("sadness", sadness);
        values.put("anger", anger);
        values.put("surprise", surprise);
        values.put("fear", fear);
        values.put("disgust", disgust);
        values.put("time", System.currentTimeMillis());
        db.insert("emotion", null, values);
    }
    public Cursor queryEmotion(long startTime, long endTime) {
        Cursor cursor = db.rawQuery("select * from emotion where time>? and time<?",
                new String[]{ String.valueOf(startTime), String.valueOf(endTime) });
        return cursor;
    }
    public Cursor queryEmotion(long eno) {
        Cursor cursor = db.rawQuery("select * from emotion where eno=?",
                new String[]{ String.valueOf(eno) });
        return cursor;
    }

}
