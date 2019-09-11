package com.khayyamapp.juveiran.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.adapters.LeagueTableRvAdapter;
import com.khayyamapp.juveiran.api_service.ApiInterface;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.data_model.LeagueTableDataModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeagueTableFragment extends Fragment {

    RecyclerView rvTable;
    LeagueTableRvAdapter leagueTableRvAdapter;
    ArrayList<LeagueTableDataModel> leagueTable = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_league_table, container, false);

        rvTable = v.findViewById(R.id.rv_FleagueTable_table);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rvTable.setLayoutManager(llm);
        leagueTableRvAdapter = new LeagueTableRvAdapter(getContext(), leagueTable);
        rvTable.setAdapter(leagueTableRvAdapter);

        ApiService.getApi().getLeagueTable().enqueue(new Callback<ArrayList<LeagueTableDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<LeagueTableDataModel>> call, Response<ArrayList<LeagueTableDataModel>> response) {
                leagueTable.addAll(response.body());
                leagueTableRvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<LeagueTableDataModel>> call, Throwable t) {

            }
        });

        return v;
    }

}
