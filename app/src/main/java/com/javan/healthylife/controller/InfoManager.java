package com.javan.healthylife.controller;

import android.annotation.TargetApi;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;

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
    public InfoManager(){
        context=HealthyApplication.getContext();
    }
    @TargetApi(21)
    public ArrayList<AppUsageModel> getAppUsageInfo(long startTime,long endTime){
        MLog.d(TAG,"getAppUsageInfo");
        PackageManager packageManager=context.getPackageManager();
        UsageStatsManager usageStatsManager=(UsageStatsManager)context.getSystemService(context.USAGE_STATS_SERVICE);
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        Map<String,UsageStats> appUsageStats=usageStatsManager.
                queryAndAggregateUsageStats(startTime,endTime);
        //Did't get permission
        if(appUsageStats.isEmpty()){
            MLog.d(TAG,"isEmpty");
            return null;
        }
        ArrayList<AppUsageModel> appUsageInfo=new ArrayList<>();
        SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager(context);
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
    public ArrayList<NoiseModel> getNoiseInfo(){
        Cursor cursor=new DatabaseManager().queryAudio(System.currentTimeMillis()-24*60*60*1000,System.currentTimeMillis());
        ArrayList<NoiseModel> arrayList=new ArrayList<>();
        while(cursor.moveToNext()){
            arrayList.add(new NoiseModel(cursor.getLong(0),cursor.getDouble(1)));
        }
        return arrayList;
    }
}
