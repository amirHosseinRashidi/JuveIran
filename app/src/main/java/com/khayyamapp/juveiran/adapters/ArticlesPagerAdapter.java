package com.khayyamapp.juveiran.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.khayyamapp.juveiran.fragment.ArticlesFragment;
import com.khayyamapp.juveiran.fragment.CategoriesFragment;
import com.khayyamapp.juveiran.fragment.ShortNewsFragment;

public class ArticlesPagerAdapter extends FragmentStatePagerAdapter {

    public ArticlesPagerAdapter(FragmentManager fm) {
        super(fm);
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
        return 3;
    }
}
