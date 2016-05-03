package org.graduation.collector;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
        try {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, listener);
            manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException e) {
            Toast.makeText(MainApplication.getContext(), "请授予我们权限", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void collect() {
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
            MainApplication.getContext().startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
        }
    };
}
