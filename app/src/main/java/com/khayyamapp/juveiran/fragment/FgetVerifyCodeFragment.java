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

public class FgetVerifyCodeFragment extends Fragment {

    EditText etEmailOrNumber;
    Button btnGetVerifyCode;

    public FgetVerifyCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fget_verify_code, container, false);
        etEmailOrNumber = v.findViewById(R.id.et_FgetVerifyCode_email);
        btnGetVerifyCode = v.findViewById(R.id.btn_FgetVerifyCode_send);

        btnGetVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnGetVerifyCode.setEnabled(false);
                btnGetVerifyCode.setAlpha(0.4f);
                ApiService.getApi().getVerifyCode(etEmailOrNumber.getText().toString()).enqueue(new Callback<GetVerifyCode>() {
                    @Override
                    public void onResponse(Call<GetVerifyCode> call, Response<GetVerifyCode> response) {
                        GetVerifyCode getVerifyCode = response.body();
                        btnGetVerifyCode.setEnabled(true);

                        btnGetVerifyCode.setAlpha(1f);
                        if (getVerifyCode != null && getVerifyCode.getStatus() == 1) {

                            FverifyCodeFragment verifyCodeFragment = new FverifyCodeFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(Globals.EXTRA_EMAIL, etEmailOrNumber.getText().toString());
                            verifyCodeFragment.setArguments(bundle);

                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .remove(FgetVerifyCodeFragment.this)
                                    .replace(R.id.rl_FforgotPassword_viewContainer, verifyCodeFragment)
                                    .commit();
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.emailOrPhoneDoesNotExists), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetVerifyCode> call, Throwable t) {
                        btnGetVerifyCode.setEnabled(true);
                        btnGetVerifyCode.setAlpha(1f);
                        Toast.makeText(getContext(), "اینترنت خود را بررسی کنید ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return v;
    }

}
