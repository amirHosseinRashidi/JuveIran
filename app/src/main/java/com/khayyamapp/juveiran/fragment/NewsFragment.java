package com.khayyamapp.juveiran.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.activity.CreateArticleActivity;
import com.khayyamapp.juveiran.adapters.ArticlesPagerAdapter;
import com.khayyamapp.juveiran.globals.GlobalMethods;


public class NewsFragment extends Fragment {


    TabLayout tabLayout;
    TabItem shortNews, lastestNews, mostViewedNews;

    ArticlesPagerAdapter pagerAdapter2;

    ViewPager viewPager;
    FloatingActionButton floatingActionButton;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        pagerAdapter2 = new ArticlesPagerAdapter(getActivity().getSupportFragmentManager());

        tabLayout = v.findViewById(R.id.tl_Fnews_tabs);
        shortNews = v.findViewById(R.id.ti_Fnews_shortNews);
        lastestNews = v.findViewById(R.id.ti_Fnews_lastestNews);
        mostViewedNews = v.findViewById(R.id.ti_Fnews_mostViewedNews);
        viewPager = v.findViewById(R.id.vp_Fnews_viewPager);
        floatingActionButton = v.findViewById(R.id.fab_Fnews_createArticle);

        viewPager.setAdapter(pagerAdapter2);
        viewPager.setCurrentItem(2);

        //tabLayoutFont
        GlobalMethods.setCustomFontToTabLayout(tabLayout,getActivity());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CreateArticleActivity.class));
            }
        });


        return v;
    }


    //setting font to tabLayout



}
