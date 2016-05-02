package org.graduation.collector;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import org.graduation.database.DatabaseManager;

/**
 * Created by javan on 2016/4/25.
 */
public class LightCollector implements ICollector {
    private static final String TAG = "LightRecord";
    private SensorManager sensorManager;
    float light;

    public LightCollector(Context context){
        sensorManager=(SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_FASTEST);
    }
    @Override
    public void collect() {
        //这是光照强度
        Log.d(TAG, String.valueOf(light));
        DatabaseManager.getDatabaseManager().saveLight(System.currentTimeMillis(), light);
    }
    public void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }
    private SensorEventListener sensorEventListener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType()==Sensor.TYPE_LIGHT){
                light=event.values[0];
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}
