package org.graduation.healthylife;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;

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

    public void prepareServices() {
        final AlarmManager alarmManager = (AlarmManager)this
                .getSystemService(Context.ALARM_SERVICE);
        final PendingIntent gatherPendingIntent = PendingIntent.getBroadcast(this, 0,
                new Intent(this, GatherAlarmReceiver.class), PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(gatherPendingIntent);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,
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
}
