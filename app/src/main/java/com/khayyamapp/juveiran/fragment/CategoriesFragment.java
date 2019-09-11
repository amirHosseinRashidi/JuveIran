package com.khayyamapp.juveiran.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.adapters.CategoriesRvAdapter;
import com.khayyamapp.juveiran.data_model.Category;
import com.khayyamapp.juveiran.globals.Globals;

import java.util.ArrayList;


public class CategoriesFragment extends Fragment {
    RecyclerView rvCategory;
    ArrayList<Category> categoryArrayList;
    Context c;
    CategoriesRvAdapter adapter;

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_categories, container, false);

        //referencing views
        rvCategory = v.findViewById(R.id.rv_Fcategories_categoryList);

        //referencing objects
        categoryArrayList = new ArrayList<>();
        c = getContext();
        adapter = new CategoriesRvAdapter(c, categoryArrayList);


        categoryArrayList.add(new Category(R.drawable.logo_seriea, "سری آ", Globals.CATEGORY_ID_SERIA));
        categoryArrayList.add(new Category(R.drawable.logo_ucl, "لیگ قهرمانان", Globals.CATEGORY_ID_UCL));
        categoryArrayList.add(new Category(R.drawable.logo_coppa, "جام حذفی", Globals.CATEGORY_ID_COPPA));
        categoryArrayList.add(new Category(R.drawable.logo_transfer, "نقل و انتقالات", Globals.CATEGORY_ID_TRANSFER));
        categoryArrayList.add(new Category(R.drawable.logo_figc, "تیم ملی ایتالیا", Globals.CATEGORY_ID_FIGC));
        categoryArrayList.add(new Category(R.drawable.logo_microphone, "مصاحبه", Globals.CATEGORY_ID_MICROPHONE));
        categoryArrayList.add(new Category(R.drawable.logo_article, "مقالات", Globals.CATEGORY_ID_ARTICLE));
        categoryArrayList.add(new Category(R.drawable.logo_other, "متفرقه", Globals.CATEGORY_ID_OTHER));

        rvCategory.setAdapter(adapter);
        rvCategory.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false));

        return v;
    }

}
