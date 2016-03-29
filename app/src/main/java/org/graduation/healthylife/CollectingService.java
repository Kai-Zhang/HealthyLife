package org.graduation.healthylife;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.graduation.collector.ICollector;

import java.util.ArrayList;
import java.util.List;

public class CollectingService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        _collectorList = new ArrayList<>();
        // TODO Add collectors
        Log.d("Custom Tag ", "Service started.");
        this.collect();
        this.stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Custom Tag ", "Service stopped.");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private List<ICollector> _collectorList;

    private void collect() {
        for (ICollector g : _collectorList) {
            g.collect();
        }
    }
}
