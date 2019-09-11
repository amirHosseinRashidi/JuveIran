package com.khayyamapp.juveiran.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;
import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.globals.SpGlobals;

import static com.khayyamapp.juveiran.globals.SpGlobals.KEY_USER_IMAGEURL;

public class ZoomImageFragment extends Fragment {


    public ZoomImageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_zoom_image, container, false);
        ZoomageView zoomageView = v.findViewById(R.id.zImg_FzoomImage_image);
        Glide.with(this).load(getArguments().getString(KEY_USER_IMAGEURL)).into(zoomageView);
        return v;
    }

}
