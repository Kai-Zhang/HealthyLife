package org.graduation.collector;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import org.graduation.database.DatabaseManager;

/**
 * Created by javan on 2016/4/25.
 */
public class AudioCollector implements ICollector {
    private static final String TAG = "AudioRecord";
    AudioRecord mAudioRecord = null;
    private static boolean isGetVoiceRun=false;
    private static boolean collectFlag=false;
    private static int[] mSampleRates = new int[] { 8000, 11025, 22050, 44100 };
    private int bufferSize = 0;

    public AudioRecord findAudioRecord() {
        for (int rate : mSampleRates) {
            for (short audioFormat : new short[]
                    { AudioFormat.ENCODING_PCM_8BIT, AudioFormat.ENCODING_PCM_16BIT }) {
                for (short channelConfig : new short[]
                        { AudioFormat.CHANNEL_IN_MONO, AudioFormat.CHANNEL_IN_STEREO }) {
                    try {
                        Log.d(TAG, "Attempting rate " + rate + "Hz, bits: "
                                + audioFormat + ", channel: " + channelConfig);
                        int bufferSize = AudioRecord.getMinBufferSize(
                                rate, channelConfig, audioFormat);

                        if (bufferSize != AudioRecord.ERROR_BAD_VALUE) {
                            // check if we can instantiate and have a success
                            AudioRecord recorder = new AudioRecord(
                                    MediaRecorder.AudioSource.DEFAULT,
                                    rate,
                                    channelConfig,
                                    audioFormat,
                                    bufferSize);

                            if (recorder.getState() == AudioRecord.STATE_INITIALIZED) {
                                this.bufferSize = bufferSize;
                                return recorder;
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, rate + " Exception, keep trying.");
                    }
                }
            }
        }
        return null;
    }


    public void collect() {
        mAudioRecord = findAudioRecord();
        if (mAudioRecord == null) {
            Log.e(TAG, "mAudioRecord initialization failed");
            return;
        }
        isGetVoiceRun = true;
        mAudioRecord.startRecording();
        long startTime=System.currentTimeMillis();
        short[] buffer = new short[bufferSize];
        //r是实际读取的数据长度，一般而言r会小于buffer size
        int r = mAudioRecord.read(buffer, 0, bufferSize);
        Log.d(TAG, "data size " + r);
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
        Log.d(TAG, "time: " + startTime);
        Log.d(TAG, "decibel: " + volume);

        DatabaseManager.getDatabaseManager().saveAudio(startTime, volume);
    }

    @Override
    public void startCollect() {
        if (isGetVoiceRun) {
            return;
        }
        collectFlag=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(collectFlag){
                    collect();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @Override
    public void stopCollect() {
        collectFlag=false;
    }
}
