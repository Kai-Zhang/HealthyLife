package org.graduation.healthylife;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.graduation.R;
import org.graduation.service.FeedbackAlarmReceiver;
import org.graduation.service.GatherAlarmReceiver;

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
        if (id == R.id.action_settings) {
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
        Calendar clock8 = Calendar.getInstance();
        clock8.setTimeInMillis(System.currentTimeMillis());
        clock8.set(Calendar.HOUR_OF_DAY, 8);
        clock8.set(Calendar.MINUTE, 0);
        clock8.set(Calendar.SECOND, 0);
        alarmManager2.cancel(feedbackPendingIntent);
        alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP,
                clock8.getTimeInMillis(),
                AlarmManager.INTERVAL_HALF_DAY,
                feedbackPendingIntent);
        Log.d("Service Preparation", "done.");
    }
    private void checkPermission() {
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
}
