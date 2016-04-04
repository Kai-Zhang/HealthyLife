package com.javan.healthylife.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.javan.healthylife.controller.HealthyApplication;

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
}
