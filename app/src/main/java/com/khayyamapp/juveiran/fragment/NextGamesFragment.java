package com.khayyamapp.juveiran.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.adapters.NextGamesRvAdapter;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.data_model.GamesDataModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NextGamesFragment extends Fragment {

    RecyclerView rvNextGames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_next_games, container, false);

        rvNextGames = v.findViewById(R.id.rv_FnextGames_gamesList);

        ApiService.getApi().getGames().enqueue(new Callback<ArrayList<GamesDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GamesDataModel>> call, Response<ArrayList<GamesDataModel>> response) {
                NextGamesRvAdapter adapter = new NextGamesRvAdapter(getContext(), response.body());
                rvNextGames.setLayoutManager(new LinearLayoutManager(getContext()));
                rvNextGames.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<GamesDataModel>> call, Throwable t) {

            }
        });


        return v;
    }

}
