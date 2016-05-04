package org.graduation.healthylife;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.graduation.R;
import org.graduation.service.FeedbackAlarmReceiver;
import org.graduation.service.GatherAlarmReceiver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getFragmentManager().beginTransaction()
                .add(R.id.layout_mainpage, new OptionFragment())
                .commit();

        checkPermission();
        prepareServices();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_database) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
                }
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dumpDatabase();
                }
            }).run();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void prepareServices() {
        final AlarmManager alarmManager = (AlarmManager)this
                .getSystemService(Context.ALARM_SERVICE);
        final PendingIntent gatherPendingIntent = PendingIntent.getBroadcast(this, 0,
                new Intent(this, GatherAlarmReceiver.class), PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(gatherPendingIntent);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                2 * 60 * 1000,
                gatherPendingIntent);

        final AlarmManager alarmManager2 = (AlarmManager)this
                .getSystemService(Context.ALARM_SERVICE);
        final PendingIntent feedbackPendingIntent = PendingIntent.getBroadcast(this, 0,
                new Intent(this, FeedbackAlarmReceiver.class), PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar clock10 = Calendar.getInstance();
        clock10.setTimeInMillis(System.currentTimeMillis());
        clock10.set(Calendar.HOUR_OF_DAY, 10);
        clock10.set(Calendar.MINUTE, 0);
        clock10.set(Calendar.SECOND, 0);
        alarmManager2.cancel(feedbackPendingIntent);
        alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP,
                clock10.getTimeInMillis(),
                AlarmManager.INTERVAL_HALF_DAY,
                feedbackPendingIntent);
        Log.d("Service Preparation", "done.");
    }

    private void checkPermission() {
        // Dynamically request permissions on Android 6.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> requesting = new ArrayList<>();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requesting.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                requesting.add(Manifest.permission.RECORD_AUDIO);
            }
            if (requesting.isEmpty()) {
                return;
            }
            ActivityCompat.requestPermissions(this, requesting.toArray(new String[2]), 0);
        }
        // Ask for usage permission on available system
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.PACKAGE_USAGE_STATS)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getBaseContext(),
                        "我们的实验需要您打开权限开关,谢谢", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            }
        }
    }

    private void dumpDatabase() {
        final String SRC_DATABASE = "/data/data/org.graduation.healthylife/"
                + "databases/HealthyLife.db";
        final String DEST_FILE = Environment.getExternalStorageDirectory() + "/HealthyLife.db";
        try {
            InputStream fin = new FileInputStream(new File(SRC_DATABASE));
            OutputStream fout = new FileOutputStream(new File(DEST_FILE));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fin.read(buffer)) > 0) {
                fout.write(buffer, 0, length);
            }
            fout.flush();
            fin.close();
            fout.close();
            Toast.makeText(getBaseContext(), "数据库准备完毕", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "数据库复制失败", Toast.LENGTH_SHORT).show();
        }
    }
}
