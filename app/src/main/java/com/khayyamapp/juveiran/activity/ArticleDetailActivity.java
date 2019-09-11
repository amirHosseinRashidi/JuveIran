package com.khayyamapp.juveiran.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.fragment.ArticleDetailFragment;
import com.khayyamapp.juveiran.globals.Globals;

public class ArticleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        ArticleDetailFragment articleDetailFragment = new ArticleDetailFragment();
        Bundle bundle = new Bundle();
        String id = getIntent().getStringExtra(Globals.EXTRA_ARTICLE_ARTICLE_ID);
        String shareText = getIntent().getStringExtra(Globals.EXTRA_ARTICLE_ARTICLE_SHARETEXT);
        Log.i("ID_TEST", "onCreateView: " + id);

        if (id != null) {
            bundle.putString(Globals.EXTRA_ARTICLE_ARTICLE_ID, id);
            bundle.putString(Globals.EXTRA_ARTICLE_ARTICLE_SHARETEXT, shareText);
            articleDetailFragment.setArguments(bundle);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.fl_AarticleDetail_container, articleDetailFragment);
        transaction.commit();

    }
}
