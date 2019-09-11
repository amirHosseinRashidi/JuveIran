package com.khayyamapp.juveiran.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.activity.MainActivity;
import com.khayyamapp.juveiran.custom_views.IranSansButton;
import com.khayyamapp.juveiran.custom_views.IranSansTextView;
import com.khayyamapp.juveiran.globals.SpGlobals;

import static android.content.Context.MODE_PRIVATE;

public class LoginOrRegisterFragment extends Fragment {

    IranSansButton btnLogin, btnRegister;
    IranSansTextView tvGetInside;
    SharedPreferences spUserStat;


    Context c;


    public LoginOrRegisterFragment() {
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
        View v = inflater.inflate(R.layout.fragment_login_or_register, container, false);
        spUserStat = getActivity().getSharedPreferences(SpGlobals.userStatsSpName, MODE_PRIVATE);
        c = getActivity();


        btnLogin = v.findViewById(R.id.btn_FloginOrRegister_login);
        btnRegister = v.findViewById(R.id.btn_FloginOrRegister_register);
        tvGetInside = v.findViewById(R.id.txt_FloginOrRegister_getInside);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                RegisterFragment registerFragment = new RegisterFragment();
                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                transaction.replace(R.id.rl_AloginAndRegister_container, new RegisterFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                transaction.replace(R.id.rl_AloginAndRegister_container, new LoginFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        tvGetInside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });


        return v;

    }

}
