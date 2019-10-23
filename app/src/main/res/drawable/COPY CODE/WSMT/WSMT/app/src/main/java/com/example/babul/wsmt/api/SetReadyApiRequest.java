package com.example.babul.wsmt.api;

import com.example.babul.wsmt.utils.ConstantData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

class SetReadyApiRequest {
    private static final SetReadyApiRequest instance = new SetReadyApiRequest();

    private SetReadyApiRequest() {

    }

    static SetReadyApiRequest getInstance() {
        return instance;
    }


    OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(new NetworkInterceptor());
        okHttpClient.connectTimeout(1500 * 60, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(1000 * 60, TimeUnit.MILLISECONDS);
//        okHttpClient.retryOnConnectionFailure(true);
        return okHttpClient.build();
    }
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit getRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ConstantData.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
