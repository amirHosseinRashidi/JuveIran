package com.khayyamapp.juveiran.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.adapters.MatchResultRvAdapter;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.data_model.MatchesDataModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayedGamesFragment extends Fragment {

    RecyclerView rvMatchesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_played_games, container, false);
        rvMatchesList = v.findViewById(R.id.rv_FplayedGames_gamesList);

        ApiService.getApi().getMatchStats().enqueue(new Callback<ArrayList<MatchesDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MatchesDataModel>> call, Response<ArrayList<MatchesDataModel>> response) {
                ArrayList<MatchesDataModel> arrayList = response.body();
                rvMatchesList.setLayoutManager(new LinearLayoutManager(getContext()));
                rvMatchesList.setAdapter(new MatchResultRvAdapter(getContext(), arrayList));

            }

            @Override
            public void onFailure(Call<ArrayList<MatchesDataModel>> call, Throwable t) {

            }
        });
        return v;
    }

}
