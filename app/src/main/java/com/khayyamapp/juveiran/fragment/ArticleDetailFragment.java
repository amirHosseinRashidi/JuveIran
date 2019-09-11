package com.khayyamapp.juveiran.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.adapters.CommentRvAdapter;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.custom_views.IranSansButton;
import com.khayyamapp.juveiran.custom_views.IranSansTextView;
import com.khayyamapp.juveiran.custom_views.LoginOrRegisterCustomDialog;
import com.khayyamapp.juveiran.data_model.ArticleDetail;
import com.khayyamapp.juveiran.data_model.Comment;
import com.khayyamapp.juveiran.globals.Globals;
import com.khayyamapp.juveiran.globals.SpGlobals;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.khayyamapp.juveiran.globals.SpGlobals.KEY_USER_IMAGEURL;

public class ArticleDetailFragment extends Fragment {

    String imageUrl;
    String articleId;
    String shareText;
   // boolean isfavorite;

    CommentRvAdapter adapter;
    Context context;
    Fragment fragment;
 //   ArticleDatabaseOpenHelper databaseOpenHelper;

    AppBarLayout appBarLayout;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RoundedImageView imageView;
    IranSansTextView date, views, content, writer, title, toolbarTitle;
    IranSansButton readMore, enterComment;
    RecyclerView rvComments;
    SharedPreferences userStats;

    RelativeLayout rlProgress;

    String articleTitle, articleLink;


