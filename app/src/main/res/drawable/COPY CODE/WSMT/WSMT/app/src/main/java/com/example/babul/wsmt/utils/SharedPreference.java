package com.example.babul.wsmt.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class SharedPreference {

    SharedPreferences preferences;
    Editor editor;

    private static final String PREFS_NAME = "WSMT";
    public static final String LOG_IN_STATUS = "login";
    public static final String USER_ID = "user_id";
    public static final String USER_TYPE = "user_type";
    public static final String HANDSET_ID = "handset_id";
    public static final String API_TOKEN = "api_token";
    public static final String HANDSET_TITLE = "handsettitle";



    public SharedPreference() {
        super();
        // TODO Auto-generated constructor stub
    }


    public static boolean getBooleanValue(final Context context, String key) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(key, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public static void setBooleanValue(final Context context, String key, Boolean status) {
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();

        editor.putBoolean(key, status);
        editor.apply();
    }


    public static String getStringValue(final Context context, String key) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(key, "");
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public static void setStringValue(final Context context, String key, String value) {
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();

        editor.putString(key, value);
        editor.apply();
    }

    public static int getIntValue(final Context context, String key) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getInt(key, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public static void setIntValue(final Context context, String key,
                                   int value) {
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();

        editor.putInt(key, value);
        editor.apply();
    }

    public static long getLongValue(final Context context, String key) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getLong(key, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public static void setLongValue(final Context context, String key, long value) {
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putLong(key, value);
        editor.apply();
    }

}
