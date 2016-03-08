package com.javan.healthylife.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class HealthyService extends Service {
    public HealthyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
