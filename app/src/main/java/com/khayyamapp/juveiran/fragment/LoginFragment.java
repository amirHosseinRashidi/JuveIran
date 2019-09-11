package com.khayyamapp.juveiran.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.activity.MainActivity;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.custom_views.IranSansEditText;
import com.khayyamapp.juveiran.custom_views.IranSansTextView;
import com.khayyamapp.juveiran.data_model.Login;
import com.khayyamapp.juveiran.globals.Globals;
import com.khayyamapp.juveiran.globals.SpGlobals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    Button btnBack;
    Button btnLogin;
    IranSansTextView tvForgotPassword;
    IranSansEditText etEmail;
    IranSansEditText etPassword;
    boolean isFromRegisterFragment = false;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_login, container, false);

        if (getArguments() != null) {
            isFromRegisterFragment = getArguments().getBoolean(Globals.EXTRA_IS_REGISTERED);
        }

        btnBack = v.findViewById(R.id.btn_Flogin_back);
        etEmail = v.findViewById(R.id.et_Flogin_email);
        etPassword = v.findViewById(R.id.et_Flogin_password);
        btnLogin = v.findViewById(R.id.btn_Flogin_login);
        tvForgotPassword = v.findViewById(R.id.tv_Flogin_forgotPassword);

        if (isFromRegisterFragment) {
            etEmail.setText(getArguments().getString(Globals.EXTRA_EMAIL));
            etPassword.setText(getArguments().getString(Globals.EXTRA_PASS));
        }

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.rl_AloginAndRegister_container, new ForgotPasswordFragment())
                        .addToBackStack("forgotPass")
                        .commit();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RelativeLayout rlProgress = v.findViewById(R.id.rl_Flogin_progress);
                if (!etPassword.getText().toString().equals("") && !etEmail.getText().toString().equals("")) {
                    if (Globals.isEmailValid(etEmail.getText().toString())) {
                        if (Globals.isPasswordRight(etPassword.getText().toString().trim())) {
                            rlProgress.setVisibility(View.VISIBLE);
                            ApiService.getApi().loginUser(etEmail.getText().toString(), etPassword.getText().toString()).enqueue(new Callback<Login>() {
                                @Override
                                public void onResponse(Call<Login> call, Response<Login> response) {
                                    Login login = response.body();
                                    rlProgress.setVisibility(View.INVISIBLE);
                                    if (login.getStatus() == 1) {
                                        Toast.makeText(getActivity(), R.string.message_login_successful, Toast.LENGTH_SHORT).show();


                                        //saving user details here
                                        SharedPreferences preferences = getActivity().getSharedPreferences(SpGlobals.userStatsSpName, Context.MODE_PRIVATE);
                                        preferences.edit().putString(SpGlobals.KEY_USER_NAME, login.getName()).apply();
                                        preferences.edit().putString(SpGlobals.KEY_USER_IMAGEURL, login.getImageThumb()).apply();
                                        preferences.edit().putString(SpGlobals.KEY_USER_TOKEN, login.getToken()).apply();
                                        preferences.edit().putBoolean(SpGlobals.KEY_USER_LOGIN_STATE, true).apply();


                                        getActivity().startActivity(new Intent(getContext(), MainActivity.class));
                                        getActivity().finish();
                                    } else {
                                        Toast.makeText(getActivity(), R.string.message_login_error, Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<Login> call, Throwable t) {
                                    rlProgress.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(), getResources().getString(R.string.api_error_respons), Toast.LENGTH_SHORT).show();


                                }
                            });
                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.passwordLengthMustBeMoreThanFive), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.pleaseEnterEmailorPhoneNumber), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.fRegister_error_checkInputs), Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return v;
    }

}
