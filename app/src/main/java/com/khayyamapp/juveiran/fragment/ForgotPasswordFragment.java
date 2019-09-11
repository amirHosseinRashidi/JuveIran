package com.khayyamapp.juveiran.fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.khayyamapp.juveiran.R;


public class ForgotPasswordFragment extends Fragment {

    ImageView imgBack;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        Window window = getActivity().getWindow();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(), android.R.color.black));
        }


        FgetVerifyCodeFragment fgetVerifyCodeFragment = new FgetVerifyCodeFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.rl_FforgotPassword_viewContainer, fgetVerifyCodeFragment)
                .commit();




        imgBack = v.findViewById(R.id.img_FforgotPassword_backButton);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
   /*             getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.rl_AloginAndRegister_container, new LoginFragment())
                        .addToBackStack(null)
                        .commit();*/
            }
        });
        return v;
    }

}
