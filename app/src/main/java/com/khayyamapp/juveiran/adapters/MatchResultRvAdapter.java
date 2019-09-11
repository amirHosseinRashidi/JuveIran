package com.khayyamapp.juveiran.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.activity.MatchDetailActivity;
import com.khayyamapp.juveiran.data_model.MatchesDataModel;
import com.khayyamapp.juveiran.globals.Globals;

import java.util.ArrayList;

public class MatchResultRvAdapter extends RecyclerView.Adapter<MatchResultRvAdapter.ViewHolder> {

    private Context c;
    private ArrayList<MatchesDataModel> arrayList;

    public MatchResultRvAdapter(Context c, ArrayList<MatchesDataModel> arrayList) {
        this.c = c;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_match_result, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(c).load(arrayList.get(position).getAwaypic()).into(holder.imgAwayTeamLogo);
        Glide.with(c).load(arrayList.get(position).getHomepic()).into(holder.imgHomeTeamLogo);

        holder.tvHomeName.setText(arrayList.get(position).getHome());
        holder.tvAwayName.setText(arrayList.get(position).getAway());
        holder.tvAwayGoals.setText(arrayList.get(position).getAwayGoals() + "");
        holder.tvHomeGoals.setText(arrayList.get(position).getHomeGoals() + "");
        holder.tvDate.setText(arrayList.get(position).getDatetime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, MatchDetailActivity.class);
                intent.putExtra(Globals.EXTRA_POSITION, position);
                intent.putExtra(Globals.EXTRA_TITLE, arrayList.get(position).getDatetime());
                c.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHomeTeamLogo, imgAwayTeamLogo;
        TextView tvHomeGoals, tvAwayGoals, tvDate, tvHomeName, tvAwayName;
        public ViewHolder(View itemView) {
            super(itemView);
            imgAwayTeamLogo = itemView.findViewById(R.id.img_iMatchResult_awayTeamImage);
            imgHomeTeamLogo = itemView.findViewById(R.id.img_iMatchResult_homeTeamImage);

            tvAwayName = itemView.findViewById(R.id.tv_iMatchResult_awayTeamNAme);
            tvHomeName = itemView.findViewById(R.id.tv_iMatchResult_homeTeamNAme);

            tvAwayGoals = itemView.findViewById(R.id.tv_iMatchResult_awayGoals);
            tvHomeGoals = itemView.findViewById(R.id.tv_iMatchResult_homeTeamGoals);

            tvDate = itemView.findViewById(R.id.tv__iMatchResult_date);

        }
    }
}
