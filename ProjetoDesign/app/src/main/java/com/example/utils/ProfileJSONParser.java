package com.example.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.models.Profile;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileJSONParser {

    public static Profile parserJsonProfile(String response, Context context){
        Profile auxProfile = null;
        try {
            JSONObject profile = new JSONObject(response);
            //int id, String username, String authKey, String email,int profile_ID, int saldo, String nome, int nif, String profileimage
            int profile_ID = profile.getInt("profile_id");
            int profile_saldo = profile.getInt("saldo");
            String profile_nome = profile.getString("nome");
            int profile_nif = profile.getInt("nif");
            String profileimage = profile.getString("profileimage");

            auxProfile = new Profile(profile_ID, profile_saldo, profile_nome, profile_nif, profileimage);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxProfile;
    }

    public static boolean isConnectionInternet(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }



}
