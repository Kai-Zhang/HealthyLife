package com.javan.healthylife.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.javan.healthylife.service.HealthyService;

public class StartServiceAlarmReceiver extends BroadcastReceiver {
    public StartServiceAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent i=new Intent(context, HealthyService.class);
        context.startService(i);
    }
}
