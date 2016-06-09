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

    public void saveWifi(long startTime, String ssid,String bssid,int rssi) {
        ContentValues values = new ContentValues();
        values.put("start_time", startTime);
        values.put("ssid", ssid);
        values.put("bssid",bssid);
        values.put("rssi",rssi);
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

    public void saveEmotion(int emotionNo, int emotion) {
        ContentValues values = new ContentValues();
        values.put("eno", emotionNo);
        values.put("emotion", emotion);
        values.put("time", System.currentTimeMillis());
        db.insert("emotion", null, values);
    }
    public void saveMagnetic(float[] magnetic){
        ContentValues values = new ContentValues();
        values.put("x_magnetic",magnetic[0]);
        values.put("y_magnetic",magnetic[1]);
        values.put("z_magnetic",magnetic[2]);
        values.put("time", System.currentTimeMillis());
        db.insert("magnetic", null, values);
    }
    public void saveGyroscope(float[] magnetic){
        ContentValues values = new ContentValues();
        values.put("x_gyroscope",magnetic[0]);
        values.put("y_gyroscope",magnetic[1]);
        values.put("z_gyroscope",magnetic[2]);
        values.put("time", System.currentTimeMillis());
        db.insert("gyroscope", null, values);
    }
    public void saveContacts(long name,long phonenum){
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("phonenum",phonenum);
        db.insert("contacts", null, values);
    }
    public void saveCalls(long time,long phonenum,int type,int duration){
        ContentValues values = new ContentValues();
        values.put("time",time);
        values.put("phonenum",phonenum);
        values.put("type",type);
        values.put("duration", duration);
        db.insert("calls", null, values);
    }
    public void saveSms(long time,long address,int type){
        ContentValues values = new ContentValues();
        values.put("time",time);
        values.put("address",address);
        values.put("type",type);
        db.insert("type", null, values);
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
