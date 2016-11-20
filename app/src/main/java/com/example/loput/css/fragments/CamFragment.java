package com.example.loput.css.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.loput.css.MainActivity;
import com.example.loput.css.R;
import com.example.loput.css.thread.NetworkThread;

/**
 * Created by loput on 2016-11-10.
 */

public class CamFragment extends Fragment {
    private ImageButton btBack;
    private NetworkThread netThread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cam, container, false);

        btBack = (ImageButton) v.findViewById(R.id.imageButtonBack);
        btBack.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((MainActivity) getActivity()).changeScreen(MainActivity.Screen.c);
                return false;
            }
        });


        netThread = new NetworkThread();
        netThread.start();

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        netThread.stopThread();
    }
}
