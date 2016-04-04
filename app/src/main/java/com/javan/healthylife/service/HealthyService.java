package com.javan.healthylife.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.javan.healthylife.Broadcast.StartServiceAlarmReceiver;
import com.javan.healthylife.controller.AudioManager;
import com.javan.healthylife.controller.CurrentAppManager;
import com.javan.healthylife.controller.MLog;

public class HealthyService extends Service {
    private static final String TAG="HealthyService";
    private static boolean isRunning=false;
    private int cnt;
    private CurrentAppManager currentAppManager;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MLog.so(TAG + " onStartCommand:" + SystemClock.elapsedRealtime());
        setAlarm();
        cnt=0;
        if(!isRunning) {
            isRunning=true;
            currentAppManager=new CurrentAppManager(this);
            doTask();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        isRunning=false;
        super.onDestroy();
    }

    private void doTask(){
        MLog.so(TAG+" start Task");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(isRunning){
                    MLog.so("Service alive");
                    appUsageMonitor();
                    //if(cnt%120==0){
                        recordAudio();
                    //}
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cnt++;
                }
            }
        }).start();
    }
    private void appUsageMonitor(){

    }
    private void recordAudio(){
        new AudioManager().getAndSaveNoiseLevel();
    }
    private void setAlarm(){
        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        long oneMin=60*1000;
        long tenMin=10*oneMin;
        long triggerAtTime= SystemClock.elapsedRealtime()+oneMin;
        Intent intent=new Intent(this, StartServiceAlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME,triggerAtTime,pendingIntent);
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
