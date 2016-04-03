package com.javan.healthylife.controller;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by javan on 2016/3/31.
 */
public class MLog {
    public static final String appLog="appUsage.log";
    public MLog(){

    }
    public static void fo(Context context,String fileName,String msg){
        BufferedWriter writer=null;
        try {
            FileOutputStream out=context.openFileOutput(fileName,Context.MODE_APPEND);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void so(String msg){
        System.out.println(msg);
    }
    public static void v(String tag,String message){
        Log.v(tag, message);
    }
    public static void d(String tag,String message){
        Log.d(tag, message);
    }
    public static void i(String tag,String message){
        Log.i(tag,message);
    }
    public static void w(String tag,String message){
        Log.w(tag, message);
    }
    public static void e(String tag,String message){
        Log.e(tag, message);
    }
}
