package com.javan.healthylife.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.javan.healthylife.controller.DateManager;
import com.javan.healthylife.controller.HealthyApplication;

import java.util.List;

/**
 * Created by javan on 2016/3/8.
 */
public class DatabaseManager {
    public static final String appTypeDbName="apps.db";
    private SQLiteDatabase db;
    private Context context;
    public DatabaseManager(){
        context= HealthyApplication.getContext();
        db=new HealthyLifeDBHelper().getWritableDatabase();
    }
    public String getAppTypeDbPath(){
        return context.getDatabasePath(appTypeDbName).toString();
    }
    public String getDBDirPath(){
        return context.getDatabasePath(appTypeDbName).getParent().toString();
    }
    public void saveAudio(long startTime,double volume){
        ContentValues values=new ContentValues();
        values.put("starttime",startTime);
        values.put("volume",volume);
        db.insert("audio",null,values);
    }
    public Cursor queryAudio(long startTime,long endTime){
        Cursor cursor=db.rawQuery("select * from audio where starttime>? and starttime<?",new String[]{String.valueOf(startTime),String.valueOf(endTime)});
        return cursor;
    }
    public void addAppUsageTime(String pkgName){
        int today=new DateManager().getTodayNum();
        db.execSQL("update appUsage set time=time+5000 where day=? and pkgName=?",new String[]{String.valueOf(today),pkgName});
    }
    public int getAppUsageTime(int day,String pkgName){
        int trueDay=new DateManager().getDayNum(day);
        Cursor cursor=db.rawQuery("select * from appUsage where day=? and pkgName=?", new String[]{String.valueOf(trueDay), pkgName});
        if(cursor.moveToNext()){
            return cursor.getInt(2);
        }
        else return 0;
    }
    public void createTodayUsage(){
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        int today=new DateManager().getTodayNum();
        for(int i=0;i<packages.size();i++){
            String pkgName=packages.get(i).packageName;
            ContentValues values=new ContentValues();
            values.put("day",today);
            values.put("pkgname",pkgName);
            values.put("time",0);
            db.insert("appUsage",null,values);
        }
    }
}
