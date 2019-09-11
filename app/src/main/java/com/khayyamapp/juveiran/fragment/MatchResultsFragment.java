package com.khayyamapp.juveiran.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.globals.GlobalMethods;

public class MatchResultsFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    public MatchResultsFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_match_results, container, false);

        viewPager = v.findViewById(R.id.vp_FmatchResults_viewPager);
        tabLayout = v.findViewById(R.id.tl_FmatchResults_tabs);

        viewPager.setAdapter(new PagerAdapter(getActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        GlobalMethods.setCustomFontToTabLayout(tabLayout,getActivity());
        return v;
    }


    public class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new NextGamesFragment();
                case 1:
                    return new PlayedGamesFragment();
            }
            return null;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.nextGames);
                case 1:
                    return getResources().getString(R.string.playedGames);
            }
            return super.getPageTitle(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
