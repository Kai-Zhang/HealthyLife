package com.javan.healthylife.controller;

import android.annotation.TargetApi;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;

import java.util.Map;

/**
 * Created by javan on 2016/4/2.
 */
public class PermissionManager {
    private Context context;
    public PermissionManager(Context context){
        this.context=context;
    }
    public void getUsagePermission(){
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        context.startActivity(intent);
    }

    @TargetApi(21)
    public boolean checkUsagePermission(){
        MLog.d("mainactivity","checkUsagePermission");
        UsageStatsManager usageStatsManager=(UsageStatsManager)context.getSystemService(context.USAGE_STATS_SERVICE);
        Map<String,UsageStats> appUsageStats=usageStatsManager.
                queryAndAggregateUsageStats(System.currentTimeMillis() - SystemClock.elapsedRealtime(),System.currentTimeMillis());
        return !appUsageStats.isEmpty();

    }
}
