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
import com.khayyamapp.juveiran.adapters.ArticleRvAdapter;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.data_model.Article;
import com.khayyamapp.juveiran.database.ArticleDatabaseOpenHelper;
import com.khayyamapp.juveiran.globals.Globals;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArticlesFragment extends Fragment {

    RecyclerView rvArticles;
    Context c;
    ArrayList<Article> arrayList;
    ArticleRvAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ArticleDatabaseOpenHelper databaseOpenHelper;

    public ArticlesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_articles, container, false);

        c = getContext();
        databaseOpenHelper = new ArticleDatabaseOpenHelper(getContext());
        rvArticles = v.findViewById(R.id.rv_Farticles_newsList);
        swipeRefreshLayout = v.findViewById(R.id.srl_Farticles_swipeLayout);
        loadApi();
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

    private void loadApi() {
        swipeRefreshLayout.setRefreshing(true);
        ApiService.getApi().getArticles().enqueue(new Callback<ArrayList<Article>>() {
            @Override
            public void onResponse(Call<ArrayList<Article>> call, Response<ArrayList<Article>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    arrayList = response.body();

                    adapter = new ArticleRvAdapter(arrayList, c);
                    rvArticles.setAdapter(adapter);
                    rvArticles.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false));
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);

                    //insert data to database
                    //databaseOpenHelper.addPosts(response.body(), Globals.CATEGORY_ID_HOME);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ArrayList<Article>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(c, R.string.api_error_respons, Toast.LENGTH_SHORT).show();

            }
        });

    }

    //read data from sqlite
    private void loadArticles() {
        arrayList = databaseOpenHelper.getAllPosts(Globals.CATEGORY_ID_HOME);
        adapter = new ArticleRvAdapter(arrayList, c);
        Collections.reverse(arrayList);
        rvArticles.setAdapter(adapter);
        rvArticles.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false));
        adapter.notifyDataSetChanged();

    }
}
