package org.graduation.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.graduation.collector.AudioCollector;
import org.graduation.collector.GpsCollector;
import org.graduation.collector.GyroscopeCollector;
import org.graduation.collector.ICollector;
import org.graduation.collector.LightCollector;
import org.graduation.collector.MagneticCollector;
import org.graduation.collector.ScreenCollector;
import org.graduation.collector.StepCollector;
import org.graduation.collector.UsageCollector;
import org.graduation.collector.WifiCollector;

import java.util.ArrayList;
import java.util.List;

public class CollectingService extends Service {
    private static final String TAG = "CollectingService";
    @Override
    public void onCreate() {
        super.onCreate();
        _collectorList = new ArrayList<>();
        _collectorList.add(new AudioCollector());
        _collectorList.add(new WifiCollector());
        _collectorList.add(new ScreenCollector());
        _collectorList.add(LightCollector.getCollector());
        _collectorList.add(StepCollector.getCollector());
        _collectorList.add(GpsCollector.getCollector());
        _collectorList.add(MagneticCollector.getCollector());
        _collectorList.add(GyroscopeCollector.getCollector());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
            _collectorList.add(new UsageCollector());
        }
        Log.d(TAG, "Service started.");
        collect();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    for (ICollector g : _collectorList) {
                        g.stopCollect();
                    }
                    stopSelf();
                }
            }
        }).start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service stopped.");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private List<ICollector> _collectorList;

    private void collect() {
        for (ICollector g : _collectorList) {
            g.startCollect();
        }
    }
}
