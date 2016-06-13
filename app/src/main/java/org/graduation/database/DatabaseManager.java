package org.graduation.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by javan on 2016/3/8.
 */
public class DatabaseManager {
    private SQLiteDatabase db;
    private static DatabaseManager self = new DatabaseManager();

    public static DatabaseManager getDatabaseManager() {
        return self;
    }

    private DatabaseManager() {
        db = new HealthyLifeDBHelper().getWritableDatabase();
    }


    public SQLiteDatabase getDatabase(){
        return db;
    }
    public void refresh(){
        db.execSQL("delete from audio");
        db.execSQL("delete from light");
        db.execSQL("delete from appusage");
        db.execSQL("delete from app");
        db.execSQL("delete from wifi");
        db.execSQL("delete from acceleration");
        db.execSQL("delete from location");
        db.execSQL("delete from magnetic");
        db.execSQL("delete from gyroscope");
        db.execSQL("delete from contacts");
        db.execSQL("delete from calls");
        db.execSQL("delete from sms");
        db.execSQL("delete from screen");
    }
    public void saveAudio(long startTime, double volume) {
        ContentValues values = new ContentValues();
        values.put("start_time",startTime);
        values.put("volume",volume);
        db.insert("audio", null, values);
    }


    public void saveLight(long startTime, double volume) {
        ContentValues values = new ContentValues();
        values.put("start_time",startTime);
        values.put("volume",volume);
        db.insert("light", null, values);
    }
    public Cursor queryEmotion(){
        return db.rawQuery("select * from emotion",null);
    }
    public void saveAppUsage(String pkgName, long period, int emotionNo) {
        ContentValues values = new ContentValues();
        values.put("pkg_name", pkgName);
        values.put("period", period);
        values.put("time", System.currentTimeMillis());
        values.put("eno", emotionNo);
        db.insert("appUsage", null, values);
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

    public void saveAcc(long startTime, int step, float xAxis, float yAxis, float zAxis) {
        ContentValues values = new ContentValues();
        values.put("start_time", startTime);
        values.put("steps", step);
        values.put("x_axis", xAxis);
        values.put("y_axis", yAxis);
        values.put("z_axis", zAxis);
        db.insert("acceleration", null, values);
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
        db.insert("sms", null, values);
    }
    public void saveScreen(int state){
        ContentValues values = new ContentValues();
        values.put("time", System.currentTimeMillis());
        values.put("state",state);
        db.insert("screen", null, values);
    }


}
