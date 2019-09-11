package com.khayyamapp.juveiran.custom_views;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.khayyamapp.juveiran.JuveIranApp;

public class IranSansTextView extends AppCompatTextView {
    public IranSansTextView(Context context) {
        super(context);
        if (!isInEditMode()) {
            setFont();
        }
    }

    public IranSansTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            setFont();
        }
    }

    public IranSansTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            setFont();
        }
    }

    private void setFont() {
        JuveIranApp application = (JuveIranApp) getContext().getApplicationContext();
        setTypeface(application.getIranSansFont());
    }

}
