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
import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.activity.ArticleDetailActivity;
import com.khayyamapp.juveiran.custom_views.IranSansTextView;
import com.khayyamapp.juveiran.data_model.Catarticle;
import com.khayyamapp.juveiran.globals.Globals;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class CatarticleRvAdapter extends RecyclerView.Adapter<CatarticleRvAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Catarticle> arrayList;

    public CatarticleRvAdapter(Context context, ArrayList<Catarticle> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.content.setText(Html.fromHtml(arrayList.get(position).getTitle(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.content.setText(Html.fromHtml(arrayList.get(position).getTitle()));
        }

        holder.views.setText(arrayList.get(position).getViews().toString());
        holder.date.setText(arrayList.get(position).getDate());
        Glide.with(context).load(arrayList.get(position).getImage()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ArticleDetailActivity.class);
                i.putExtra(Globals.EXTRA_ARTICLE_ARTICLE_ID, arrayList.get(position).getId().toString());
                context.startActivity(i);
            }
        });

      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ArticleDetailActivity.class);
                intent.putExtra(Globals.EXTRA_ARTICLE_ARTICLE_ID, String.valueOf(arrayList.get(position).getId()));
                context.startActivity(intent);
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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
