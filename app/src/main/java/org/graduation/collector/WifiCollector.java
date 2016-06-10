package org.graduation.collector;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.graduation.database.DatabaseManager;
import org.graduation.healthylife.MainApplication;

import java.util.List;

public class WifiCollector implements ICollector {
    private static final String TAG = "WifiRecord";

    public void collect() {
        Log.i(TAG, "WiFi recording...");
        WifiManager wifiManager = (WifiManager) MainApplication.getContext()
                .getSystemService(Context.WIFI_SERVICE);
        boolean on = wifiManager.isWifiEnabled();
        if (!on) {
            wifiManager.setWifiEnabled(true);
        }
        List<ScanResult> results=null;
        while(results==null||results.size()==0) {
            wifiManager.startScan();
            results = wifiManager.getScanResults();
        }
        if (!on) {
            wifiManager.setWifiEnabled(false);
        }
        DatabaseManager databaseManager = DatabaseManager.getDatabaseManager();
        for (ScanResult result : results) {
            Log.d(TAG, "WiFi ssid " + result.SSID);
            databaseManager.saveWifi(System.currentTimeMillis(), result.SSID,result.BSSID,result.level);
        }
    }

    @Override
    public void startCollect() {
        collect();
    }

    @Override
    public void stopCollect() {

    }
}
