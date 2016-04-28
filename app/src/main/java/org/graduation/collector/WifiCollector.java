package org.graduation.collector;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

public class WifiCollector implements ICollector {
    private static final String TAG = "WifiRecord";
    Context context = null;

    public WifiCollector(Context context) {
        this.context = context;
    }

    @Override
    public void collect() {
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> results = wifiManager.getScanResults();
        for (ScanResult result : results) {
            Log.d(TAG, "wifi ssid " + result.SSID);
            // TODO Save to database
        }
    }
}
