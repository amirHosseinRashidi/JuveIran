package com.khayyamapp.juveiran.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.custom_views.IranSansTextView;
import com.khayyamapp.juveiran.data_model.user_commetns.UserCommentsDatum;
import com.khayyamapp.juveiran.globals.SpGlobals;

import java.util.ArrayList;

public class UserCommentRvAdapter extends RecyclerView.Adapter<UserCommentRvAdapter.ViewHolder> {

    private Context c;
    private ArrayList<UserCommentsDatum> arrayList;

    public UserCommentRvAdapter(Context c, ArrayList<UserCommentsDatum> arrayList) {
        this.c = c;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SharedPreferences userStats = c.getSharedPreferences(SpGlobals.userStatsSpName, Context.MODE_PRIVATE);
        holder.name.setText(userStats.getString(SpGlobals.KEY_USER_NAME, ""));
        holder.message.setText(Html.fromHtml(arrayList.get(position).getText()));
        holder.date.setText(Html.fromHtml(arrayList.get(position).getDate()));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        IranSansTextView name, message, date;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_iComment_name);
            message = itemView.findViewById(R.id.tv_iComment_message);
            date = itemView.findViewById(R.id.tv_iComment_date);
        }
    }
}
