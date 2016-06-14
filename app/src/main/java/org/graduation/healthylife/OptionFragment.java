package org.graduation.healthylife;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.usage.UsageStats;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.graduation.R;
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
        final String[] result = {"无", "低", "中", "高"};
        final TextView result_happiness= (TextView) (view.findViewById(R.id.result_happiness));
        final TextView result_sadness= (TextView) (view.findViewById(R.id.result_sadness));
        final TextView result_anger= (TextView) (view.findViewById(R.id.result_anger));
        final TextView result_surprise= (TextView) (view.findViewById(R.id.result_surprise));
        final TextView result_fear= (TextView) (view.findViewById(R.id.result_fear));
        final TextView result_disgust= (TextView) (view.findViewById(R.id.result_disgust));
        ((SeekBar) view.findViewById(R.id.seekBar_happiness))
                .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                result_happiness.setText(result[progress]);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        ((SeekBar) view.findViewById(R.id.seekBar_sadness))
                .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                result_sadness.setText(result[progress]);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        ((SeekBar) view.findViewById(R.id.seekBar_anger))
                .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                result_anger.setText(result[progress]);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        ((SeekBar) view.findViewById(R.id.seekBar_surprise))
                .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                result_surprise.setText(result[progress]);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        ((SeekBar) view.findViewById(R.id.seekBar_fear))
                .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                result_fear.setText(result[progress]);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        ((SeekBar) view.findViewById(R.id.seekBar_disgust))
                .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                result_disgust.setText(result[progress]);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        view.findViewById(R.id.button_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int happiness = ((SeekBar) view.findViewById(R.id.seekBar_happiness)).getProgress();
                int sadness = ((SeekBar) view.findViewById(R.id.seekBar_sadness)).getProgress();
                int anger = ((SeekBar) view.findViewById(R.id.seekBar_anger)).getProgress();
                int surprise = ((SeekBar) view.findViewById(R.id.seekBar_surprise)).getProgress();
                int fear = ((SeekBar) view.findViewById(R.id.seekBar_fear)).getProgress();
                int disgust = ((SeekBar) view.findViewById(R.id.seekBar_disgust)).getProgress();
                long time = System.currentTimeMillis();
                SharedPreferenceManager sm = SharedPreferenceManager.getManager();
                long lastTime = sm.getLong("lasttime", 0);
                if (time - lastTime <= 5 * 60 * 60 * 1000) {
                    Toast.makeText(getActivity(), "您在5小时内提交过心情，请勿频繁提交", Toast.LENGTH_SHORT).show();
                    return;
                }
                sm.put("lasttime", time);
                int emotionCnt = sm.getInt("emotionCnt", 0);
                emotionCnt++;
                sm.put("emotionCnt", emotionCnt);
                new Thread(new ResultRecord()
                        .setContextParam(getActivity().getApplicationContext())
                        .setEmotions(happiness, sadness, anger, surprise, fear, disgust)).start();
                FragmentTransaction ft=getActivity ().getFragmentManager().beginTransaction();
                ft.replace(R.id.layout_mainpage, new ResultFragment());
                ft.commit();
            }
        });
        return view;
    }

    private static class ResultRecord implements Runnable {
        int happiness = -1;
        int sadness = -1;
        int anger = -1;
        int surprise = -1;
        int fear = -1;
        int disgust = -1;
        Context contextParam = null;
        private static final String LAST_RECORD_TIME = "LastRecordAt";

        @Override
        public void run() {
            int emotionId = UUID.randomUUID().toString().hashCode();
            DatabaseManager manager = DatabaseManager.getDatabaseManager();
            manager.saveEmotion(emotionId, happiness, sadness, anger, surprise, fear, disgust);
            Log.d("Result Record", "save " + emotionId + "(" + happiness + ", " + anger + ", "
                    + surprise + ", " + fear + ", " + disgust + ")");
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
                return;
            }
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

        public ResultRecord setEmotions(int happiness, int sadness, int anger,
                                        int surprise, int fear, int disgust) {
            this.happiness = happiness;
            this.sadness = sadness;
            this.anger = anger;
            this.surprise = surprise;
            this.fear = fear;
            this.disgust = disgust;
            return this;
        }
        public ResultRecord setContextParam(Context context) {
            this.contextParam = context;
            return this;
        }
    }
}
