package com.khayyamapp.juveiran.custom_views;

import android.content.Context;
import android.util.AttributeSet;

import com.khayyamapp.juveiran.JuveIranApp;

public class IranSansEditText extends android.support.v7.widget.AppCompatEditText {

    public IranSansEditText(Context context) {
        super(context);
        if (!isInEditMode()) {
            setupFont();
        }
    }

    public IranSansEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            setupFont();
        }
    }

    public IranSansEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            setupFont();
        }
    }

    //Here I putin font on this custom view
    public void setupFont() {
        JuveIranApp juveIranApp = (JuveIranApp) getContext().getApplicationContext();
        setTypeface(juveIranApp.getIranSansFont());
    }
}
