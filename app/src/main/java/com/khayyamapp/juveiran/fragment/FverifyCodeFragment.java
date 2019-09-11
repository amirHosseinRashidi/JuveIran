package com.khayyamapp.juveiran.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.data_model.GetVerifyCode;
import com.khayyamapp.juveiran.globals.Globals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FverifyCodeFragment extends Fragment {

    EditText etVerifyCode;
    Button btnVerifyCode;
    String email, code;

    public FverifyCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fverify_code, container, false);
        etVerifyCode = v.findViewById(R.id.et_FverifyCode_code);
        btnVerifyCode = v.findViewById(R.id.btn_FverifyCode_verify);
        email = getArguments().getString(Globals.EXTRA_EMAIL, "");
        code = getArguments().getString(Globals.EXTRA_CODE, "");

        btnVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVerifyCode.setEnabled(false);
                btnVerifyCode.setAlpha(0.4f);
                ApiService.getApi().checkVerifyCode(etVerifyCode.getText().toString(), email).enqueue(new Callback<GetVerifyCode>() {
                    @Override
                    public void onResponse(Call<GetVerifyCode> call, Response<GetVerifyCode> response) {
                        GetVerifyCode getVerifyCode = response.body();
                        btnVerifyCode.setEnabled(true);
                        btnVerifyCode.setAlpha(1f);
                        if (getVerifyCode != null && getVerifyCode.getStatus() == 1) {
                            FenterNewPassWordFragment newPassWordFragment = new FenterNewPassWordFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(Globals.EXTRA_EMAIL, email);
                            bundle.putString(Globals.EXTRA_CODE, etVerifyCode.getText().toString());
                            newPassWordFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.rl_FforgotPassword_viewContainer, newPassWordFragment)
                                    .remove(FverifyCodeFragment.this)
                                    .commit();
                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.codeIsNotCorrect), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetVerifyCode> call, Throwable t) {
                        btnVerifyCode.setEnabled(true);
                        btnVerifyCode.setAlpha(1f);
                    }
                });
            }
        });

        return v;
    }

}
