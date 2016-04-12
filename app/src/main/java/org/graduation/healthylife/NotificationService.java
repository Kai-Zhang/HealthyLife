package org.graduation.healthylife;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("debug message", "Binding ...");
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("debug message", "created notification service");
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Test notification")
                .setContentText("If you see this, than I won.")
                .setSmallIcon(R.mipmap.ic_launcher);

        ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(0, nBuilder.build());
    }
}
