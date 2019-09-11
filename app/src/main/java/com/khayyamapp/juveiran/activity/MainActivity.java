package com.khayyamapp.juveiran.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.custom_views.CustomTypefaceSpan;
import com.khayyamapp.juveiran.fragment.LeagueTableFragment;
import com.khayyamapp.juveiran.fragment.MatchResultsFragment;
import com.khayyamapp.juveiran.fragment.NewsFragment;
import com.khayyamapp.juveiran.fragment.PlayerListFragment;
import com.khayyamapp.juveiran.globals.SpGlobals;
import com.makeramen.roundedimageview.RoundedImageView;

import static com.khayyamapp.juveiran.globals.SpGlobals.KEY_USER_EDIT_STATE;

public class MainActivity extends AppCompatActivity {


    SharedPreferences spUserDetail;
    Button btnSite;
    FrameLayout flTabs;
    BottomNavigationViewEx bottomNavigationViewEx;
    RoundedImageView imgUserIcon;
    Toolbar toolbar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_toolbarmenu, menu);

        if (spUserDetail.getBoolean(SpGlobals.KEY_USER_LOGIN_STATE, false)) {
            Glide.with(this).load(spUserDetail.getString(SpGlobals.KEY_USER_IMAGEURL, "")).into(imgUserIcon);
            imgUserIcon.setVisibility(View.VISIBLE);
            menu.getItem(0).setVisible(false);
        } else {
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_Amain_account:
                Intent intent;

                if (!spUserDetail.getBoolean(SpGlobals.KEY_USER_LOGIN_STATE, false)) {
                    intent = new Intent(this, LoginAndRegisterActivity.class);
                    startActivity(intent);
                }

                return true;

            case R.id.menu_Amain_setting:
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationViewEx = findViewById(R.id.bnv_Amain_bottomMenu);


        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        spUserDetail = getSharedPreferences(SpGlobals.userStatsSpName, MODE_PRIVATE);
        toolbar = findViewById(R.id.tb_Amain_toolbar);
        btnSite = findViewById(R.id.tv_Amain_toolbarSite);
        flTabs = findViewById(R.id.fl_Amain_tabsContainer);
        imgUserIcon = findViewById(R.id.rImg_Amain_userIcon);
        setupBottomNavigation();

        imgUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateProfileActivity.class);
                startActivity(intent);
            }
        });
        btnSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://JuveIran.ir");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        //setting up toolbar
        setupToolbar();

        fireBaseCodes();

        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                switch (item.getItemId()) {
                    case R.id.menu_AmainBottomnavigation_gameResults:
                        fragmentTransaction
                                .replace(R.id.fl_Amain_tabsContainer, new MatchResultsFragment()).commit();
                        return true;

                    case R.id.menu_AmainBottomnavigation_news:
                        fragmentTransaction
                                .replace(R.id.fl_Amain_tabsContainer, new NewsFragment()).commit();
                        return true;


                    case R.id.menu_AmainBottomnavigation_leagueTable:
                        fragmentTransaction
                                .replace(R.id.fl_Amain_tabsContainer, new LeagueTableFragment()).commit();
                        return true;


                    case R.id.menu_AmainBottomnavigation_playerStats:
                        fragmentTransaction
                                .replace(R.id.fl_Amain_tabsContainer, new PlayerListFragment()).commit();
                        return true;

                    default:
                        return false;
                }
            }
        });
        bottomNavigationViewEx.setCurrentItem(2);


    }

    private void fireBaseCodes() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.i("TOKEN_TEST", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        Log.i("TOKEN_TEST", token);
                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.topic_juveiran))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d("FireBaseTest", msg);
                    }
                });

    }

    void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (spUserDetail.getBoolean(KEY_USER_EDIT_STATE, false)) {


            spUserDetail.edit().putBoolean(KEY_USER_EDIT_STATE, false).apply();


        }
    }

    private void setupBottomNavigation() {
        bottomNavigationViewEx.setIconSize(20, 20);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextSize(10);
        Menu m = bottomNavigationViewEx.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/IRAN Sans.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }
}
