package com.javan.healthylife.controller;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.javan.healthylife.R;
import com.javan.healthylife.database.DatabaseManager;
import com.javan.healthylife.database.SharedPreferenceManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by javan on 2016/3/8.
 */
public class FirstOpenHandler {
    private static final String TAG="FirstOpenHandler";
    public static final String firstOpenKey="isFirstOpen";
    private SharedPreferenceManager sharedPreferenceManager;
    private PackageManager packageManager;
    private DatabaseManager databaseManager;
    private Resources resources;
    private Context context;
    public FirstOpenHandler(Context context){
        this.context=context;
        packageManager=context.getPackageManager();
        databaseManager=new DatabaseManager(context);
        sharedPreferenceManager=new SharedPreferenceManager(context);
        sharedPreferenceManager.put("isFirstOpen",false);
        resources= context.getResources();
        MLog.i("dataPath", databaseManager.getAppTypeDbPath());
    }
    public void handle(){
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
        FileOutputStream out;
        File dbFile=new File(databaseManager.getAppTypeDbPath());
        if(!dbFile.exists()){
            MLog.d(TAG,"dbfile not exist");
            File DBDir=new File(databaseManager.getDBDirPath());
            if(DBDir.mkdirs()) {
                MLog.d(TAG, "mkdir succeed");
            }
            else {
                MLog.d(TAG,"mkdir failed");
            }
        }
        try {
            out = new FileOutputStream(dbFile);
            byte[] buffer=new byte[1024];
            int cnt;
            while((cnt=in.read(buffer))>0)
                out.write(buffer,0,cnt);
            out.flush();
            out.close();
            in.close();

        } catch (Exception e) {
            MLog.i(TAG,"move dbFile failed");
            e.printStackTrace();
        }
    }
    private void findApp() {
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(databaseManager.getAppTypeDbPath(), null);
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        for(int i=0;i<packages.size();i++) {
            PackageInfo packageInfo = packages.get(i);
            Cursor cursor = db.rawQuery("select * from apps where name=?", new String[]{packageInfo.packageName});
            if (cursor.moveToNext()) {
                sharedPreferenceManager.put(cursor.getString(0) + "_type", cursor.getString(1));
                sharedPreferenceManager.put(cursor.getString(0) + "_name", packageInfo.applicationInfo.loadLabel(packageManager).toString());
                MLog.i(TAG,cursor.getString(0) + " " + cursor.getString(1));
                MLog.i(TAG,cursor.getString(0) + " " + packageInfo.applicationInfo.loadLabel(packageManager).toString());
            } else {
                MLog.i(TAG,packageInfo.packageName+"not found");
            }
        }
    }


}
