package com.javan.healthylife.controller;

import android.annotation.TargetApi;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;

import com.javan.healthylife.Model.AppUsageModel;
import com.javan.healthylife.Model.NoiseModel;
import com.javan.healthylife.database.DatabaseManager;
import com.javan.healthylife.database.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by javan on 2016/3/31.
 */
public class InfoManager {
    private final String TAG="InfoManager";
    private Context context;
    private PackageManager packageManager;
    private List<PackageInfo> packages;
    public InfoManager(){
        context=HealthyApplication.getContext();
        packageManager=context.getPackageManager();
        packages = packageManager.getInstalledPackages(0);
    }
    //today is 0,yesterday is -1 ,and so on

    public ArrayList<AppUsageModel> getAppUsageInfo(int day){
        if(Build.VERSION.SDK_INT>=21){
            return getAppUsageInfo_high(day);
        }
        else{
            return getAppUsageInfo_low(day);
        }
    }
    @TargetApi(21)
    private ArrayList<AppUsageModel> getAppUsageInfo_high(int day){
        MLog.d(TAG,"getAppUsageInfo");
        DateManager dateManager=new DateManager();
        UsageStatsManager usageStatsManager=(UsageStatsManager)context.getSystemService(context.USAGE_STATS_SERVICE);
        Map<String,UsageStats> appUsageStats=usageStatsManager.
                queryAndAggregateUsageStats(dateManager.getDayStartTime(day),dateManager.getDayEndTime(day));
        //Did't get permission
        if(appUsageStats.isEmpty()){
            MLog.d(TAG,"isEmpty");
            return null;
        }
        ArrayList<AppUsageModel> appUsageInfo=new ArrayList<>();
        SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager();
        //遍历安装的应用。
        //读取对应的使用时间和appName
        for(int i=0;i<packages.size();i++) {
            PackageInfo packageInfo = packages.get(i);
            String packageName=packageInfo.packageName;
            String appName=packageInfo.applicationInfo.loadLabel(packageManager).toString();
            String appType=sharedPreferenceManager.getAppType(packageName);
            if (appUsageStats.containsKey(packageName)) {
                UsageStats usageStats=appUsageStats.get(packageName);
                long usingTime=usageStats.getTotalTimeInForeground();
                if(usingTime==0) continue;
                else {
                    appUsageInfo.add(new AppUsageModel(appName,packageName,appType,usingTime));
                    MLog.so(TAG + appName + packageName + appType + usingTime);
                }
            }
        }
        return appUsageInfo;
    }
    private ArrayList<AppUsageModel> getAppUsageInfo_low(int day){
        DatabaseManager databaseManager=new DatabaseManager();
        ArrayList<AppUsageModel> appUsageInfo=new ArrayList<>();
        SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager();
        //遍历安装的应用。
        //读取对应的使用时间和appName
        for(int i=0;i<packages.size();i++) {
            PackageInfo packageInfo = packages.get(i);
            String packageName=packageInfo.packageName;
            ApplicationInfo applicationInfo=packageInfo.applicationInfo;
            String appName;
            if(applicationInfo==null){
                appName="unknown";
            }
            else{
                appName=applicationInfo.loadLabel(packageManager).toString();
            }
            String appType=sharedPreferenceManager.getAppType(packageName);
            long usingTime=databaseManager.getAppUsageTime(day,packageName);
            if(usingTime!=0){
                appUsageInfo.add(new AppUsageModel(appName,packageName,appType,usingTime));
                MLog.so(TAG + appName + packageName + appType + usingTime);
            }
            else{
                continue;
            }
        }
        return appUsageInfo;
    }
    public ArrayList<NoiseModel> getNoiseInfo(){
        Cursor cursor=new DatabaseManager().queryAudio(System.currentTimeMillis()-24*60*60*1000,System.currentTimeMillis());
        ArrayList<NoiseModel> arrayList=new ArrayList<>();
        while(cursor.moveToNext()){
            arrayList.add(new NoiseModel(cursor.getLong(0),cursor.getDouble(1)));
        }
        return arrayList;
    }
}
