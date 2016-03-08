package com.javan.healthylife.controller;

import android.content.Context;
import android.content.res.Resources;

import com.javan.healthylife.R;
import com.javan.healthylife.database.SharedPreferenceManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by javan on 2016/3/8.
 */
public class FirstOpenHandler {
    private SharedPreferenceManager sharedPreferenceManager;
    private Resources resources;
    private String DBdir;
    private String filePath;
    public FirstOpenHandler(Context context){
        sharedPreferenceManager=new SharedPreferenceManager(context);
        sharedPreferenceManager.add("isFirstOpen",false);
        resources=context.getResources();
        DBdir=resources.getString(R.string.DBDir);
        filePath=DBdir+"/apps.db";
        moveDBFile();
    }
    private void moveDBFile(){

        InputStream is=resources.openRawResource(R.raw.apps);
        //BufferedInputStream buf = new BufferedInputStream(is);

        File dbFile=new File(filePath);
        if(dbFile.exists())
            System.out.println("File exists!");
        else{
            File dir=new File(DBdir);
            if(dir.mkdirs())
                System.out.println("mkdir succeed!");
            else
                System.out.println("mkdir failed!");
        }
        try {
            FileOutputStream fos=new FileOutputStream(dbFile);
            byte[] buffer=new byte[1024];
            int cnt;
            while((cnt=is.read(buffer))>0)
                fos.write(buffer,0,cnt);
            fos.flush();
            fos.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(dbFile.exists())
            System.out.println("File exists!");
    }

}
