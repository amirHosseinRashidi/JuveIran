package com.khayyamapp.juveiran.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.khayyamapp.juveiran.R;
import com.khayyamapp.juveiran.custom_views.IranSansTextView;
import com.khayyamapp.juveiran.globals.Globals;

public class ArticleReadMoreFragment extends Fragment {

    Toolbar toolbar;
    WebView webView;
    RelativeLayout rlProgress;
    IranSansTextView toolbarTitle;
    RelativeLayout rlErrorPage;
    ImageView imgRetry;
    String uri, title;

    Context c;

    public ArticleReadMoreFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_article_read_more, container, false);

        c = getActivity();
        uri = getArguments().getString(Globals.EXTRA_ARTICLEDETAIL_ARTICLE_URI);
        title = getArguments().getString(Globals.EXTRA_ARTICLEDETAIL_ARTICLE_TITLE);

        toolbarTitle = v.findViewById(R.id.tv_FarticleReadMore_toolbarTitle);
        toolbar = v.findViewById(R.id.tb_FarticleReadMore_toolbar);
        rlProgress = v.findViewById(R.id.rl_FarticleReadMore_progress);
        webView = v.findViewById(R.id.wv_FarticleReadMore_webPage);
        rlErrorPage = v.findViewById(R.id.rl_FarticleReadMore_errorPage);
        imgRetry = v.findViewById(R.id.img_FarticleReadMore_retry);

        rlProgress.setVisibility(View.VISIBLE);

        setupToolbar();

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                rlProgress.setVisibility(View.VISIBLE);
                rlErrorPage.setVisibility(View.GONE);
                retry();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                rlProgress.setVisibility(View.GONE);
                rlErrorPage.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (isInternetAvialable()) {
                    rlErrorPage.setVisibility(View.VISIBLE);
                    retry();
                }
            }
        });


        if (isInternetAvialable()) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(uri);
        } else {
            rlProgress.setVisibility(View.GONE);
            rlErrorPage.setVisibility(View.VISIBLE);
            retry();
        }
        return v;
    }


    void retry() {
        imgRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(uri);

            }
        });
    }

    boolean isInternetAvialable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }

    void setupToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarTitle.setText(title.replaceAll("-"," "));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

}
