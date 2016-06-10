package org.graduation.collector;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import org.graduation.database.DatabaseManager;
import org.graduation.healthylife.MainApplication;

public class GpsCollector implements ICollector {
    private static final String TAG = "GPSRecord";
    private LocationManager manager = null;
    private Location location = null;

    public static GpsCollector self = new GpsCollector();

    public static GpsCollector getCollector() {
        return self;
    }

    private GpsCollector() {
        manager = (LocationManager) MainApplication.getContext()
                .getSystemService(Context.LOCATION_SERVICE);
    }
    public void collect() {
        System.out.println("collect gps");
        Log.d(TAG, "location: altitude: " + (location == null ? 0 : location.getAltitude()) + ", "
                + "longitude: " + (location == null ? 0 : location.getLongitude()) + ", "
                + "latitude: " + (location == null ? 0 : location.getLatitude()));
        if (location == null) {
            DatabaseManager.getDatabaseManager().saveLocation(0, 0, 0);
        } else {
            DatabaseManager.getDatabaseManager().saveLocation(
                    location.getAltitude(), location.getLongitude(), location.getLatitude());
        }
    }

    private LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location loc) {
            location = loc;
            collect();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(MainApplication.getContext(), "请激活GPS", Toast.LENGTH_SHORT).show();
            MainApplication.getContext().startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    };

    @Override
    public void startCollect() {
        try {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, listener);
            location=manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            collect();
        } catch (SecurityException e) {
            Toast.makeText(MainApplication.getContext(), "请授予我们权限", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void stopCollect() {
        if ( Build.VERSION.SDK_INT >= 23) MainApplication.getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        if(manager!=null)
            manager.removeUpdates(listener);
    }
}
