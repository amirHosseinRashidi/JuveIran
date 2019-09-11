package com.khayyamapp.juveiran;

import android.app.Application;
import android.graphics.Typeface;

public class JuveIranApp extends Application {

    private static Typeface iranSansFont;

    @Override
    public void onCreate() {
        super.onCreate();
        iranSansFont = Typeface.createFromAsset(getAssets(),"fonts/IRAN Sans.ttf");

    }

    public static Typeface getIranSansFont() {
        return iranSansFont;
    }

}
