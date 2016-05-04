package org.graduation.collector;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import org.graduation.database.DatabaseManager;
import org.graduation.healthylife.MainApplication;

import java.util.List;

public class UsageCollector implements ICollector {
    private static final String TAG = "UsageRecord";

    @Override
    public void collect() {
        ActivityManager activityManager = (ActivityManager) MainApplication.getContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcessList
                = activityManager.getRunningAppProcesses();
        String[] running = null;
        for (ActivityManager.RunningAppProcessInfo info : runningProcessList) {
            if (info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && info.importanceReasonCode == 0) {
                running = info.pkgList;
                break;
            }
        }
        if (running == null) {
            Log.d(TAG, "Nothing found this time");
            return;
        }
        int size = 0;
        DatabaseManager manager = DatabaseManager.getDatabaseManager();
        for (String process : running) {
            ++size;
            manager.saveAppUsage(process);
        }
        Log.d(TAG, "Found " + size + " running process");
    }
}
