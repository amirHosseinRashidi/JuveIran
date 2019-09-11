package com.khayyamapp.juveiran.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.khayyamapp.juveiran.R;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> settingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        settingList.add(getResources().getString(R.string.aboutUs));
        settingList.add(getString(R.string.notificationSetting));
        listView = findViewById(R.id.lvAsetting_settingList);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.item_simple_list, settingList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                        break;
                    case 1:
                        Intent intent = new Intent();
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent.putExtra("app_package",getPackageName());
                        intent.putExtra("app_uid",getApplicationInfo().uid);

                        intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
                        startActivity(intent);
                        break;
                }
            }
        });

    }
}
