package com.khayyamapp.juveiran.api_service;

import com.khayyamapp.juveiran.globals.GlobalMethods;
import com.khayyamapp.juveiran.globals.Globals;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    public static Retrofit retrofit = null;

    public static ApiInterface getApi() {
        if (retrofit == null) {
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS);

            retrofit = new Retrofit.Builder()
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Globals.BASE_URL)
                    .build();

        }

        return retrofit.create(ApiInterface.class);
    }

}
