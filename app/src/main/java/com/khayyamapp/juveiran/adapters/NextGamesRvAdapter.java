package com.khayyamapp.juveiran.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.data_model.GamesDataModel;

import java.util.ArrayList;

public class NextGamesRvAdapter extends RecyclerView.Adapter<NextGamesRvAdapter.ViewHoler> {

    private Context c;
    private ArrayList<GamesDataModel> arrayList;

    private int section = 0;

    public NextGamesRvAdapter(Context c, ArrayList<GamesDataModel> arrayList) {
        this.c = c;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View v = LayoutInflater.from(c).inflate(R.layout.item_next_game_top, parent, false);
            return new ViewHoler(v);
        } else {
            View v = LayoutInflater.from(c).inflate(R.layout.item_nex_games, parent, false);
            return new ViewHoler(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            section = 0;
        } else {
            section = 1;
        }
        return section;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {


        Glide.with(c).load(arrayList.get(position).getGuestpic()).into(holder.imgAwayLogo);
        Glide.with(c).load(arrayList.get(position).getHomepic()).into(holder.imgHomeLogo);

        holder.tvAwayTeamName.setText(arrayList.get(position).getGuest());
        holder.tvHomeTeamName.setText(arrayList.get(position).getHome());
        holder.tvDate.setText(arrayList.get(position).getDate());
        holder.tvTime.setText(" ساعت " + arrayList.get(position).getTime());
        holder.tvStadium.setText(arrayList.get(position).getStudiom());
        holder.tvLeagueName.setText(arrayList.get(position).getLeague());
        if (section == 0) {
            Glide.with(c).load(arrayList.get(position).getStudiompic()).into(holder.stadiumPic);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView tvHomeTeamName, tvAwayTeamName, tvDate, tvTime, tvStadium, tvLeagueName;
        ImageView imgHomeLogo, imgAwayLogo, stadiumPic;

        public ViewHoler(View itemView) {
            super(itemView);

            tvHomeTeamName = itemView.findViewById(R.id.tv_iNextGames_homeName);
            tvAwayTeamName = itemView.findViewById(R.id.tv_iNextGames_awayName);
            tvDate = itemView.findViewById(R.id.tv_iNextGames_gameDate);
            tvTime = itemView.findViewById(R.id.tv_iNextGames_gameTime);
            tvStadium = itemView.findViewById(R.id.tv_iNextGames_stadium);
            tvLeagueName = itemView.findViewById(R.id.tv_iNextGames_leagueName);
            imgAwayLogo = itemView.findViewById(R.id.img_iNextGames_awayLogo);
            imgHomeLogo = itemView.findViewById(R.id.img_iNextGames_homeLogo);
            if (section == 0) {
                stadiumPic = itemView.findViewById(R.id.img_iNextGames_stadiumPic);
            }

        }
    }
}
