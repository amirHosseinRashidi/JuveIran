package com.khayyamapp.juveiran.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.data_model.MatchesDataModel;

import java.util.ArrayList;

public class MatchStatsRvAdapter extends RecyclerView.Adapter<MatchStatsRvAdapter.ViewHolder> {

    private Context c;
    private ArrayList<MatchesDataModel> arrayList;
    private int matchPosition;

    public MatchStatsRvAdapter(Context c, ArrayList<MatchesDataModel> arrayList, int matchPosition) {
        this.c = c;
        this.arrayList = arrayList;
        this.matchPosition = matchPosition;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_match_detail, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String homeStatsStr = arrayList.get(matchPosition).getHomeMatchState().get(position).get(1);
        String homeTitleStr = arrayList.get(matchPosition).getHomeMatchState().get(position).get(0);

        String awayStatsStr = arrayList.get(matchPosition).getAwayMatchState().get(position).get(1);


        float homeScore = Float.parseFloat(homeStatsStr.replaceAll("%", ""));
        float awayScore = Float.parseFloat(awayStatsStr.replaceAll("%", ""));

/*

        try {
            homeScore = (homeScore * 100) / homeScore + awayScore;
        } catch (ArithmeticException e) {
            homeScore = 0;
        }
        if () {
            awayScore = (awayScore * 100) / homeScore + awayScore;
        } else {
            awayScore = 0;
        }
*/

        LinearLayout.LayoutParams layoutParamsHome = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, (homeScore == 0) ? 0 : (homeScore * 100) / (homeScore + awayScore));
        if (position == 3) {
            Log.i("WeightTest", "home: " + homeScore);
        }
        View viewHome = new View(c);
        viewHome.setLayoutParams(layoutParamsHome);
        viewHome.setBackgroundColor(c.getResources().getColor(R.color.colorPrimaryDark));
        holder.llRange.addView(viewHome);

        LinearLayout.LayoutParams layoutParamsAway = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, (awayScore == 0) ? 0 : (awayScore * 100) / (awayScore + homeScore));
        if (position == 3) {
            Log.i("WeightTest", "away: " + awayScore);
        }
        View viewAway = new View(c);
        viewAway.setLayoutParams(layoutParamsAway);
        viewAway.setBackgroundColor(c.getResources().getColor(android.R.color.darker_gray));
        holder.llRange.addView(viewAway);

        holder.tvTitle.setText(homeTitleStr);
        holder.tvAwayStats.setText(awayStatsStr);
        holder.tvHomeStats.setText(homeStatsStr);

        if (position == 0) {
            holder.llRange.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.get(matchPosition).getAwayMatchState().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHomeStats, tvAwayStats, tvTitle;
        LinearLayout llRange;
        View divider;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAwayStats = itemView.findViewById(R.id.tv_iMatchDetail_awayTeamScore);
            tvHomeStats = itemView.findViewById(R.id.tv_iMatchDetail_homeTeamScore);
            tvTitle = itemView.findViewById(R.id.tv_iMatchDetail_title);
            llRange = itemView.findViewById(R.id.ll_iMatchDetail_rangeContainer);
            divider = itemView.findViewById(R.id.v_iMatchDetail_divider);

        }
    }
}
