package org.graduation.collector;

import android.database.Cursor;

import org.graduation.healthylife.MainApplication;

import java.util.ArrayList;
import java.util.List;

public class CallLogs {
    public static class CallLog {
        public String phoneNumber;
        public String callType;
        public String callDate;
        public String callDuration;
    }
    @SuppressWarnings("ResourceType")
    public List<CallLog> getCallLogs() {
        Cursor cursor = MainApplication.getContext().getContentResolver()
                .query(android.provider.CallLog.Calls.CONTENT_URI, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        List<CallLog> result = new ArrayList<>();
        int number = cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(android.provider.CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(android.provider.CallLog.Calls.DURATION);
        while (cursor.moveToNext()) {
            CallLog log = new CallLog();
            log.phoneNumber = cursor.getString(number);
            log.callType = cursor.getString(type);
            log.callDate = cursor.getString(date);
            log.callDuration = cursor.getString(duration);
            result.add(log);
        }
        cursor.close();
        return result;
    }
}
