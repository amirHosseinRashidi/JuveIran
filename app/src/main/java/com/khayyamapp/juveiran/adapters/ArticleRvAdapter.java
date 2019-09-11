package com.khayyamapp.juveiran.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.activity.ArticleDetailActivity;
import com.khayyamapp.juveiran.custom_views.IranSansTextView;
import com.khayyamapp.juveiran.data_model.Article;
import com.khayyamapp.juveiran.database.ArticleDatabaseOpenHelper;
import com.khayyamapp.juveiran.globals.Globals;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class ArticleRvAdapter extends RecyclerView.Adapter<ArticleRvAdapter.ViewHolder> {

    private ArrayList<Article> articles;
    private Context c;
    ArticleDatabaseOpenHelper openHelper;

    public ArticleRvAdapter(ArrayList<Article> articles, Context c) {
        this.articles = articles;
        this.c = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_article, parent, false);
        openHelper = new ArticleDatabaseOpenHelper(c);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.content.setText(Html.fromHtml(articles.get(position).getTitle(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.content.setText(Html.fromHtml(articles.get(position).getTitle()));
        }
//
//        if (articles.get(position).getIsViewed() == 1) {
//            holder.content.setTextColor(c.getResources().getColor(R.color.soccerColorGrey));
//        }
        holder.views.setText(articles.get(position).getViews().toString());
        holder.date.setText(articles.get(position).getDate());

        RequestOptions requestOptions = new RequestOptions();

        requestOptions.placeholder(c.getResources().getDrawable(R.drawable.bc_placeholder));

        Glide.with(c).setDefaultRequestOptions(requestOptions).load(articles.get(position).getImage()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, ArticleDetailActivity.class);
                intent.putExtra(Globals.EXTRA_ARTICLE_ARTICLE_ID, String.valueOf(articles.get(position).getId()));
                intent.putExtra(Globals.EXTRA_ARTICLE_ARTICLE_SHARETEXT, String.valueOf(articles.get(position).getSharetext()));
                c.startActivity(intent);
//                openHelper.setArticleIsVisited(articles.get(position).getId(), 1);
//                holder.content.setTextColor(c.getResources().getColor(R.color.soccerColorGrey));
//                Log.i("db_test", "onClick: "+articles.get(position).getIsViewed());
            }
        });


    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        IranSansTextView content, date, views;
        RoundedImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.tv_iArticle_content);
            date = itemView.findViewById(R.id.tv_iArticle_date);
            views = itemView.findViewById(R.id.tv_iArticle_views);
            imageView = itemView.findViewById(R.id.rimg_iArticle_image);
        }
    }
}
