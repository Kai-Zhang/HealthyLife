package com.javan.healthylife.database;

import android.content.Context;

/**
 * Created by javan on 2016/3/8.
 */
public class DatabaseManager {
    public static final String appTypeDbName="apps.db";
    private Context context;
    public DatabaseManager(Context context){
        this.context=context;
    }
    public String getAppTypeDbPath(){
        return context.getDatabasePath(appTypeDbName).toString();
    }
    public String getDBDirPath(){
        return context.getDatabasePath(appTypeDbName).getParent().toString();
    }
}
