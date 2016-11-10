package com.example.loput.css;

/**
 * Created by loput on 2016-11-10.
 */

public class Setting {
    public enum Quality {low, medium, high};


    private static Setting INSTANCE;

    public int sound;
    public Quality soundQuality;
    public Quality videoQuality;

    private Setting() {
        sound = 50;
        soundQuality = Quality.high;
        videoQuality = Quality.high;
    }


    public static Setting getSetting (){
        if (INSTANCE == null)
            INSTANCE = new Setting();

        return INSTANCE;
    }
}
