package com.example.models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesSettersGetters {
    private static SharedPreferences mSharedPref;
    public static final String USERNAME_USER = "USERNAME_USER";
    public static final String ID_USER = "ID_USER";
    public static final String AUTH_KEY = "AUTH_KEY";
    public static final String SETTINGS_IP = "SETTINGS_IP";
    public static final String NOME_PROFILE = "NOME_PROFILE";
    public static final String NIF_PROFILE = "NIF_PROFILE";
    public static final String SALDO_PROFILE = "SALDO_PROFILE";

    private SharedPreferencesSettersGetters()
    {

    }

    public static void init(Context context)
    {
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static String readString(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public static void writeString(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public static boolean readBoolean(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public static void writeBoolean(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public static Integer readInt(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public static void writeInt(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).commit();
    }

    public static void removeKey(String key){
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.remove(key).commit();
    }

}
