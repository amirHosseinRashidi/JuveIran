package com.khayyamapp.juveiran.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.khayyamapp.juveiran.fragment.CategoriesFragment;
import com.khayyamapp.juveiran.fragment.ArticlesFragment;
import com.khayyamapp.juveiran.fragment.ShortNewsFragment;

public class NewsPagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;


    public NewsPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0:

                //position 2
                return new CategoriesFragment();
            case 1:

                //position 1
                return new ShortNewsFragment();
            case 2:

                //position 0
                return new ArticlesFragment();
            default:
                return new ArticlesFragment();

        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }


}
