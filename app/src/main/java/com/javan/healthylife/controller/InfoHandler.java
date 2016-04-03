package com.javan.healthylife.controller;

import android.content.Context;

import com.javan.healthylife.Model.AppUsageModel;

import java.util.ArrayList;

/**
 * Created by javan on 2016/4/3.
 */
public class InfoHandler {
    private final String TAG="InfoHandler";
    private Context context;
    public InfoHandler(Context context){
        this.context=context;
    }

    public double getAddictionIndex(){
        ArrayList<AppUsageModel> appUsageList=new InfoManager(context).getAppUsageInfo(System.currentTimeMillis()-24*60*60*1000,System.currentTimeMillis());
        double totalTime=0;
        if(appUsageList==null) return -1;
        for(int i=0;i<appUsageList.size();i++){
            totalTime+=appUsageList.get(i).foregroundTime;
        }
        MLog.so("time"+totalTime);

        /*
        将手机使用时间映射到0~100分。
        当前是简单的0~8小时线性映射到0~100分，待优化
         */
        double index=totalTime/1000/60/60/8*100;
        if(index>100){
            index=100;
        }
        index=((double)(int)(index*100))/100;
        return index;
    }
}