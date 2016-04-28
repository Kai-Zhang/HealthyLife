package org.graduation.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by javan on 2016/3/8.
 */
public class SharedPreferenceManager {
    private SharedPreferences sharedPreferences;

    private static final String fileName="HealthyLifeLog";

    private static SharedPreferenceManager self = null;

    public static SharedPreferenceManager getManager() {
        return self;
    }
    public static SharedPreferenceManager initManager(Context applicationContext) {
        if (self == null) {
            self = new SharedPreferenceManager(applicationContext);
        }
        return self;
    }
    private SharedPreferenceManager() { }
    private SharedPreferenceManager(Context applicationContext){
        sharedPreferences = applicationContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }
    public String getAppType(String packageName){
        return sharedPreferences.getString(packageName+"_type",null);
    }
    public boolean getBoolean(String key,boolean defVal){
        return sharedPreferences.getBoolean(key, defVal);
    }
    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }
    public void put(String key,String value){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public void put(String key,boolean value){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public void put(String key,int value){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public void put(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }
    public void clear(){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
