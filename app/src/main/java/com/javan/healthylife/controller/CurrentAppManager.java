package com.javan.healthylife.controller;

import android.app.ActivityManager;
import android.content.Context;

import com.javan.healthylife.database.DatabaseManager;
import com.javan.healthylife.database.SharedPreferenceManager;

import java.util.List;

/**
 * Created by javan on 2016/3/30.
 */
public class CurrentAppManager {
    private static final String TAG="CurrentAppManager";
    private ActivityManager activityManager;
    private SharedPreferenceManager sharedPreferenceManager;
    private DatabaseManager databaseManager;
    private Context context;
    public CurrentAppManager(){
        context=HealthyApplication.getContext();
        activityManager= (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        sharedPreferenceManager=new SharedPreferenceManager();
        databaseManager=new DatabaseManager();
    }
    public void add(){
        String[] pkgName=getCurrentApp();
        if(pkgName!=null) {
            for (int i = 0; i < pkgName.length; i++) {
                databaseManager.addAppUsageTime(pkgName[i]);
            }
        }
        else{
            MLog.so("running app not found");
        }
    }
    private  String[] getCurrentApp() {
        List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND&&processInfo.importanceReasonCode==0) {
                for (int i = 0; i < processInfo.pkgList.length; i++) {
                    MLog.so(TAG + " " + processInfo.pkgList[i] + " is running");
                }
                return processInfo.pkgList;
            }
        }
        return null;
    }
}