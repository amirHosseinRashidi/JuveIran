package com.khayyamapp.juveiran.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.custom_views.IranSansButton;
import com.khayyamapp.juveiran.custom_views.IranSansEditText;
import com.khayyamapp.juveiran.data_model.Register;
import com.khayyamapp.juveiran.globals.Globals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {

    Button btnBack;
    IranSansButton btnRegister;
    IranSansEditText etEmail, etPassword, etRePassword, etName;
    Context c;

    public RegisterFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_register, container, false);
        c = getContext();

        btnBack = v.findViewById(R.id.btn_Fregister_back);
        etEmail = v.findViewById(R.id.et_Fregister_email);
        etPassword = v.findViewById(R.id.et_Fregister_password);
        etRePassword = v.findViewById(R.id.et_Fregister_repassword);
        etName = v.findViewById(R.id.et_Fregister_name);
        btnRegister = v.findViewById(R.id.btn_Fregister_register);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RelativeLayout rlProgress = v.findViewById(R.id.rl_Fregister_progress);

                final String email, name, password, rePassword;
                email = etEmail.getText().toString();
                name = etName.getText().toString().trim();
                password = etPassword.getText().toString();
                rePassword = etRePassword.getText().toString();

                //checking client inputs
                if (!email.equals("") && !name.equals("") && !password.equals("") && !rePassword.equals("")) {
                    if (password.equals(rePassword)) {
                        if (Globals.isEmailValid(etEmail.getText().toString().trim())) {
                            if (Globals.isPasswordRight(etPassword.getText().toString().trim())) {
                                rlProgress.setVisibility(View.VISIBLE);
                                ApiService.getApi().registerUser(email, password, name).enqueue(new Callback<Register>() {
                                    @Override
                                    public void onResponse(Call<Register> call, Response<Register> response) {
                                        rlProgress.setVisibility(View.GONE);
                                        Register register = response.body();
                                        Log.i("RegisterTest", "onResponse: " + register.getMsg());
                                        if (register.getStatus() == 1) {
                                            Toast.makeText(c, R.string.fRegisterFragment_register_successful, Toast.LENGTH_SHORT).show();
                                            LoginFragment loginFragment = new LoginFragment();
                                            Bundle bundle = new Bundle();
                                            bundle.putBoolean(Globals.EXTRA_IS_REGISTERED, true);
                                            bundle.putString(Globals.EXTRA_EMAIL, email);
                                            bundle.putString(Globals.EXTRA_PASS, password);
                                            loginFragment.setArguments(bundle);
                                            getActivity().getSupportFragmentManager().beginTransaction().remove(RegisterFragment.this)
                                                    .replace(R.id.rl_AloginAndRegister_container, loginFragment).commit();
                                        } else {
                                            Toast.makeText(c, R.string.fRegisterFragment_register_error, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Register> call, Throwable t) {
                                        rlProgress.setVisibility(View.GONE);
                                        Toast.makeText(c, R.string.api_error_respons, Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } else {
                                Toast.makeText(getContext(), getResources().getString(R.string.passwordLengthMustBeMoreThanFive), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.fRegister_error_checkInputs), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(c, R.string.fRegister_error_checkPassword, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(c, R.string.fRegister_error_checkInputs, Toast.LENGTH_SHORT).show();
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
