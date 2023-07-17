package com.utp.testinnp.api.http;

import com.utp.testinnp.api.params.HttpParams;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordPressClient {

    public static Retrofit getRetroInstance(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpParams.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static ApiService getApiService() {
        return getRetroInstance().create(ApiService.class);
    }
}