    public ArticleDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_article_detail, container, false);
        setHasOptionsMenu(true);

        articleId = getArguments().getString(Globals.EXTRA_ARTICLE_ARTICLE_ID);
        shareText = getArguments().getString(Globals.EXTRA_ARTICLE_ARTICLE_SHARETEXT);
        context = getContext();
        fragment = this;
       // databaseOpenHelper = new ArticleDatabaseOpenHelper(context);
        Log.i("TTTTTTT", "onCreateView: " + articleId);
        //isfavorite = databaseOpenHelper.getArticleIsFavorited(articleId);


        appBarLayout = v.findViewById(R.id.al_FarticleDetail_appbarLayout);
        collapsingToolbarLayout = v.findViewById(R.id.ctl_FarticleFragment_collapsingToolbarlayout);
        toolbar = v.findViewById(R.id.tb_FarticleDetail_toolbar);
        imageView = v.findViewById(R.id.rImg_FarticleDetail_articleImage);
        date = v.findViewById(R.id.tv_FarticleDetail_date);
        toolbarTitle = v.findViewById(R.id.tv_FarticleDetail_toolbarTitle);
        views = v.findViewById(R.id.tv_FarticleDetail_views);
        writer = v.findViewById(R.id.tv_FarticleDetail_writer);
        title = v.findViewById(R.id.tv_FarticleDetail_title);
        content = v.findViewById(R.id.tv_FarticleDetail_content);
        readMore = v.findViewById(R.id.btn_FarticleDetail_readMore);
        enterComment = v.findViewById(R.id.btn_FarticleDetail_commentInput);
        rvComments = v.findViewById(R.id.rv_FarticleDetail_comments);
        rlProgress = v.findViewById(R.id.rl_FarticleDetail_progress);

        userStats = getActivity().getSharedPreferences(SpGlobals.userStatsSpName, Context.MODE_PRIVATE);

        rlProgress.setVisibility(View.VISIBLE);

        setupToolbar();


        ApiService.getApi().getArticleDetail(articleId).enqueue(new Callback<ArticleDetail>() {
            @Override
            public void onResponse(Call<ArticleDetail> call, Response<ArticleDetail> response) {
                final ArticleDetail articleDetail = response.body();
                if (articleDetail.getId() != null) {

                    AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
                    anim.setDuration(400);
                    rlProgress.startAnimation(anim);
                    rlProgress.setVisibility(View.GONE);

                    articleTitle = articleDetail.getTitle();
                    articleLink = articleDetail.getUrl();
                    imageUrl = articleDetail.getImage();
                    Log.i("ID_TEST", "onCreateView: " + articleDetail.getImage());
                    Glide.with(getActivity()).load(articleDetail.getImage()).into(imageView);

                    String articleTitle = articleDetail.getTitle().replaceAll("-", " ");
                    Log.i("DASHED_REMOVING", "onResponse: " + articleTitle);
                    title.setText(articleTitle);
                    toolbarTitle.setText(articleDetail.getWriter());
                    setHtmltoTextView(articleDetail.getWriter(), writer);
                    setHtmltoTextView(articleDetail.getDate(), date);
                    setHtmltoTextView(articleDetail.getSummery(), content);
                    views.setText(articleDetail.getViews().toString());


                    readMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();

                            bundle.putString(Globals.EXTRA_ARTICLEDETAIL_ARTICLE_URI, articleDetail.getUrl());
                            bundle.putString(Globals.EXTRA_ARTICLEDETAIL_ARTICLE_TITLE, articleDetail.getTitle());
                            ArticleReadMoreFragment readMoreFragment = new ArticleReadMoreFragment();
                            readMoreFragment.setArguments(bundle);

                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fl_AarticleDetail_container, readMoreFragment)
                                    .addToBackStack(null).commit();

                        }
                    });

                    enterComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EnterCommentFragment enterCommentFragment = new EnterCommentFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(Globals.EXTRA_ARTICLE_ARTICLE_ID, articleId);
                            enterCommentFragment.setArguments(bundle);
                            if (userStats.getBoolean(SpGlobals.KEY_USER_LOGIN_STATE, false)) {
                                getActivity()
                                        .getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fl_AarticleDetail_container, enterCommentFragment)
                                        .addToBackStack(null).commit();
                            } else {
                                LoginOrRegisterCustomDialog loginOrRegisterCustomDialog = new LoginOrRegisterCustomDialog(context);
                                loginOrRegisterCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                loginOrRegisterCustomDialog.show();
                            }
                        }
                    });

                    ApiService.getApi().getComments(articleId).enqueue(new Callback<ArrayList<Comment>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {
                            ArrayList<Comment> arrayList = response.body();
                            if (arrayList.get(0).getText() != null) {
                                adapter = new CommentRvAdapter(context, arrayList);
                                rvComments.setAdapter(adapter);
                                rvComments.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                            } else {
                                getActivity().finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {
                            rvComments.setVisibility(View.GONE);
                            v.findViewById(R.id.tv_FarticleDetail_noComments).setVisibility(View.VISIBLE);

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ArticleDetail> call, Throwable t) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZoomImageFragment zoomImageFragment = new ZoomImageFragment();
                Bundle bundle = new Bundle();
                bundle.putString(KEY_USER_IMAGEURL, imageUrl);
                zoomImageFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_AarticleDetail_container, zoomImageFragment).addToBackStack("").commit();
            }
        });

        return v;
    }

    void setHtmltoTextView(String str, TextView textView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(str));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_articledetail_toolbarmenu, menu);
      /*  MenuItem item = menu.getItem(0);
        if (isfavorite) {
            item.setIcon(R.drawable.ic_bookmark_black_24dp);
        } else {
            item.setIcon(R.drawable.ic_bookmark_border_black_24dp);
        }*/
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_FarticleDetail_share:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "JuveIran");
                    String shareMessage = shareText.replaceAll("-", " ");
                    shareMessage = shareMessage + "\n\n" + articleLink;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.fArticleDetail_shareMessage)));
                } catch (Exception e) {
                    //e.toString();
                }
                return true;
           /* case R.id.menu_FarticleDetail_bookmark:
                if (isfavorite) {
                    item.setIcon(R.drawable.ic_bookmark_border_black_24dp);
                    databaseOpenHelper.setArticleIsFavorited(Integer.parseInt(articleId), 0);
                    isfavorite = false;
                } else {
                    item.setIcon(R.drawable.ic_bookmark_black_24dp);
                    databaseOpenHelper.setArticleIsFavorited(Integer.parseInt(articleId), 1);
                    isfavorite = true;
                }*/
            default:
                return true;
        }

    }

    void setupToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                    //  Collapsed
                    toolbar.setTitle(" ");
                    // toolbar.setBackgroundColor(context.getResources().getColor(R.color.soccerColorPrimary));
                    toolbarTitle.setVisibility(View.VISIBLE);
                } else {
                    //toolbar.setBackground(getResources().getDrawable(R.drawable.bc_articledetail_toolbar));
                    toolbar.setTitle(" ");
                    toolbarTitle.setVisibility(View.GONE);
                }
            }
        });
    }

}
