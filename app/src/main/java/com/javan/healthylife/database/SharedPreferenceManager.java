package com.javan.healthylife.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.javan.healthylife.R;

/**
 * Created by javan on 2016/3/8.
 */
public class SharedPreferenceManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferenceManager(Context context){
        String fileName=context.getString(R.string.sharedPreferencesName);
        sharedPreferences=context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }
    public void add(String key,String value){
        editor=sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public void add(String key,boolean value){
        editor=sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }
    public void clear(){
        editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
