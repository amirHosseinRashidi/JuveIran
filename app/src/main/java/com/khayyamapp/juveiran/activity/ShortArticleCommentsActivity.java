package com.khayyamapp.juveiran.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.adapters.ShortArticlesCommentRvAdapter;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.custom_views.IranSansEditText;
import com.khayyamapp.juveiran.custom_views.IranSansTextView;
import com.khayyamapp.juveiran.custom_views.LoginOrRegisterCustomDialog;
import com.khayyamapp.juveiran.data_model.SendComment;
import com.khayyamapp.juveiran.data_model.ShortArticleComment;
import com.khayyamapp.juveiran.globals.Globals;
import com.khayyamapp.juveiran.globals.SpGlobals;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.khayyamapp.juveiran.globals.Globals.EXTRA_ARTICLE_ARTICLE_ID;
import static com.khayyamapp.juveiran.globals.Globals.hideKeyboard;
import static com.khayyamapp.juveiran.globals.SpGlobals.KEY_USER_LOGIN_STATE;
import static com.khayyamapp.juveiran.globals.SpGlobals.userStatsSpName;

public class ShortArticleCommentsActivity extends AppCompatActivity {

    boolean commentIsEmpty = true;

    Toolbar toolbar;
    IranSansEditText etComment;
    IranSansTextView tvSubmitComment;
    RecyclerView recyclerView;
    String shortArticleId;
    SharedPreferences userStats;
    TextView tvNoComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_article_comments);

        userStats = getSharedPreferences(userStatsSpName, MODE_PRIVATE);

        shortArticleId = getIntent().getExtras().getString(EXTRA_ARTICLE_ARTICLE_ID, "0");


        toolbar = findViewById(R.id.tb_AshortArticleComments_toolbar);
        etComment = findViewById(R.id.et_AshortArticleComments_comment);
        tvSubmitComment = findViewById(R.id.tv_AshortArticleComments_submitComment);
        recyclerView = findViewById(R.id.rv_AshortArticleComments_commentList);
        tvNoComments = findViewById(R.id.tv_AshortArticleComments_noComments);

        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(etComment, 0);
        manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        setupToolbar();
        setupRvComments();

        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etComment.getText().toString().length() == 0) {
                    tvSubmitComment.setTextColor(getResources().getColor(R.color.soccerColorAccentOff));
                    commentIsEmpty = true;
                } else {
                    tvSubmitComment.setTextColor(getResources().getColor(R.color.colorAccent));
                    commentIsEmpty = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tvSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!commentIsEmpty) {

                    if (userStats.getBoolean(KEY_USER_LOGIN_STATE, false)) {
                        ApiService.getApi().sendSaComment(shortArticleId,
                                etComment.getText().toString(),
                                "Bearer " + userStats.getString(SpGlobals.KEY_USER_TOKEN, " ")
                        ).enqueue(new Callback<SendComment>() {
                            @Override
                            public void onResponse(Call<SendComment> call, Response<SendComment> response) {
                                SendComment sendComment = response.body();
                                if (sendComment != null) {
                                    Toast.makeText(ShortArticleCommentsActivity.this, sendComment.getMsg(), Toast.LENGTH_SHORT).show();
                                    hideKeyboard(ShortArticleCommentsActivity.this);
                                    etComment.setText("");
                                } else {
                                    Toast.makeText(ShortArticleCommentsActivity.this, getResources().getString(R.string.errorHappens), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<SendComment> call, Throwable t) {

                            }
                        });
                    } else {
                        LoginOrRegisterCustomDialog loginOrRegisterCustomDialog = new LoginOrRegisterCustomDialog(ShortArticleCommentsActivity.this);
                        loginOrRegisterCustomDialog.show();
                    }
                }
            }
        });


    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        toolbar.setTitle(" ");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupRvComments() {
        final String id = getIntent().getExtras().getString(Globals.EXTRA_ARTICLE_ARTICLE_ID, "");
        ApiService.getApi().getShortArticleComments(id).enqueue(new Callback<ArrayList<ShortArticleComment>>() {
            @Override
            public void onResponse(Call<ArrayList<ShortArticleComment>> call, Response<ArrayList<ShortArticleComment>> response) {
                ArrayList<ShortArticleComment> arrayList = response.body();
                ShortArticlesCommentRvAdapter adapter = new ShortArticlesCommentRvAdapter(ShortArticleCommentsActivity.this, arrayList);
                recyclerView.setLayoutManager(new LinearLayoutManager(ShortArticleCommentsActivity.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
                Log.i("THE_TEST", "onResponse: " + arrayList.size());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ArrayList<ShortArticleComment>> call, Throwable t) {
                tvNoComments.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });
    }
}
