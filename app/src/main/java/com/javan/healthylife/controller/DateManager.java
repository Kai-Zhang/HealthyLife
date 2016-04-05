package com.javan.healthylife.controller;

import com.javan.healthylife.database.DatabaseManager;
import com.javan.healthylife.database.SharedPreferenceManager;

/**
 * Created by javan on 2016/4/4.
 * 时间管理
 */
public class DateManager {
    private final int startHour=3;
    private final long MillisInAnHour=60*60*1000;
    private final String startDayKey="startDay";
    private final String todayKey="today";
    public DateManager(){

    }
    public void setStartDay(){
        int today=getTodayNum();
        new SharedPreferenceManager().put(startDayKey,today);
    }
    public int getStartDay(){
        return new SharedPreferenceManager().getSharedPreferences().getInt(startDayKey,0);
    }
    public void setToday(){
        new SharedPreferenceManager().put(todayKey,getTodayNum());
    }
    public int getTodayNum(){
        long currunt=System.currentTimeMillis();
        int today= (int)((currunt/MillisInAnHour-startHour)/24);
        SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager();
        int savedToday=sharedPreferenceManager.getSharedPreferences().getInt(todayKey,0);
        if(today!=savedToday){
            sharedPreferenceManager.put(todayKey,today);
            new DatabaseManager().createTodayUsage();
        }
        return today;
    }
    public long getDayStartTime(int nDaysAgo){
        long current=System.currentTimeMillis();
        long todayStartTime=current-current%(MillisInAnHour*24)+MillisInAnHour*startHour;
        return todayStartTime+MillisInAnHour*24*nDaysAgo;
    }
    public long getDayEndTime(int nDaysAgo){
        if(nDaysAgo==0){
            return System.currentTimeMillis();
        }
        else{
            return getDayStartTime(nDaysAgo+1);
        }
    }
    /*
     *  eg. getDayNum(0) means get today's num
     *      getDayNum(-1) means get yesterday's num
     */
    public int getDayNum(int nDaysAgo){
        return getTodayNum()+nDaysAgo;
    }
}
