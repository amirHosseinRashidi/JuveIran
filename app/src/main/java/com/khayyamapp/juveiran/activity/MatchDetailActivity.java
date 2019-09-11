package com.khayyamapp.juveiran.activity;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.adapters.MatchPerRvAdapter;
import com.khayyamapp.juveiran.adapters.MatchStatsRvAdapter;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.custom_views.IranSansTextView;
import com.khayyamapp.juveiran.data_model.MatchesDataModel;
import com.khayyamapp.juveiran.globals.Globals;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchDetailActivity extends AppCompatActivity {
    int position;
    Toolbar toolbar;
    RecyclerView rvStats, rvHomePer, rvAwayPer;
    TextView tvHomeGoals, tvAwayGoals, tvHome, tvAway;
    ImageView imgHome, imgAway;
    IranSansTextView tvToolbarTitle;
    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        c = this;
        position = getIntent().getExtras().getInt(Globals.EXTRA_POSITION);

        toolbar = findViewById(R.id.tb_AmatchDetail_toolbar);
        tvToolbarTitle = findViewById(R.id.tv_AmatchDetail_toolbarTitle);
        rvStats = findViewById(R.id.rv_AmatchDetail_stats);
        rvHomePer = findViewById(R.id.rv_AmatchDetail_homePer);
        rvAwayPer = findViewById(R.id.rv_AmatchDetail_awayPer);
        tvAway = findViewById(R.id.tv_AmatchDetail_awayTeamName);
        tvAwayGoals = findViewById(R.id.tv_AmatchDetail_awayTeamGoals);
        tvHomeGoals = findViewById(R.id.tv_AmatchDetail_homeTeamGoals);
        tvHome = findViewById(R.id.tv_AmatchDetail_homeTeamName);
        imgHome = findViewById(R.id.img_AmatchDetail_homeTeamImage);
        imgAway = findViewById(R.id.img_AmatchDetail_awayTeamImage);

        setupToolbar();

        ApiService.getApi().getMatchStats().enqueue(new Callback<ArrayList<MatchesDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MatchesDataModel>> call, Response<ArrayList<MatchesDataModel>> response) {
                ArrayList<MatchesDataModel> arrayList = response.body();

                rvStats.setLayoutManager(new LinearLayoutManager(c) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
                rvStats.setAdapter(new MatchStatsRvAdapter(c, arrayList, position));

                tvAway.setText(arrayList.get(position).getAway() + "");
                tvAwayGoals.setText(arrayList.get(position).getAwayGoals() + "");
                tvHome.setText(arrayList.get(position).getHome() + "");
                tvHomeGoals.setText(arrayList.get(position).getHomeGoals() + "");
                Glide.with(c).load(arrayList.get(position).getAwaypic()).into(imgAway);
                Glide.with(c).load(arrayList.get(position).getHomepic()).into(imgHome);

                rvHomePer.setLayoutManager(new LinearLayoutManager(c) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
                rvHomePer.setAdapter(new MatchPerRvAdapter(c, arrayList.get(position).getHomePer()));

                rvAwayPer.setLayoutManager(new LinearLayoutManager(c) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
                rvAwayPer.setAdapter(new MatchPerRvAdapter(c, arrayList.get(position).getAwayPer()));


            }

            @Override
            public void onFailure(Call<ArrayList<MatchesDataModel>> call, Throwable t) {

            }
        });

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvToolbarTitle.setText(getIntent().getExtras().getString(Globals.EXTRA_TITLE));

    }
}
