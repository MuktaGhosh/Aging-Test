package com.example.babul.wsmt.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by newton on 7/6/17.
 */

public class NetworkModule implements NetworkMonitor {

    private Context mContext;

    NetworkModule(Context context){
        this.mContext = context.getApplicationContext();
    }
    @Override
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
