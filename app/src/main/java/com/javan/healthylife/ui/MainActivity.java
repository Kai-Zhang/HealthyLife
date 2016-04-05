package com.javan.healthylife.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.javan.healthylife.R;
import com.javan.healthylife.controller.FirstOpenHandler;
import com.javan.healthylife.controller.InfoHandler;
import com.javan.healthylife.controller.PermissionManager;
import com.javan.healthylife.database.SharedPreferenceManager;
import com.javan.healthylife.service.HealthyService;

public class MainActivity extends AppCompatActivity {
    private Button permissonBtn;
    private TextView noiseIndex,sleepIndex,addictionIndex,sportIndex,emotionIndex;
    public static MHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskInitial();
        mHandler=new MHandler();
        permissonBtn=(Button)findViewById(R.id.permissonBtn);
        permissonBtn.setOnClickListener(onClickListener);
        noiseIndex=(TextView)findViewById(R.id.noiseIndex);
        sleepIndex=(TextView)findViewById(R.id.sleepIndex);
        addictionIndex=(TextView)findViewById(R.id.addictionIndex);
        sportIndex=(TextView)findViewById(R.id.sportIndex);
        emotionIndex=(TextView)findViewById(R.id.emotionIndex);

    }

    @Override
    protected void onResume() {
        super.onResume();
        InfoHandler infoHandler=new InfoHandler();
        noiseIndex.setText(String.valueOf(infoHandler.getNoiseIndex()));
        addictionIndex.setText(String.valueOf(infoHandler.getAddictionIndex()));
    }

    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.permissonBtn:
                    Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                    startActivity(intent);
                    break;
            }
        }
    };
    class MHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle=msg.getData();
            switch (bundle.getInt("type")){
                case 1://update noiseIndex
                    break;
                case 2://update sleep
                    break;
                case 3://update addiction
                    addictionIndex.setText(bundle.getString("content"));
                    break;
                case 4://update sport
                    break;
                case 5://update emotion
                    break;
            }
        }
    }
    private void taskInitial(){
        if(new SharedPreferenceManager().getBoolean(FirstOpenHandler.firstOpenKey,true)){
            new FirstOpenHandler().handle();
        }
        if(Build.VERSION.SDK_INT>=21) {
            PermissionManager permissionManager = new PermissionManager(this);
            if (!permissionManager.checkUsagePermission()) {
                permissionManager.getUsagePermission();
            }
        }
        Intent intent=new Intent(this,HealthyService.class);
        startService(intent);
    }


}
