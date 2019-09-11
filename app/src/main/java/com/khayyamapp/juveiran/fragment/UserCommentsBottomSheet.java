package com.khayyamapp.juveiran.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.adapters.UserCommentRvAdapter;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.data_model.user_commetns.UserComments;
import com.khayyamapp.juveiran.data_model.user_commetns.UserCommentsDatum;
import com.khayyamapp.juveiran.globals.SpGlobals;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserCommentsBottomSheet extends BottomSheetDialogFragment {

    ArrayList<UserCommentsDatum> commentsArrayList;
    ArrayList<UserCommentsDatum> saCommentsArrayList;
    RecyclerView rvComments, rvSaComments;
    SharedPreferences userStats;
    Context context;
    UserCommentRvAdapter commentRvAdapter, saCommentRvAdapter;

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        context = getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.bsfragment_usercomments, null);
        dialog.setContentView(view);

        rvComments = view.findViewById(R.id.rv_bsFuserArticles_commentList);
        rvSaComments = view.findViewById(R.id.rv_bsFuserArticles_saCommentList);
        userStats = getActivity().getSharedPreferences(SpGlobals.userStatsSpName, Context.MODE_PRIVATE);
        final String token = "Bearer " + userStats.getString(SpGlobals.KEY_USER_TOKEN, "");
        ApiService.getApi().getUserComments(token).enqueue(new Callback<UserComments>() {
            @Override
            public void onResponse(Call<UserComments> call, Response<UserComments> response) {
                UserComments userComments = response.body();
                commentsArrayList = userComments.getData();

                if (commentsArrayList != null) {
                    commentRvAdapter = new UserCommentRvAdapter(context, commentsArrayList);
                    rvComments.setAdapter(commentRvAdapter);
                    rvComments.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

                    ApiService.getApi().getUserSaComments(token).enqueue(new Callback<UserComments>() {
                        @Override
                        public void onResponse(Call<UserComments> call, Response<UserComments> response) {
                            UserComments userSaComments = response.body();
                            if (response.isSuccessful() && userSaComments != null) {
                                saCommentsArrayList = userSaComments.getData();
                                saCommentRvAdapter = new UserCommentRvAdapter(context, saCommentsArrayList);
                                rvSaComments.setAdapter(saCommentRvAdapter);
                                rvSaComments.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                            }
                        }

                        @Override
                        public void onFailure(Call<UserComments> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<UserComments> call, Throwable t) {

            }
        });

    }
}
