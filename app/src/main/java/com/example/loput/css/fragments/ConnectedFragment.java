package com.example.loput.css.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.loput.css.MainActivity;
import com.example.loput.css.R;

/**
 * Created by loput on 2016-11-09.
 */

public class ConnectedFragment extends Fragment {
    private ImageButton btCam;
    private ImageButton btMic;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_connected, container, false);

        btCam = (ImageButton) v.findViewById(R.id.imageButtonCam);
        btMic = (ImageButton) v.findViewById(R.id.imageButtonMic);

        btCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeScreen(MainActivity.Screen.cam);

            }
        });

        btMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeScreen(MainActivity.Screen.mic);
            }
        });


        return v;
    }
}
