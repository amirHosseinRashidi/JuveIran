package com.khayyamapp.juveiran.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.data_model.LeagueTableDataModel;

import java.util.ArrayList;

public class LeagueTableRvAdapter extends RecyclerView.Adapter<LeagueTableRvAdapter.ViewHolder> {

    private Context c;
    private ArrayList<LeagueTableDataModel> leagueTableList;

    public LeagueTableRvAdapter(Context c, ArrayList<LeagueTableDataModel> leagueTableList) {
        this.c = c;
        this.leagueTableList = leagueTableList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_leauge_table, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

      /*  if (position == 0) {
            holder.tvPlace.setVisibility(View.INVISIBLE);
            holder.imgPlage.setVisibility(View.VISIBLE);
            holder.imgPlage.setImageResource(R.drawable.ic_medal_gold);
        }

        if (position == 1) {
            holder.tvPlace.setVisibility(View.INVISIBLE);
            holder.imgPlage.setVisibility(View.VISIBLE);
            holder.imgPlage.setImageResource(R.drawable.ic_medal_silver);
        }


        if (position == 2) {
            holder.tvPlace.setVisibility(View.INVISIBLE);
            holder.imgPlage.setVisibility(View.VISIBLE);
            holder.imgPlage.setImageResource(R.drawable.ic_medal_boronz);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c, "" + position, Toast.LENGTH_SHORT).show();
            }
        });*/

        holder.tvTeamName.setText(leagueTableList.get(position).getTeamId());
        holder.tvScore.setText(leagueTableList.get(position).getScore() + "");
        holder.tvDifference.setText(leagueTableList.get(position).getDeference() + "");
        holder.tvPlace.setText((position + 1) + "");

    }

    @Override
    public int getItemCount() {
        return leagueTableList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeamName, tvScore, tvPlace, tvDifference;
        ImageView imgPlage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTeamName = itemView.findViewById(R.id.tv_iLeagueTable_teamName);
            tvScore = itemView.findViewById(R.id.tv_iLeagueTable_score);
            tvDifference = itemView.findViewById(R.id.tv_iLeagueTable_difference);
            tvPlace = itemView.findViewById(R.id.tv_iLeagueTable_place);
            imgPlage = itemView.findViewById(R.id.img_iLeagueTable_place);
        }
    }
}
