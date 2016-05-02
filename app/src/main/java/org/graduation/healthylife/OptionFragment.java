package org.graduation.healthylife;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.usage.UsageStats;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import org.graduation.collector.UsageInfo;
import org.graduation.database.DatabaseManager;
import org.graduation.database.SharedPreferenceManager;

import java.util.Map;
import java.util.UUID;

public class OptionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.content_main, container, false);
        view.findViewById(R.id.button_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checked = ((RadioGroup) view.findViewById(R.id.radiogroup_main))
                        .getCheckedRadioButtonId();
                if (checked == -1) {
                    return;
                }
                int emotion = -1;
                switch (checked) {
                    case R.id.radio_sad:
                        Log.d("onclick", "sad");
                        emotion = 1;
                        break;
                    case R.id.radio_happy:
                        Log.d("onclick", "happy");
                        emotion = 2;
                        break;
                    case R.id.radio_angry:
                        Log.d("onclick", "angry");
                        emotion = 3;
                        break;
                    case R.id.radio_content:
                        Log.d("onclick", "content");
                        emotion = 4;
                        break;
                    case R.id.radio_tense:
                        Log.d("onclick", "tense");
                        emotion = 5;
                        break;
                    case R.id.radio_energetic:
                        emotion = 6;
                        Log.d("onclick", "energetic");
                }
                new Thread(new ResultRecord()
                        .setContextParam(getActivity().getApplicationContext())
                        .setEmotion(emotion)).run();
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.layout_mainpage, new ResultFragment())
                        .commit();
            }
        });
        return view;
    }

    private static class ResultRecord implements Runnable {
        int emotion = -1;
        Context contextParam = null;
        private static final String LAST_RECORD_TIME = "LastRecordAt";

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            int emotionId = UUID.randomUUID().toString().hashCode();
            DatabaseManager manager = DatabaseManager.getDatabaseManager();
            manager.saveEmotion(emotionId, emotion);
            Log.d("Result Record", "save " + emotionId + ": " + emotion);
            long last = SharedPreferenceManager.getManager().getLong(LAST_RECORD_TIME, 0);
            long now = System.currentTimeMillis();
            SharedPreferenceManager.getManager().put(LAST_RECORD_TIME, now);
            Map<String, UsageStats> usage = new UsageInfo(contextParam).getAppUsageInfo(last, now);
            if (usage != null) {
                for (Map.Entry<String, UsageStats> entry : usage.entrySet()) {
                    manager.saveAppUsage(entry.getKey(),
                            entry.getValue().getTotalTimeInForeground(), emotionId);
                }
            }
        }

        public ResultRecord setEmotion(int emotion) {
            this.emotion = emotion;
            return this;
        }
        public ResultRecord setContextParam(Context context) {
            this.contextParam = context;
            return this;
        }
    }
}
