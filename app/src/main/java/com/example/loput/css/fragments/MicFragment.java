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
 * Created by loput on 2016-11-10.
 */

public class MicFragment extends Fragment {
    private ImageButton btBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mic, container, false);

        btBack = (ImageButton) v.findViewById(R.id.imageButtonBackM);
        btBack.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((MainActivity) getActivity()).changeScreen(MainActivity.Screen.c);
                return false;
            }
        });


        return v;
    }
}
