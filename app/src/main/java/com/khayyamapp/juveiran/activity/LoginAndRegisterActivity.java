package com.khayyamapp.juveiran.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.fragment.LoginOrRegisterFragment;
import com.khayyamapp.juveiran.globals.SpGlobals;

public class LoginAndRegisterActivity extends AppCompatActivity {

    SharedPreferences spUserStat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);
        spUserStat = getSharedPreferences(SpGlobals.userStatsSpName, MODE_PRIVATE);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        if (spUserStat.getBoolean(SpGlobals.KEY_USER_LOGIN_STATE, false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.rl_AloginAndRegister_container, new LoginOrRegisterFragment());
        transaction.commit();


    }
}
