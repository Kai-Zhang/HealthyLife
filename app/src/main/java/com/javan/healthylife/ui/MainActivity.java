package com.javan.healthylife.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.javan.healthylife.R;
import com.javan.healthylife.controller.FirstOpenHandler;
import com.javan.healthylife.service.HealthyService;

public class MainActivity extends AppCompatActivity {
    private static String sharedPreferencedName;//存储的文件名
    private SharedPreferences sharedPreferences;
    private boolean isFirstOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initial();
        if(isFirstOpen){
            new FirstOpenHandler(this);
        }

        Intent startMonitorServiceIntent=new Intent(this,HealthyService.class);
        startService(startMonitorServiceIntent);
    }
    private void initial(){
        sharedPreferencedName=getString(R.string.sharedPreferencesName);
        sharedPreferences= getSharedPreferences(sharedPreferencedName, MODE_PRIVATE);
        isFirstOpen=sharedPreferences.getBoolean("isFirstOpen", true);
    }

}
