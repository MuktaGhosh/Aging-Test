package com.waltonbd.agingtest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;



public class BackgroundService extends Service {

    public Handler handler = null;
    public static Runnable runnable = null;
    JSONArray jArray = new JSONArray();
    TelephonyManager mTelephonyManager;
    private long mStartRX = 0, mStartTX = 0;
    Context mContext;
    Double latitude, longitude;
    LocationManager mLocationManager;

    private SignalStrength signalStrength;
    JSONObject jsonObject = new JSONObject();
    int dbm = 0;
    CustomPhoneStateListener customPhoneStateListener;
    String dateToStr;
    SignalStrength signalStrength1;
    NotificationCompat.Builder notification;
    final static String MY_ACTION = "MY_ACTION";
    LocationManager locationManager;
    String mprovider;
    int interval = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this;

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        turnOffDozeMode(mContext);
        if (intent != null) {
            interval = intent.getIntExtra("interval", 0);
            Log.e("time interval", String.valueOf(interval));
        }


        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                Intent intent = new Intent();
                intent.setAction(MY_ACTION);
                intent.putExtra("DATAPASSED", dateToStr);
                sendBroadcast(intent);

                doInBackground();
                handler.postDelayed(runnable, interval * 1000);
            }
        };
        handler.postDelayed(runnable, interval * 1000);
        return START_STICKY;
    }


    @Override
    public void onCreate() {
        mContext = this;
    }

    @Override
    public void onDestroy() {
        jArray = new JSONArray();
        issueNotificationStop();
        handler.removeCallbacks(runnable);
    }

    protected void doInBackground() {




    }




    void issueNotificationStop() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            makeNotificationChannel("CHANNEL_1", "BackGround Process Running", NotificationManager.IMPORTANCE_DEFAULT);
        }
        Intent myIntent = new Intent(mContext, MainActivity.class);
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        notification.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Notification!").setContentText("BackGround Process Stop").setContentIntent(pendingIntent).setNumber(10);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(1, notification.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void makeNotificationChannel(String id, String name, int importance) {
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setSound(null, null);
        channel.setShowBadge(true); // set false to disable badges, Oreo exclusive
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }

    public void turnOffDozeMode(Context context) {  //you can use with or without passing context
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }




    public class CustomPhoneStateListener extends PhoneStateListener {


    }

}
