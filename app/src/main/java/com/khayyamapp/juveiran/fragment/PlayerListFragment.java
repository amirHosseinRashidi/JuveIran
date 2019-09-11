package com.khayyamapp.juveiran.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.adapters.PlayerListPagerAdapter;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.custom_views.VerticalViewPager;
import com.khayyamapp.juveiran.data_model.PlayersListDataModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerListFragment extends Fragment {
    VerticalViewPager vvpPlayerPager;
    ArrayList<PlayersListDataModel> playerList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player_list, container, false);
        vvpPlayerPager = v.findViewById(R.id.vvp_FplayerList_playerPager);
        ApiService.getApi().getPlayerList().enqueue(new Callback<ArrayList<PlayersListDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PlayersListDataModel>> call, Response<ArrayList<PlayersListDataModel>> response) {
                playerList = response.body();
                if (getActivity() != null) {
                    PlayerListPagerAdapter pagerAdapter = new PlayerListPagerAdapter(getActivity().getSupportFragmentManager(), playerList);
                    vvpPlayerPager.setAdapter(pagerAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PlayersListDataModel>> call, Throwable t) {

            }
        });

        return v;
    }

}
