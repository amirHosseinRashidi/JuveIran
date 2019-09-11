package com.khayyamapp.juveiran.globals;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;

public class Globals {
    public static final String BASE_URL = "http://juveiran.ir";
    public static final String EXTRA_ARTICLE_ARTICLE_ID = "id";
    public static final String EXTRA_PLAYER_STATS = "player";
    public static final String EXTRA_ARTICLE_ARTICLE_SHARETEXT = "sharetext";
    public static final String EXTRA_EMAIL = "email";
    public static final String EXTRA_PASS = "password";
    public static final String EXTRA_CODE = "code";
    public static final String EXTRA_IS_REGISTERED = "is_registered";
    public static final String EXTRA_ARTICLEDETAIL_ARTICLE_TITLE = "title";
    public static final String EXTRA_ARTICLEDETAIL_ARTICLE_URI = "uri";
    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_TITLE = "title";

    //these are category ids in juveiran.ir/api
    public static final String CATEGORY_ID_HOME = "1";
    public static final String CATEGORY_ID_UCL = "28";
    public static final String CATEGORY_ID_TRANSFER = "31";
    public static final String CATEGORY_ID_SERIA = "27";
    public static final String CATEGORY_ID_OTHER = "37";
    public static final String CATEGORY_ID_MICROPHONE = "33";
    public static final String CATEGORY_ID_FIGC = "32";
    public static final String CATEGORY_ID_ARTICLE = "38";
    public static final String CATEGORY_ID_COPPA = "29";


    public static int PLAYER_ROLE_GOALKEEPER = 1;
    public static int PLAYER_ROLE_DEFENDER = 2;
    public static int PLAYER_ROLE_MIDFEILDER = 3;
    public static int PLAYER_ROLE_ATTACKER = 4;

    public static void loadImage(RequestManager glide, String url, ImageView view) {
        glide.load(url).into(view);
    }

    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() || (Patterns.PHONE.matcher(email).matches() && email.length() >= 11);
        //return matcher.matches();
    }

    public static boolean isPasswordRight(String pass) {
        return pass.length() >= 5;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
