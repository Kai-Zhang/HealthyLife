package com.javan.healthylife.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by javan on 2016/3/8.
 */
public class SharedPreferenceManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String fileName="healthyLifeLog";
    public static final String APILevelKey="API_Level";
    public SharedPreferenceManager(Context context){
        sharedPreferences=context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }
    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }
    public String getAppType(String packageName){
        return sharedPreferences.getString(packageName+"_type",null);
    }
    public boolean getBoolean(String key,boolean defVal){
        return sharedPreferences.getBoolean(key,defVal);
    }
    public void put(String key,String value){
        editor=sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public void put(String key,boolean value){
        editor=sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public void put(String key,int value){
        editor=sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public void addTime(String key){
        editor=sharedPreferences.edit();
        int val=sharedPreferences.getInt(key,0);
        editor.putInt(key,val+5);
        editor.commit();
    }
    public void clear(){
        editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
