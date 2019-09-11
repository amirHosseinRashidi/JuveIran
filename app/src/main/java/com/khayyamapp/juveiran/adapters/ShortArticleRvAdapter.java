package com.khayyamapp.juveiran.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.activity.ShortArticleCommentsActivity;
import com.khayyamapp.juveiran.activity.ShortArticleZoomImageActivity;
import com.khayyamapp.juveiran.custom_views.IranSansTextView;
import com.khayyamapp.juveiran.custom_views.LoginOrRegisterCustomDialog;
import com.khayyamapp.juveiran.data_model.ShortArticle;
import com.khayyamapp.juveiran.fragment.SendCommentBottomSheet;
import com.khayyamapp.juveiran.globals.SpGlobals;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import static com.khayyamapp.juveiran.globals.Globals.EXTRA_ARTICLE_ARTICLE_ID;

public class ShortArticleRvAdapter extends RecyclerView.Adapter<ShortArticleRvAdapter.ViewHolder> {

    private boolean commentIsEmpty = true;
    private ArrayList<ShortArticle> shortArticles;
    private Context c;
    SharedPreferences userStats;

    public ShortArticleRvAdapter(ArrayList<ShortArticle> shortArticles, Context c) {
        this.shortArticles = shortArticles;
        this.c = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_shortnews, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        userStats = c.getSharedPreferences(SpGlobals.userStatsSpName, Context.MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvContent.setText(Html.fromHtml(shortArticles.get(position).getText(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvContent.setText(Html.fromHtml(shortArticles.get(position).getText()));
        }

        holder.tvWriter.setText(shortArticles.get(position).getWriter());
        holder.tvDate.setText(shortArticles.get(position).getDate());
        RequestOptions requestOptions = new RequestOptions();

        requestOptions.placeholder(c.getResources().getDrawable(R.drawable.bc_placeholder));

        Glide.with(c).setDefaultRequestOptions(requestOptions).load(shortArticles.get(position).getImage()).into(holder.imgArticleImage);
        holder.imgShareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "JuveIran");


                    Spanned shareMessage = Html.fromHtml(shortArticles.get(position).getSharetext().replaceAll("-", " "));
                    // shareMessage = shareMessage + "\n\n" + articleLink;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    c.startActivity(Intent.createChooser(shareIntent, c.getString(R.string.fArticleDetail_shareMessage)));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });

        holder.llSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userStats.getBoolean(SpGlobals.KEY_USER_LOGIN_STATE, false)) {
                    SendCommentBottomSheet sendCommentBottomSheet = new SendCommentBottomSheet();
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRA_ARTICLE_ARTICLE_ID, String.valueOf(shortArticles.get(position).getId()));
                    sendCommentBottomSheet.setArguments(bundle);
                    sendCommentBottomSheet.show(((AppCompatActivity) c).getSupportFragmentManager(), "sendComment");
                } else {
                    LoginOrRegisterCustomDialog loginOrRegisterCustomDialog = new LoginOrRegisterCustomDialog(c);
                    loginOrRegisterCustomDialog.show();
                }
            }
        });

        holder.imgBtnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, ShortArticleCommentsActivity.class);
                intent.putExtra(EXTRA_ARTICLE_ARTICLE_ID,String.valueOf(shortArticles.get(position).getId()));
                c.startActivity(intent);
            }
        });

        holder.imgArticleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!shortArticles.get(position).getImage().equals("storage/images/article_img/NoImage_thumb.jpg")) {
                    Intent intent = new Intent(c, ShortArticleZoomImageActivity.class);
                    intent.putExtra(SpGlobals.KEY_USER_IMAGEURL, shortArticles.get(position).getImage());

                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            (Activity) c, holder.imgArticleImage, "shortNewsImage");

                    c.startActivity(intent, activityOptionsCompat.toBundle());
                    Log.i("Teeest", "onCreate: " + shortArticles.get(position).getImage());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return shortArticles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView imgArticleImage;
        IranSansTextView tvWriter;
        IranSansTextView tvDate;
        IranSansTextView tvContent;

        LinearLayout llSendComment;

        ImageView imgShareIcon;
        ImageButton imgBtnComments;


        public ViewHolder(View itemView) {
            super(itemView);
            imgArticleImage = itemView.findViewById(R.id.rimg_iShortNews_image);
            tvWriter = itemView.findViewById(R.id.tv_iShortNews_writer);
            tvDate = itemView.findViewById(R.id.tv_iShortNews_date);
            tvContent = itemView.findViewById(R.id.tv_iShortNews_content);
            imgShareIcon = itemView.findViewById(R.id.img_iShortNews_shareIcon);
            llSendComment = itemView.findViewById(R.id.ll_iShortNews_sendComment);
            imgBtnComments = itemView.findViewById(R.id.imgBtn_iShortNews_commetns);
        }
    }
}
