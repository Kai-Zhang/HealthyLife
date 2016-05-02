package org.graduation.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.graduation.collector.AudioCollector;
import org.graduation.collector.ICollector;
import org.graduation.collector.LightCollector;
import org.graduation.collector.StepCollector;
import org.graduation.collector.WifiCollector;

import java.util.ArrayList;
import java.util.List;

public class CollectingService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        _collectorList = new ArrayList<>();
        //_collectorList.add(new AudioCollector());
        //_collectorList.add(new LightCollector(this));
        _collectorList.add(new WifiCollector(this));
        //_collectorList.add(new StepCollector(this));
        Log.d("Collecting Service", "Service started.");
        this.collect();
        this.stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Collecting Service", "Service stopped.");
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
