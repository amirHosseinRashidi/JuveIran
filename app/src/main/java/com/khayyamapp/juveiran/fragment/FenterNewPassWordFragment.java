package com.khayyamapp.juveiran.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.custom_views.IranSansEditText;
import com.khayyamapp.juveiran.data_model.ResetPasswordResponse;
import com.khayyamapp.juveiran.globals.Globals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FenterNewPassWordFragment extends Fragment {

    IranSansEditText etPass, etRepass;
    String email, code;
    Button btnChangePass;


    public FenterNewPassWordFragment() {
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
        View v = inflater.inflate(R.layout.fragment_fenter_new_pass_word, container, false);

        email = getArguments().getString(Globals.EXTRA_EMAIL);
        code = getArguments().getString(Globals.EXTRA_CODE);

        etPass = v.findViewById(R.id.et_FenterNewPassword_pass);
        etRepass = v.findViewById(R.id.et_FenterNewPassword_rePass);
        btnChangePass = v.findViewById(R.id.btn_FenterNewPassword_changePass);

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etPass.getText().toString().equals("")) {
                    if (etPass.getText().toString().equals(etRepass.getText().toString())) {
                        if (Globals.isPasswordRight(etPass.getText().toString())) {
                            ApiService.getApi().resetPassword(code, email, etPass.getText().toString().trim()).enqueue(new Callback<ResetPasswordResponse>() {
                                @Override
                                public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                                    ResetPasswordResponse resetPasswordResponse = response.body();
                                    if (resetPasswordResponse.getStatus() == 1) {
                                        Toast.makeText(getContext(), R.string.passwordSuccessfullyChanged, Toast.LENGTH_SHORT).show();
                                        getActivity().getSupportFragmentManager().popBackStack();
                                    } else {
                                        Toast.makeText(getContext(), getResources().getString(R.string.errorHappens), Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                                    Toast.makeText(getContext(), getResources().getString(R.string.errorHappens), Toast.LENGTH_SHORT).show();

                                }
                            });
                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.passwordLengthMustBeMoreThanFive), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getContext(), R.string.passwordMustBeLikeEachother, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.pleaseEnterNewPass, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

}
