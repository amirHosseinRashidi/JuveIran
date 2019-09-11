package com.khayyamapp.juveiran.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.api_service.ApiService;
import com.khayyamapp.juveiran.custom_views.IranSansEditText;
import com.khayyamapp.juveiran.custom_views.IranSansTextView;
import com.khayyamapp.juveiran.data_model.SendComment;
import com.khayyamapp.juveiran.globals.Globals;
import com.khayyamapp.juveiran.globals.SpGlobals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendCommentBottomSheet extends BottomSheetDialogFragment {

    private Context context;
    private String articleId;
    IranSansEditText etComment;
    IranSansTextView tvSubmitComment;
    boolean commentIsEmpty = true;
    String shortArticleId;
    SharedPreferences userStats;

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        context = getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.bottomsheet_sendcomment_shortnews, null);
        dialog.setContentView(view);

        userStats = getActivity().getSharedPreferences(SpGlobals.userStatsSpName, Context.MODE_PRIVATE);
        shortArticleId = getArguments().getString(Globals.EXTRA_ARTICLE_ARTICLE_ID);

        etComment = view.findViewById(R.id.et_BSsendComment_comment);
        tvSubmitComment = view.findViewById(R.id.tv_BSsendComment_submitComment);

        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(etComment, 0);
        manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etComment.getText().toString().length() == 0) {
                    tvSubmitComment.setTextColor(getResources().getColor(R.color.soccerColorAccentOff));
                    commentIsEmpty = true;
                } else {
                    tvSubmitComment.setTextColor(getResources().getColor(R.color.colorAccent));
                    commentIsEmpty = false;
                }
            }
        });

        tvSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!commentIsEmpty) {
                    ApiService.getApi().sendSaComment(shortArticleId,
                            etComment.getText().toString(),
                            "Bearer " + userStats.getString(SpGlobals.KEY_USER_TOKEN, " ")
                    ).enqueue(new Callback<SendComment>() {
                        @Override
                        public void onResponse(Call<SendComment> call, Response<SendComment> response) {
                            SendComment sendComment = response.body();

                            Toast.makeText(getContext(), sendComment.getMsg(), Toast.LENGTH_SHORT).show();
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<SendComment> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}
