package com.khayyamapp.juveiran.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.adapters.ShortArticleRvAdapter;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.data_model.ShortArticle;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShortNewsFragment extends Fragment {
    RecyclerView rvShortNews;
    Context c;
    ArrayList<ShortArticle> arrayList;
    ShortArticleRvAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public ShortNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_short_news, container, false);

        rvShortNews = v.findViewById(R.id.rv_FshortNews_newsList);
        swipeRefreshLayout = v.findViewById(R.id.srl_FshortNews_swipeLayout);
        c = getContext();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadApi();
            }
        });
        swipeRefreshLayout.setRefreshing(true);

        loadApi();

        return v;
    }

    void loadApi() {
        ApiService.getApi().getShortArticles().enqueue(new Callback<ArrayList<ShortArticle>>() {
            @Override
            public void onResponse(Call<ArrayList<ShortArticle>> call, Response<ArrayList<ShortArticle>> response) {
                arrayList = response.body();

                adapter = new ShortArticleRvAdapter(arrayList, c);


                rvShortNews.setAdapter(adapter);
                rvShortNews.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false));
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ArrayList<ShortArticle>> call, Throwable t) {
                Toast.makeText(c, R.string.api_error_respons, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

}
