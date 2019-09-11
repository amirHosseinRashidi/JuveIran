package com.khayyamapp.juveiran.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.custom_views.IranSansTextView;
import com.khayyamapp.juveiran.data_model.MatchesDataModel;

import java.util.ArrayList;
import java.util.List;

public class MatchPerRvAdapter extends RecyclerView.Adapter<MatchPerRvAdapter.ViewHolder> {

    private Context c;
    private List<List<String>> arrayList;

    public MatchPerRvAdapter(Context c, List<List<String>> arrayList) {
        this.c = c;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MatchPerRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_match_per, parent, false);
        return new MatchPerRvAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchPerRvAdapter.ViewHolder holder, int position) {
        String str = arrayList.get(position).get(0);
        int iconType = Integer.parseInt(str);
        switch (iconType) {
            case 1:
                holder.imgIcon.setImageResource(R.drawable.ic_ball_black);
                break;
            case 2:
                holder.imgIcon.setImageResource(R.drawable.ic_yellow_card);
                break;
            case 3:
                holder.imgIcon.setImageResource(R.drawable.ic_red_card);
                break;
            case 4:
                holder.imgIcon.setImageResource(R.drawable.ic_arrow_out_black_24dp);
                break;
            case 5:
                holder.imgIcon.setImageResource(R.drawable.ic_arrow_in_24dp);
                break;
        }

        holder.tvName.setText(arrayList.get(position).get(1));
        holder.tvTime.setText("'" + arrayList.get(position).get(2));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        IranSansTextView tvName, tvTime;
        ImageView imgIcon;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_iMatchPer_playerName);
            tvTime = itemView.findViewById(R.id.tv_iMatchPer_time);
            imgIcon = itemView.findViewById(R.id.img_iMatchPer_icon);
        }
    }

}
