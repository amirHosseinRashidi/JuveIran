package com.khayyamapp.juveiran.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.adapters.UserArticlesRvAdapter;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.data_model.user_articles.DatumUserArticles;
import com.khayyamapp.juveiran.data_model.user_articles.UserArticles;
import com.khayyamapp.juveiran.globals.SpGlobals;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserArticlesBottomSheet extends BottomSheetDialogFragment {

    ArrayList<DatumUserArticles> arrayList;
    RecyclerView recyclerView;
    SharedPreferences userStats;
    Context context;
    UserArticlesRvAdapter adapter;

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        context = getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.bsfragment_userarticles, null);
        dialog.setContentView(view);
        recyclerView = view.findViewById(R.id.rv_bsFuserArticles_articleList);
        userStats = getActivity().getSharedPreferences(SpGlobals.userStatsSpName, Context.MODE_PRIVATE);

        ApiService.getApi().getUserArticles("Bearer "+userStats.getString(SpGlobals.KEY_USER_TOKEN, "")).enqueue(new Callback<UserArticles>() {
            @Override
            public void onResponse(Call<UserArticles> call, Response<UserArticles> response) {
                UserArticles userArticles = response.body();
                Log.i("THE_USER", "onResponse: "+userArticles.getStatus());
                assert response.body() != null;
                arrayList = response.body().getData();
              //  Log.i("THE_USER", "onResponse: "+userStats.getString(SpGlobals.KEY_USER_TOKEN, "www"));
                if (arrayList != null) {
                    adapter = new UserArticlesRvAdapter(arrayList, context);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false
                    ));
                }
            }

            @Override
            public void onFailure(Call<UserArticles> call, Throwable t) {

            }
        });

    }
}
