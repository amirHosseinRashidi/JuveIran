package com.khayyamapp.juveiran.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.data_model.PlayersListDataModel;
import com.khayyamapp.juveiran.globals.Globals;


public class PlayerStatsFragment extends Fragment {

    PlayersListDataModel player;
    ImageView imgStatic1;
    ImageView imgStatic2;

    ImageView imgPlayerImage;

    TextView tvStatic1;
    TextView tvStatic2;

    TextView tvPlayerNumber;
    TextView tvNameEn, tvNameFa;

    TextView tvPlayerPost;

    TextView tvAge, tvMinute, tvMatchCount, tvNationality;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player_stats, container, false);

        player = (PlayersListDataModel) getArguments().getSerializable(Globals.EXTRA_PLAYER_STATS);
        imgStatic1 = v.findViewById(R.id.img_FplayerStats_static1Image);
        imgStatic2 = v.findViewById(R.id.img_FplayerStats_static2Image);
        tvStatic1 = v.findViewById(R.id.tv_FplayerStats_static1);
        tvStatic2 = v.findViewById(R.id.tv_FplayerStats_static2);

        tvNameFa = v.findViewById(R.id.tv_FplayerStats_playerFaName);
        tvNameEn = v.findViewById(R.id.tv_FplayerStats_playerEnName);
        tvAge = v.findViewById(R.id.tv_FplayerStats_age);
        tvMinute = v.findViewById(R.id.tv_FplayerStats_minute);
        tvMatchCount = v.findViewById(R.id.tv_FplayerStats_matchCount);
        tvNationality = v.findViewById(R.id.tv_FplayerStats_nationality);
        tvPlayerNumber = v.findViewById(R.id.tv_FplayerStats_number);
        imgPlayerImage = v.findViewById(R.id.img_FplayerStats_playerImage);


        tvNameEn.setText(player.getEnname());
        tvNameFa.setText(player.getName());

        tvPlayerPost = v.findViewById(R.id.tv_FplayerStats_post);

        tvStatic1.setText(player.getStatic1().get(0) + " : " + player.getStatic1().get(1));
        tvStatic2.setText(player.getStatic2().get(0) + " : " + player.getStatic2().get(1));

        tvAge.setText(player.getAge().toString());
        tvNationality.setText(player.getCountry());
        tvMatchCount.setText(player.getMatchCount().toString());
        tvMinute.setText(player.getMinute().toString());


        switch (player.getRole()) {
            case 1:
                imgStatic1.setImageResource(R.drawable.pic_save_goal);
                imgStatic2.setImageResource(R.drawable.pic_goaled);
                tvPlayerPost.setText(getResources().getString(R.string.goalKeeper));
                break;
            case 2:
                imgStatic1.setImageResource(R.drawable.pic_soccer_clear);
                imgStatic2.setImageResource(R.drawable.pic_takl);
                tvPlayerPost.setText(getResources().getString(R.string.defender));
                break;
            case 3:
                imgStatic2.setImageResource(R.drawable.pic_pass);
                tvPlayerPost.setText(getResources().getString(R.string.midLine));
                break;
            case 4:
                tvPlayerPost.setText(getResources().getString(R.string.attacker));
                break;
        }

        tvPlayerNumber.setText(" شماره :" + player.getNumber());

        Glide.with(this).load(player.getPic()).into(imgPlayerImage);


        return v;
    }

}
