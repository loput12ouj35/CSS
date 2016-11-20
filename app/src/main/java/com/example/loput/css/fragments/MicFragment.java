package com.example.loput.css.fragments;

import android.app.Fragment;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.loput.css.MainActivity;
import com.example.loput.css.R;
import com.example.loput.css.recorder.CSSAudioRecorder;
import com.example.loput.css.thread.NetworkThread;

/**
 * Created by loput on 2016-11-10.
 */

public class MicFragment extends Fragment {
    private ImageButton btBack;
    private CSSAudioRecorder recorder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mic, container, false);

        btBack = (ImageButton) v.findViewById(R.id.imageButtonBackM);
        btBack.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                recorder.stopRecording();
                ((MainActivity) getActivity()).changeScreen(MainActivity.Screen.c);
                return false;
            }
        });

        try {
            recorder = new CSSAudioRecorder();
        }
        catch( Exception e )
        {
            e.printStackTrace();
            return v;
        }

        recorder.startRecording();

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        recorder.stopRecording();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        recorder.stopRecording();
    }
}
