package org.graduation.healthylife;

import android.app.NotificationManager;
import android.app.PendingIntent;
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
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("debug message", "created notification service");
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("您现在心情如何呢?")
                .setContentText("快来告诉我你的心情吧!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity
                (this, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        nBuilder.setContentIntent(pendingIntent);

        ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(0, nBuilder.build());
        this.stopSelf();
    }
}
