package com.khayyamapp.juveiran.custom_views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.activity.LoginAndRegisterActivity;

public class LoginOrRegisterCustomDialog extends Dialog {

    IranSansButton login, cancel;
    Context context;

    public LoginOrRegisterCustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_loginorregister);
        login = findViewById(R.id.btn_layoutLoginOrRegister_login);
        cancel = findViewById(R.id.btn_layoutLoginOrRegister_cancelDialog);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, LoginAndRegisterActivity.class));
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


    }
}
