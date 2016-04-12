package org.graduation.healthylife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GatherAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, CollectingService.class));
        Log.d("Custom Tag", "Gather alarm Receiver awake.");
    }
}
