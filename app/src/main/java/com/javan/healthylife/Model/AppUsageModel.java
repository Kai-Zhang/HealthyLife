package com.javan.healthylife.Model;

/**
 * Created by javan on 2016/4/3.
 */
public class AppUsageModel {
    public String appName;//like "美团"
    public String packageName;//like "com.sankuai.meituan"
    public String appType;//like "购物"
    public long foregroundTime;//milliseconds
    public AppUsageModel(String appName,String packageName,String appType,long foregroundTime){
        this.appName=appName;
        this.packageName=packageName;
        this.foregroundTime=foregroundTime;
    }
}
