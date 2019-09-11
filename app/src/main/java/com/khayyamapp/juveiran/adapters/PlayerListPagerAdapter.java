package com.khayyamapp.juveiran.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.khayyamapp.juveiran.data_model.PlayersListDataModel;
import com.khayyamapp.juveiran.fragment.PlayerStatsFragment;
import com.khayyamapp.juveiran.globals.Globals;

import java.util.ArrayList;

public class PlayerListPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<PlayersListDataModel> playerList;

    public PlayerListPagerAdapter(FragmentManager fm, ArrayList<PlayersListDataModel> playerList) {
        super(fm);
        this.playerList = playerList;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        PlayerStatsFragment playerStatsFragment = new PlayerStatsFragment();
        bundle.putSerializable(Globals.EXTRA_PLAYER_STATS, playerList.get(position));
        playerStatsFragment.setArguments(bundle);
        return playerStatsFragment;
    }

    @Override
    public int getCount() {
        return playerList.size();
    }
}
