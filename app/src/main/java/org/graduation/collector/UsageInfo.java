package org.graduation.collector;

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

    // startTime到endTime之间每个应用的使用时间，Map中的Key是应用的packageName,使用时间可以从UsageStats中获得
    // usageStats.getTotalTimeInForeground()即这个应用在前台的时间
    public Map<String,UsageStats> getAppUsageInfo(long startTime,long endTime) {
        Map<String, UsageStats> appUsageStats = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            UsageStatsManager usageStatsManager = (UsageStatsManager)context
                    .getSystemService(Context.USAGE_STATS_SERVICE);
            appUsageStats = usageStatsManager
                    .queryAndAggregateUsageStats(startTime, endTime);
        }
        // 权限获取失败
        if(appUsageStats == null || appUsageStats.isEmpty()){
            Log.d("UsageInfo", "Didn't get permission");
            return null;
        }
        return appUsageStats;
    }
}
