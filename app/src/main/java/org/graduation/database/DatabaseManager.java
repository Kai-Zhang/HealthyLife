package org.graduation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by javan on 2016/3/8.
 */
public class DatabaseManager {
    public static final String appTypeDbName = "apps.db";
    private SQLiteDatabase db;
    private Context context;
    private static DatabaseManager self = null;

    public static DatabaseManager getDatabaseManager() {
        return self;
    }

    public static DatabaseManager initManager(Context applicationContext) {
        if (self == null) {
            self = new DatabaseManager(applicationContext);
        }
        return self;
    }
    private DatabaseManager() { }
    private DatabaseManager(Context applicationContext) {
        context = applicationContext;
        db = new HealthyLifeDBHelper(applicationContext).getWritableDatabase();
    }

    public String getAppTypeDbPath(){
        return context.getDatabasePath(appTypeDbName).toString();
    }
    public String getDBDirPath(){
        return context.getDatabasePath(appTypeDbName).getParent();
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

    public void saveEmotion(int emotionNo, int emotion) {
        ContentValues values = new ContentValues();
        values.put("eno", emotionNo);
        values.put("emotion", emotion);
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
