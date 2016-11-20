package com.example.loput.css.recorder;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.example.loput.css.thread.NetworkThread;

/**
 * Created by Xeno on 2016-11-14.
 */

public class CSSAudioRecorder {

    private AudioRecord audioRecord;

    private int BufferElements2Rec = 2048;

    private Thread recordingThread = null;
    private boolean isRecording = false;
    private NetworkThread nThread;

    private AudioRecord findAudioRecord() {
        for (int rate : new int[] { 11025, 22050, 44100 }) {
            for (short audioFormat : new short[] { AudioFormat.ENCODING_PCM_8BIT, AudioFormat.ENCODING_PCM_16BIT }) {
                for (short channelConfig : new short[] { AudioFormat.CHANNEL_IN_MONO, AudioFormat.CHANNEL_IN_STEREO }) {
                    try {
                        Log.d( "test", "Attempting rate " + rate + "Hz, bits: " + audioFormat + ", channel: "
                                + channelConfig);
                        int bufferSize = AudioRecord.getMinBufferSize(rate, channelConfig, audioFormat);

                        if (bufferSize != AudioRecord.ERROR_BAD_VALUE) {
                            // check if we can instantiate and have a success
                            AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, rate, channelConfig, audioFormat, bufferSize);

                            if (recorder.getState() == AudioRecord.STATE_INITIALIZED)
                                return recorder;
                        }
                    } catch (Exception e) {
                        Log.e( "test", rate + "Exception, keep trying.",e);
                    }
                }
            }
        }
        return null;
    }

    public CSSAudioRecorder() throws Exception
    {
        nThread = new NetworkThread();
        audioRecord = findAudioRecord();

        if( audioRecord == null )
        {
            throw new Exception( "cannot instantiate AudioRecord class" );
        }
    }

    public void startRecording() {

        if( !isRecording )
        {

            nThread.start();

            audioRecord.startRecording();
            isRecording = true;
            recordingThread = new Thread() {

                public void run() {
                    short sData[] = new short[BufferElements2Rec];

                    while (isRecording) {
                        // gets the voice output from microphone to byte format

                        audioRecord.read(sData, 0, BufferElements2Rec);

                        byte bData[] = short2byte(sData);
                        nThread.addToQueue(bData);
                    }
                }
            };
            recordingThread.start();
        }

    }

    private byte[] short2byte(short[] sData) {
        int shortArrsize = sData.length;
        byte[] bytes = new byte[shortArrsize * 2];
        for (int i = 0; i < shortArrsize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;

    }

    public void stopRecording() {
        if( isRecording )
        {
            if (null != audioRecord) {
                isRecording = false;
                audioRecord.stop();
                audioRecord.release();
                audioRecord = null;
                recordingThread = null;
                nThread.stopThread();
            }
        }
    }
}
