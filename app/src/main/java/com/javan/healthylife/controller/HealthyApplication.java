package com.javan.healthylife.controller;

import android.app.Application;
import android.content.Context;

/**
 * Created by javan on 2016/4/4.
 */
public class HealthyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        HealthyApplication.context=getApplicationContext();
    }
    public static Context getContext(){
        return HealthyApplication.context;
    }

}
