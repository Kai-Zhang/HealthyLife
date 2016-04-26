package org.graduation.collector;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * Created by javan on 2016/4/25.
 */
public class AudioCollector implements ICollector {
    private static final String TAG = "AudioRecord";
    static final int SAMPLE_RATE_IN_HZ = 8000;
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
    AudioRecord mAudioRecord = null;
    private static boolean isGetVoiceRun=false;
    @Override
    public void collect() {
        if (isGetVoiceRun) {
            return;
        }
        try {
            mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                    AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
        } catch (IllegalArgumentException e) {
            Log.e("sound", "mAudioRecord初始化失败");
        }
        isGetVoiceRun = true;


        mAudioRecord.startRecording();
        long startTime=System.currentTimeMillis();
        short[] buffer = new short[BUFFER_SIZE];
        //r是实际读取的数据长度，一般而言r会小于buffersize
        int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
        long v = 0;
        // 将 buffer 内容取出，进行平方和运算
        for (short aBuffer : buffer) {
            v += aBuffer * aBuffer;
        }
        // 平方和除以数据总长度，得到音量大小。
        double mean = v / (double) r;
        double volume = 10 * Math.log10(mean);
        mAudioRecord.stop();
        mAudioRecord.release();
        mAudioRecord = null;
        isGetVoiceRun=false;

        //这是时间和分贝值
        Log.d(TAG,"时间："+startTime);
        Log.d(TAG ,"分贝值:" + volume);
    }
}
