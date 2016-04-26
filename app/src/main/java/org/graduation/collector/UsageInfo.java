package org.graduation.collector;

import android.annotation.TargetApi;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.util.Log;

import java.util.Map;

/**
 * Created by javan on 2016/4/26.
 */
public class UsageInfo {
    private Context context;
    public UsageInfo(Context context){
        this.context=context;
    }

    //startTime到endTime之间每个应用的使用时间，Map中的Key是应用的packageName,使用时间可以从UsageStats中获得
    //usageStats.getTotalTimeInForeground()即这个应用在前台的时间
    @TargetApi(21)
    private Map<String,UsageStats> getAppUsageInfo(long startTime,long endTime){
        UsageStatsManager usageStatsManager=(UsageStatsManager)context.getSystemService(context.USAGE_STATS_SERVICE);
        Map<String,UsageStats> appUsageStats=usageStatsManager.queryAndAggregateUsageStats(startTime, endTime);
        //Did't get permission
        if(appUsageStats.isEmpty()){
            Log.d("UsageInfo" , "Did't get permission");
            return null;
        }
        return appUsageStats;
    }
}