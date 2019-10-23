package com.example.babul.wsmt.api;

import android.util.Log;

import com.example.babul.wsmt.utils.UserDialog;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by newton on 7/6/17.
 */

public class NetworkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        NetworkModule networkModule = new NetworkModule(UserDialog.AppController.getInstance());
        if (networkModule.isConnected()) {

            return chain.proceed(getRequest(chain.request()));
        } else
            throw new NoNetworkException();
    }

    private Request getRequest(Request request){
        HttpUrl rootUrl = request.url();
        HttpUrl callUrl = rootUrl.newBuilder()
               // .addQueryParameter("api_token", SharedPreference.getStringValue(AppController.getInstance(),SharedPreference.API_TOKEN))
                .build();
        Log.e("call url", String.valueOf(callUrl));
        return request.newBuilder()
                .url(callUrl)
               // .addHeader("Content-type", "application/x-www-form-urlencoded")
               // .addHeader("Content-type", "application/json")
               // .addHeader("Authorization", "Bearer :")
                .build();

    }

}
