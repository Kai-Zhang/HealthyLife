package org.graduation.healthylife;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.graduation.database.DatabaseManager;
import org.graduation.database.SharedPreferenceManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by javan on 2016/6/13.
 */
public class FtpUploader {
    private final static String addr="114.212.84.179";
    private final static int port=21;
    private final static String TAG="upload database";
    public boolean upload(){
        boolean result=false;
        FTPClient ftpClient=new FTPClient();
        try {
            ftpClient.connect(addr, port);
            Log.d(TAG,"connected");
            if (ftpClient.login("testftp","test"))
            {
                ftpClient.enterLocalPassiveMode(); // important!
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                String data = "/data/data/org.graduation.healthylife/"
                        + "databases/HealthyLife.db";

                FileInputStream in = new FileInputStream(new File(data));
                SharedPreferenceManager sm=SharedPreferenceManager.getManager();
                String phoneID = sm.getString("phoneID", null);
                int uploadID = sm.getInt("uploadID",0);
                String fileName="/"+phoneID+"_"+uploadID+".db";
                Log.d(TAG,fileName);
                sm.put("uploadID", uploadID + 1);
                result = ftpClient.storeFile(fileName, in);
                in.close();
                Looper.prepare();
                if (result){
                    Toast.makeText(MainApplication.getContext(), "HealthyLife:Upload succeed", Toast.LENGTH_SHORT).show();
                    DatabaseManager.getDatabaseManager().refresh();
                }
                else Toast.makeText(MainApplication.getContext(), "HealthyLife:Upload failed", Toast.LENGTH_SHORT).show();
                Looper.loop();
                ftpClient.logout();
                ftpClient.disconnect();
            }
            else Log.d(TAG,"login failed");
        } catch (IOException e) {
            Log.e(TAG,"error");
            e.printStackTrace();
        }
        return result;
    }

}
