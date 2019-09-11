package com.khayyamapp.juveiran.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;
import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.globals.SpGlobals;

public class ShortArticleZoomImageActivity extends AppCompatActivity {

    ZoomageView zImgImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_article_zoom_image);


        zImgImage = findViewById(R.id.zImg_AshortArticleZoomImage_pic);

        Glide.with(this).load(getIntent().getExtras().getString(SpGlobals.KEY_USER_IMAGEURL, "s")).into(zImgImage);
        Log.i("Teeest", "onCreate: "+getIntent().getExtras().getString(SpGlobals.KEY_USER_IMAGEURL, "s"));
    }
}
