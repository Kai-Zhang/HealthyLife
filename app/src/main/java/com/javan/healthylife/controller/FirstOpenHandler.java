package com.javan.healthylife.controller;

import android.content.Context;
import android.content.res.Resources;

import com.javan.healthylife.R;
import com.javan.healthylife.database.SharedPreferenceManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by javan on 2016/3/8.
 */
public class FirstOpenHandler {
    private SharedPreferenceManager sharedPreferenceManager;
    private Resources resources;
    private FileOutputStream out;
    public FirstOpenHandler(Context context){
        sharedPreferenceManager=new SharedPreferenceManager(context);
        sharedPreferenceManager.add("isFirstOpen",false);
        resources= context.getResources();
        try {
            out=context.openFileOutput("apps.db",context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                moveDBFile();
                findApp();
            }
        }).start();
    }
    private void moveDBFile(){

        InputStream in=resources.openRawResource(R.raw.apps);

        try {
            byte[] buffer=new byte[1024];
            int cnt;
            while((cnt=in.read(buffer))>0)
                out.write(buffer,0,cnt);
            out.flush();
            out.close();
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void findApp() {

    }


}
