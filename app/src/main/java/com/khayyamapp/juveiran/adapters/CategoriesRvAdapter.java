package com.khayyamapp.juveiran.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.activity.CatarticleActivity;
import com.khayyamapp.juveiran.custom_views.IranSansTextView;
import com.khayyamapp.juveiran.data_model.Category;
import com.khayyamapp.juveiran.globals.Globals;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class CategoriesRvAdapter extends RecyclerView.Adapter<CategoriesRvAdapter.ViewHolder> {

    private Context c;
    private ArrayList<Category> categories;

    public CategoriesRvAdapter(Context c, ArrayList<Category> categories) {
        this.c = c;
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_categories, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(c).load(categories.get(position).getImage()).into(holder.imgLogo);
        holder.tvName.setText(categories.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, CatarticleActivity.class);
                i.putExtra(Globals.EXTRA_ARTICLE_ARTICLE_ID, categories.get(position).getId());
                i.putExtra(Globals.EXTRA_ARTICLEDETAIL_ARTICLE_TITLE, categories.get(position).getName());
                c.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imgLogo;
        IranSansTextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.rImg_iCategories_categoryImage);
            tvName = itemView.findViewById(R.id.tv_iCategories_categoryName);

        }

    }
}
