package org.graduation.collector;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.text.TextUtils;

import org.graduation.database.DatabaseManager;
import org.graduation.healthylife.MainApplication;

/**
 * Created by javan on 2016/6/9.
 */
public class ContactCollector {
    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER };
    private static final String[] CONTACT_RECORD_PROJECTION = { CallLog.Calls.DATE, // 日期
            CallLog.Calls.NUMBER, // 号码
            CallLog.Calls.TYPE, // 类型
            CallLog.Calls.DURATION
    };
    private static final String[] SMS_PROJECTION=new String[]{
            Telephony.Sms.ADDRESS,
            Telephony.Sms.DATE,
            Telephony.Sms.TYPE
    };
    /**联系人显示名称**/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    /**电话号码**/
    private static final int PHONES_NUMBER_INDEX = 1;

    private static final int CALLS_DATE_INDEX=0;
    private static final int CALLS_NUMBER_INDEX=1;
    private static final int CALLS_TYPE_INDEX=2;
    private static final int CALLS_DURATION_INDEX=3;

    private static final int SMS_ADDRESS_INDEX=0;
    private static final int SMS_DATE_INDEX=1;
    private static final int SMS_TYPE_INDEX=2;

    public void collect(){
        ContentResolver resolver = MainApplication.getContext().getContentResolver();
        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,PHONES_PROJECTION, null, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
        DatabaseManager databaseManager=DatabaseManager.getDatabaseManager();
        SQLiteDatabase db=databaseManager.getDatabase();
        if (phoneCursor != null) {
            db.beginTransaction();
            while (phoneCursor.moveToNext()) {
                //得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                long phoneNumHash=phoneNumber.hashCode();
                //得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                long nameHash=contactName.hashCode();
                databaseManager.saveContacts(nameHash,phoneNumHash);
            }
            phoneCursor.close();
        }
        //获取通话记录
        if ( Build.VERSION.SDK_INT >= 23) MainApplication.getContext().checkSelfPermission(android.Manifest.permission.READ_CALL_LOG);
        Cursor contactRecordCursor = resolver.query(CallLog.Calls.CONTENT_URI, CONTACT_RECORD_PROJECTION, null, null, CallLog.Calls.DATE + " DESC");

        while (contactRecordCursor.moveToNext()) {
            long phNumberHash = contactRecordCursor.getString(CALLS_NUMBER_INDEX).hashCode();
            String callType = contactRecordCursor.getString(CALLS_TYPE_INDEX);
            String callDate = contactRecordCursor.getString(CALLS_DATE_INDEX);
            long callTime=Long.valueOf(callDate);
            String callDuration = contactRecordCursor.getString(CALLS_DURATION_INDEX);
            int callDurationTime=Integer.valueOf(callDuration);
            int dircode = Integer.parseInt(callType);
            //1 is incomming,2 is outgoing,3 is missed
            databaseManager.saveCalls(callTime,phNumberHash,dircode,callDurationTime);
        }
        contactRecordCursor.close();

        Uri smsUri = Uri.parse("content://sms/");
        String SORT_ORDER = "date DESC";
        Cursor smsCursor=resolver.query(smsUri,SMS_PROJECTION,null,null,SORT_ORDER);
        while(smsCursor.moveToNext()){
            long date=smsCursor.getLong(SMS_DATE_INDEX);
            long address=smsCursor.getString(SMS_ADDRESS_INDEX).hashCode();
            int type=smsCursor.getInt(SMS_TYPE_INDEX);
            if(type!=1&&type!=2) continue;
            databaseManager.saveSms(date,address,type);
        }
        db.setTransactionSuccessful();
        db.endTransaction();;
    }
}
