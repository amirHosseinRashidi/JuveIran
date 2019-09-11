package com.khayyamapp.juveiran.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.adapters.CatarticleRvAdapter;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.custom_views.IranSansTextView;
import com.khayyamapp.juveiran.data_model.Article;
import com.khayyamapp.juveiran.data_model.Catarticle;
import com.khayyamapp.juveiran.globals.Globals;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatarticleActivity extends AppCompatActivity {

    //  ArticleDatabaseOpenHelper databaseOpenHelper;
    Context context;
    ArrayList<Article> articleArrayList;


    String category;
    RecyclerView recyclerView;
    Toolbar toolbar;
    IranSansTextView toolbarTitle;
    RelativeLayout rlProgress;
    ArrayList<Catarticle> arrayList;
    SwipeRefreshLayout swipeRefreshLayout;
    CatarticleRvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catarticle);
        context = this;
        category = getIntent().getExtras().getString(Globals.EXTRA_ARTICLE_ARTICLE_ID);
        // databaseOpenHelper = new ArticleDatabaseOpenHelper(context);

        recyclerView = findViewById(R.id.rv_Acatarticle_list);
        toolbar = findViewById(R.id.tb_Acatarticle_toolbar);
        toolbarTitle = findViewById(R.id.tv_Acatarticle_toolbarTitle);
        swipeRefreshLayout = findViewById(R.id.srl_AcatArticle_swipeLayout);
        setupToolbar();
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadApi();
            }
        });
        loadApi();
    }

    void loadApi() {
        swipeRefreshLayout.setRefreshing(true);
        ApiService.getApi().getCatarticles(category).enqueue(new Callback<ArrayList<Catarticle>>() {
            @Override
            public void onResponse(Call<ArrayList<Catarticle>> call, Response<ArrayList<Catarticle>> response) {
                try {
                    arrayList = response.body();
                    if (response.isSuccessful() && response.body() != null) {

                        arrayList = response.body();
                        adapter = new CatarticleRvAdapter(context, arrayList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
                        //insert api to data base
                        /*articleArrayList = convertToArticles(arrayList);
                        Log.i("THE_FFF", "onResponse: " + articleArrayList.size());
                        Log.i("THE_FFF", "onResponse: " + articleArrayList.get(0).getId());
                        Log.i("THE_FFF", "onResponse: " + articleArrayList.get(0).getTitle());
                        Log.i("THE_FFF", "onResponse: " + articleArrayList.get(0).getImage());
                        Log.i("THE_FFF", "onResponse: " + articleArrayList.get(0).getViews());
                        Log.i("THE_FFF", "onResponse: " + articleArrayList.get(0).getIsViewed());
                        Log.i("THE_FFF", "onResponse: " + articleArrayList.get(0).getIsFavorite());
                        Log.i("THE_FFF", "onResponse: " + articleArrayList.get(0).getCategory());
                        Log.i("THE_FFF", "onResponse: " + articleArrayList.get(0).getDate());
                        databaseOpenHelper.addPosts(articleArrayList, category);
                        */
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Catarticle>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(context, R.string.api_error_respons, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        toolbarTitle.setText(getIntent().getExtras().getString(Globals.EXTRA_ARTICLEDETAIL_ARTICLE_TITLE));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private ArrayList<Article> convertToArticles(ArrayList<Catarticle> catarticles) {
        Article article = new Article();
        ArrayList<Article> articles = new ArrayList<>();
        for (int i = 0; i < catarticles.size(); i++) {
            article.setId(catarticles.get(i).getId());
            article.setTitle(catarticles.get(i).getTitle());
            article.setImage(catarticles.get(i).getImage());
            article.setViews(catarticles.get(i).getViews());
            article.setDate(catarticles.get(i).getDate());
            article.setCategory(category);
            articles.add(article);
        }
        return articles;
    }


    //use this for read from data base
//    private void loadArticles() {
//        articleArrayList = databaseOpenHelper.getAllPosts(category);
//        adapter = new ArticleRvAdapter(articleArrayList, context);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        adapter.notifyDataSetChanged();
//        swipeRefreshLayout.setRefreshing(false);
//    }
}
