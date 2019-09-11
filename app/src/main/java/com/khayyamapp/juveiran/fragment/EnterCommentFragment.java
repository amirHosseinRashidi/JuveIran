package com.khayyamapp.juveiran.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.custom_views.IranSansButton;
import com.khayyamapp.juveiran.custom_views.IranSansEditText;
import com.khayyamapp.juveiran.custom_views.IranSansTextView;
import com.khayyamapp.juveiran.data_model.SendComment;
import com.khayyamapp.juveiran.globals.Globals;
import com.khayyamapp.juveiran.globals.SpGlobals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EnterCommentFragment extends Fragment {

    Toolbar toolbar;
    IranSansTextView toolbarTitle;
    IranSansEditText content;
    IranSansButton enter;
    RelativeLayout rlProgress;

    public EnterCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_enter_comment, container, false);
        toolbar = v.findViewById(R.id.tb_FenterComment_toolbar);
        toolbarTitle = v.findViewById(R.id.tv_FenterComment_toolbarTitle);
        content = v.findViewById(R.id.et_FenterComment_content);
        enter = v.findViewById(R.id.btn_FenterComment_enterComment);
        rlProgress = v.findViewById(R.id.rl_FenterComment_progress);

        final SharedPreferences userStats = getActivity().getSharedPreferences(SpGlobals.userStatsSpName, Context.MODE_PRIVATE);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlProgress.setVisibility(View.VISIBLE);

                ApiService.getApi().sendComment(getArguments().getString(Globals.EXTRA_ARTICLE_ARTICLE_ID), content.getText().toString()
                        , " Bearer " + userStats.getString(SpGlobals.KEY_USER_TOKEN, "error")).enqueue(new Callback<SendComment>() {
                    @Override
                    public void onResponse(Call<SendComment> call, Response<SendComment> response) {
                        rlProgress.setVisibility(View.GONE);
                        SendComment sendComment = response.body();
                        if (sendComment != null) {
                            Toast.makeText(getContext(), sendComment.getMsg(), Toast.LENGTH_SHORT).show();
                            if (sendComment.getStatus() == 1) {
                                getActivity().getSupportFragmentManager().popBackStack();
                            }
                        } else {
                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SendComment> call, Throwable t) {
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();

                        rlProgress.setVisibility(View.GONE);
                    }
                });
            }
        });

        setupToolbar();

        return v;
    }


    void setupToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(" ");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

}
